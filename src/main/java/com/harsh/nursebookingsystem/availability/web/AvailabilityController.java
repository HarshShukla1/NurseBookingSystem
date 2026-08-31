package com.harsh.nursebookingsystem.availability.web;
import com.harsh.nursebookingsystem.availability.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/nurses/{nurseId}/availability")
public class AvailabilityController {
    private final AvailabilityService service;
    public AvailabilityController(AvailabilityService service) { this.service = service; }
    @GetMapping public List<AvailabilityPeriodResponse> get(@PathVariable UUID nurseId) { return service.get(nurseId); }
    @PutMapping public List<AvailabilityPeriodResponse> replace(@PathVariable UUID nurseId, @Valid @RequestBody UpdateAvailabilityRequest request) { return service.replace(nurseId, request); }
}
