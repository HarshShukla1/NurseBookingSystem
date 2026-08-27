package com.harsh.nursebookingsystem.nurse.web;

import com.harsh.nursebookingsystem.nurse.domain.NurseProfile;
import com.harsh.nursebookingsystem.nurse.domain.VerificationStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * The JSON returned to API callers. Returning this DTO instead of the JPA
 * entity prevents database-only fields and lazy relationships leaking into the API.
 */
public record NurseProfileResponse(
        UUID id, UUID userId, String firstName, String lastName, String professionalTitle,
        String bio, String licenseNumber, Integer yearsOfExperience, Set<String> specialties,
        Set<String> serviceAreas, BigDecimal hourlyRate, VerificationStatus verificationStatus,
        String verificationNotes, Instant verifiedAt) {

    // Keep entity-to-API mapping in one place so every endpoint returns the same shape.
    public static NurseProfileResponse from(NurseProfile profile) {
        return new NurseProfileResponse(profile.getId(), profile.getUser().getId(), profile.getFirstName(),
                profile.getLastName(), profile.getProfessionalTitle(), profile.getBio(), profile.getLicenseNumber(),
                profile.getYearsOfExperience(), Set.copyOf(profile.getSpecialties()), Set.copyOf(profile.getServiceAreas()),
                profile.getHourlyRate(), profile.getVerificationStatus(), profile.getVerificationNotes(), profile.getVerifiedAt());
    }
}
