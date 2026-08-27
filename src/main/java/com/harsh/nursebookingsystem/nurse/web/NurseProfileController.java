package com.harsh.nursebookingsystem.nurse.web;

import com.harsh.nursebookingsystem.nurse.NurseProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController // Return Java objects as JSON rather than rendering HTML views.
@RequestMapping("/api/nurses") // Shared URL prefix for every nurse endpoint.
public class NurseProfileController {
    private final NurseProfileService service;
    public NurseProfileController(NurseProfileService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // @Valid runs the constraints declared in CreateNurseProfileRequest before the service is called.
    public NurseProfileResponse create(@Valid @RequestBody CreateNurseProfileRequest request) { return service.create(request); }

    @GetMapping("/{id}")
    // Spring converts the UUID in the URL to a UUID method argument automatically.
    public NurseProfileResponse get(@PathVariable UUID id) { return service.get(id); }

    @PutMapping("/{id}")
    public NurseProfileResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateNurseProfileRequest request) { return service.update(id, request); }

    @PostMapping("/{id}/verification")
    // This will be restricted to ADMIN users when authentication is added.
    public NurseProfileResponse review(@PathVariable UUID id, @Valid @RequestBody ReviewVerificationRequest request) { return service.review(id, request); }

    @GetMapping
    // /api/nurses, /api/nurses?specialty=ICU, and service-area filtering all use this method.
    public List<NurseProfileResponse> directory(@RequestParam(required = false) String specialty,
                                                @RequestParam(required = false) String serviceArea) {
        return service.directory(specialty, serviceArea);
    }
}
