package com.harsh.nursebookingsystem.booking;
import com.harsh.nursebookingsystem.availability.repository.NurseAvailabilityRepository;
import com.harsh.nursebookingsystem.booking.domain.*;
import com.harsh.nursebookingsystem.booking.repository.BookingRepository;
import com.harsh.nursebookingsystem.booking.web.*;
import com.harsh.nursebookingsystem.nurse.domain.NurseProfile;
import com.harsh.nursebookingsystem.nurse.repository.NurseProfileRepository;
import com.harsh.nursebookingsystem.user.domain.*;
import com.harsh.nursebookingsystem.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.*;
import java.util.*;

@Service @Transactional
public class BookingService {
    private final BookingRepository bookings; private final NurseProfileRepository nurses; private final UserRepository users; private final NurseAvailabilityRepository availability;
    public BookingService(BookingRepository bookings, NurseProfileRepository nurses, UserRepository users, NurseAvailabilityRepository availability) { this.bookings = bookings; this.nurses = nurses; this.users = users; this.availability = availability; }
    public BookingResponse create(UUID nurseId, CreateBookingRequest request) {
        NurseProfile nurse = nurses.findById(nurseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nurse profile not found"));
        User patient = users.findById(request.patientUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));
        if (patient.getRole() != Role.PATIENT) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Booking user must have the PATIENT role");
        LocalDateTime startsAt = request.startsAt(); LocalDateTime endsAt = startsAt.plusHours(1);
        boolean withinAvailability = availability.findByNurseProfileIdOrderByDayOfWeek(nurseId).stream().anyMatch(period -> period.getDayOfWeek() == startsAt.getDayOfWeek() && !startsAt.toLocalTime().isBefore(period.getStartTime()) && !endsAt.toLocalTime().isAfter(period.getEndTime()));
        if (!withinAvailability) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The selected time is outside the nurse's availability");
        if (bookings.existsByNurseProfileIdAndStartsAtAndStatus(nurseId, startsAt, BookingStatus.CONFIRMED)) throw new ResponseStatusException(HttpStatus.CONFLICT, "This slot has already been booked");
        return BookingResponse.from(bookings.save(new Booking(nurse, patient, startsAt, endsAt)));
    }
    @Transactional(readOnly = true) public List<BookingResponse> forPatient(UUID patientId) { return bookings.findByPatientIdOrderByStartsAtDesc(patientId).stream().map(BookingResponse::from).toList(); }
    @Transactional(readOnly = true)
    public List<AvailableSlotResponse> availableSlots(UUID nurseId, LocalDate from, LocalDate to) {
        if (to.isBefore(from) || to.isAfter(from.plusDays(31))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Choose a date range of up to 31 days");
        if (!nurses.existsById(nurseId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nurse profile not found");
        Set<LocalDateTime> booked = bookings.findByNurseProfileIdAndStartsAtGreaterThanEqualAndStartsAtLessThanAndStatus(nurseId, from.atStartOfDay(), to.plusDays(1).atStartOfDay(), BookingStatus.CONFIRMED).stream().map(Booking::getStartsAt).collect(java.util.stream.Collectors.toSet());
        List<AvailableSlotResponse> result = new ArrayList<>();
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) for (var period : availability.findByNurseProfileIdOrderByDayOfWeek(nurseId)) {
            if (period.getDayOfWeek() != date.getDayOfWeek()) continue;
            for (LocalDateTime start = LocalDateTime.of(date, period.getStartTime()); !start.plusHours(1).toLocalTime().isAfter(period.getEndTime()); start = start.plusHours(1)) if (!booked.contains(start)) result.add(new AvailableSlotResponse(start, start.plusHours(1)));
        }
        return result;
    }
}
