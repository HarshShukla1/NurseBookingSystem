package com.harsh.nursebookingsystem.booking.web;
import java.time.LocalDateTime;
public record AvailableSlotResponse(LocalDateTime startsAt, LocalDateTime endsAt) { }
