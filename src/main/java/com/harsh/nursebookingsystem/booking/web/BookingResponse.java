package com.harsh.nursebookingsystem.booking.web;
import com.harsh.nursebookingsystem.booking.domain.*;
import java.time.LocalDateTime;
import java.util.UUID;
public record BookingResponse(UUID id, UUID nurseProfileId, UUID patientUserId, LocalDateTime startsAt, LocalDateTime endsAt, BookingStatus status) {
    public static BookingResponse from(Booking value) { return new BookingResponse(value.getId(), value.getNurseProfile().getId(), value.getPatient().getId(), value.getStartsAt(), value.getEndsAt(), value.getStatus()); }
}
