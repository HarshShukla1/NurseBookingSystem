package com.harsh.nursebookingsystem.admin;

import com.harsh.nursebookingsystem.admin.web.AdminDashboardResponse;
import com.harsh.nursebookingsystem.booking.domain.BookingStatus;
import com.harsh.nursebookingsystem.booking.repository.BookingRepository;
import com.harsh.nursebookingsystem.nurse.repository.NurseProfileRepository;
import com.harsh.nursebookingsystem.patient.repository.PatientProfileRepository;
import com.harsh.nursebookingsystem.user.domain.Role;
import com.harsh.nursebookingsystem.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminDashboardService {
    private final UserRepository users;
    private final PatientProfileRepository patientProfiles;
    private final NurseProfileRepository nurseProfiles;
    private final BookingRepository bookings;

    public AdminDashboardService(UserRepository users, PatientProfileRepository patientProfiles,
                                 NurseProfileRepository nurseProfiles, BookingRepository bookings) {
        this.users = users;
        this.patientProfiles = patientProfiles;
        this.nurseProfiles = nurseProfiles;
        this.bookings = bookings;
    }

    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboard() {
        List<AdminDashboardResponse.PatientAccountResponse> patients = users.findAllByRoleOrderByCreatedAtDesc(Role.PATIENT).stream()
                .map(user -> {
                    var profile = patientProfiles.findByUserId(user.getId()).orElse(null);
                    return new AdminDashboardResponse.PatientAccountResponse(user.getId(), user.getEmail(), user.getPhoneNumber(),
                            profile == null ? null : profile.getFirstName(), profile == null ? null : profile.getLastName(),
                            profile == null ? null : profile.getDefaultCity(), user.getCreatedAt());
                }).toList();
        List<AdminDashboardResponse.NurseAccountResponse> nurses = users.findAllByRoleOrderByCreatedAtDesc(Role.NURSE).stream()
                .map(user -> {
                    var profile = nurseProfiles.findByUserId(user.getId()).orElse(null);
                    return new AdminDashboardResponse.NurseAccountResponse(user.getId(), profile == null ? null : profile.getId(),
                            user.getEmail(), user.getPhoneNumber(), profile == null ? null : profile.getFirstName(),
                            profile == null ? null : profile.getLastName(), profile == null ? null : profile.getProfessionalTitle(),
                            profile == null ? "PROFILE_INCOMPLETE" : profile.getVerificationStatus().name(), user.getCreatedAt());
                }).toList();
        List<AdminDashboardResponse.ActiveBookingResponse> activeBookings = bookings
                .findByStatusAndEndsAtGreaterThanEqualOrderByStartsAtAsc(BookingStatus.CONFIRMED, LocalDateTime.now()).stream()
                .map(booking -> new AdminDashboardResponse.ActiveBookingResponse(booking.getId(), booking.getNurseProfile().getId(),
                        booking.getNurseProfile().getFirstName() + " " + booking.getNurseProfile().getLastName(),
                        booking.getPatient().getId(), patientName(booking.getPatient().getId()), booking.getPatient().getEmail(),
                        booking.getStartsAt(), booking.getEndsAt(), booking.getStatus().name()))
                .toList();
        return new AdminDashboardResponse(patients, nurses, activeBookings);
    }

    private String patientName(java.util.UUID userId) {
        return patientProfiles.findByUserId(userId)
                .map(profile -> profile.getFirstName() + " " + profile.getLastName())
                .orElse("Patient profile incomplete");
    }
}
