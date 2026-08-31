package com.harsh.nursebookingsystem.availability.web;

import com.harsh.nursebookingsystem.availability.domain.NurseAvailability;
import java.time.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityPeriodResponse(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
    public static AvailabilityPeriodResponse from(NurseAvailability value) { return new AvailabilityPeriodResponse(value.getDayOfWeek(), value.getStartTime(), value.getEndTime()); }
}
