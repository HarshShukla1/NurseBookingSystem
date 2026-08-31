package com.harsh.nursebookingsystem.availability.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record UpdateAvailabilityRequest(@NotEmpty List<@Valid AvailabilityPeriodRequest> periods) { }
