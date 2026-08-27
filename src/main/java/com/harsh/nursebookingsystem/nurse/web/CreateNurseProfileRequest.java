package com.harsh.nursebookingsystem.nurse.web;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * The JSON body accepted when a nurse profile is first created.
 * A Java record is ideal for request data because it is immutable and only
 * carries data; Spring maps matching JSON fields into its constructor.
 */
public record CreateNurseProfileRequest(
        // The account must already exist and have the NURSE role.
        @NotNull UUID userId,
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @Size(max = 150) String professionalTitle,
        String bio,
        @Size(max = 100) String licenseNumber,
        @Min(0) Integer yearsOfExperience,
        Set<@NotBlank @Size(max = 100) String> specialties,
        Set<@NotBlank @Size(max = 150) String> serviceAreas,
        // Validation annotations are checked because the controller uses @Valid.
        @PositiveOrZero BigDecimal hourlyRate) {
}
