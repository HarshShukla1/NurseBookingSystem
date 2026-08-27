package com.harsh.nursebookingsystem.nurse.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

/**
 * The editable portion of a nurse profile. The userId and verification fields
 * are deliberately absent: an update must not transfer profile ownership or
 * let a nurse approve their own profile.
 */
public record UpdateNurseProfileRequest(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @Size(max = 150) String professionalTitle,
        String bio,
        @Size(max = 100) String licenseNumber,
        @Min(0) Integer yearsOfExperience,
        Set<@NotBlank @Size(max = 100) String> specialties,
        Set<@NotBlank @Size(max = 150) String> serviceAreas,
        @PositiveOrZero BigDecimal hourlyRate) {
}
