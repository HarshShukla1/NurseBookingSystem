package com.harsh.nursebookingsystem.booking.web;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;
public record CreateBookingRequest(@NotNull UUID patientUserId, @NotNull LocalDateTime startsAt) { }
