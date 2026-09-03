package com.harsh.nursebookingsystem.booking.repository;
import com.harsh.nursebookingsystem.booking.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.*;
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsByNurseProfileIdAndStartsAtAndStatus(UUID nurseId, LocalDateTime startsAt, BookingStatus status);
    List<Booking> findByNurseProfileIdAndStartsAtGreaterThanEqualAndStartsAtLessThanAndStatus(UUID nurseId, LocalDateTime from, LocalDateTime to, BookingStatus status);
    List<Booking> findByPatientIdOrderByStartsAtDesc(UUID patientId);
    List<Booking> findByStatusAndEndsAtGreaterThanEqualOrderByStartsAtAsc(BookingStatus status, LocalDateTime endsAt);
}
