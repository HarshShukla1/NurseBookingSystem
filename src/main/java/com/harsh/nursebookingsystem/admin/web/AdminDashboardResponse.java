package com.harsh.nursebookingsystem.admin.web;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AdminDashboardResponse(
        List<PatientAccountResponse> patients,
        List<NurseAccountResponse> nurses,
        List<ActiveBookingResponse> activeBookings) {

    public record PatientAccountResponse(UUID userId, String email, String phoneNumber,
                                         String firstName, String lastName, String city, Instant joinedAt) { }

    public record NurseAccountResponse(UUID userId, UUID profileId, String email, String phoneNumber,
                                       String firstName, String lastName, String professionalTitle,
                                       String verificationStatus, Instant joinedAt) { }

    public record ActiveBookingResponse(UUID id, UUID nurseProfileId, String nurseName,
                                        UUID patientUserId, String patientName, String patientEmail,
                                        LocalDateTime startsAt, LocalDateTime endsAt, String status) { }
}
