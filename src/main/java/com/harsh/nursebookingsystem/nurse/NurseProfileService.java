package com.harsh.nursebookingsystem.nurse;

import com.harsh.nursebookingsystem.nurse.domain.NurseProfile;
import com.harsh.nursebookingsystem.nurse.domain.VerificationStatus;
import com.harsh.nursebookingsystem.nurse.repository.NurseProfileRepository;
import com.harsh.nursebookingsystem.nurse.web.CreateNurseProfileRequest;
import com.harsh.nursebookingsystem.nurse.web.NurseProfileResponse;
import com.harsh.nursebookingsystem.nurse.web.ReviewVerificationRequest;
import com.harsh.nursebookingsystem.nurse.web.UpdateNurseProfileRequest;
import com.harsh.nursebookingsystem.user.domain.Role;
import com.harsh.nursebookingsystem.user.domain.User;
import com.harsh.nursebookingsystem.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service // Marks this class as application/business logic managed by Spring.
@Transactional // Each write method runs atomically: all database changes succeed or none do.
public class NurseProfileService {
    private final NurseProfileRepository nurseProfiles;
    private final UserRepository users;

    public NurseProfileService(NurseProfileRepository nurseProfiles, UserRepository users) {
        this.nurseProfiles = nurseProfiles;
        this.users = users;
    }

    public NurseProfileResponse create(CreateNurseProfileRequest request) {
        // A one-to-one database relationship also prevents duplicates, but this
        // explicit check lets the API return a useful 409 Conflict response.
        if (nurseProfiles.findByUserId(request.userId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user already has a nurse profile");
        }
        // orElseThrow turns an absent database row into an HTTP 404 response.
        User user = users.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getRole() != Role.NURSE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must have the NURSE role");
        }
        NurseProfile profile = new NurseProfile();
        profile.setUser(user);
        apply(profile, request.firstName(), request.lastName(), request.professionalTitle(), request.bio(),
                request.licenseNumber(), request.yearsOfExperience(), request.specialties(), request.serviceAreas(), request.hourlyRate());
        return NurseProfileResponse.from(nurseProfiles.save(profile));
    }

    // readOnly can let Spring/JPA optimise the transaction and communicates intent.
    @Transactional(readOnly = true)
    public NurseProfileResponse get(UUID id) { return NurseProfileResponse.from(find(id)); }

    public NurseProfileResponse update(UUID id, UpdateNurseProfileRequest request) {
        NurseProfile profile = find(id);
        apply(profile, request.firstName(), request.lastName(), request.professionalTitle(), request.bio(),
                request.licenseNumber(), request.yearsOfExperience(), request.specialties(), request.serviceAreas(), request.hourlyRate());
        // Profile or licence changes require another review; an old approval
        // must not remain valid after credentials have been edited.
        profile.setVerificationStatus(VerificationStatus.PENDING);
        profile.setVerifiedAt(null);
        profile.setVerificationNotes(null);
        return NurseProfileResponse.from(profile);
    }

    public NurseProfileResponse review(UUID id, ReviewVerificationRequest request) {
        // PENDING is a starting state, not the outcome of a review.
        if (request.status() == VerificationStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A review must approve or reject the profile");
        }
        NurseProfile profile = find(id);
        profile.setVerificationStatus(request.status());
        profile.setVerificationNotes(request.notes());
        profile.setVerifiedAt(Instant.now()); // Records when the decision was made.
        return NurseProfileResponse.from(profile);
    }

    @Transactional(readOnly = true)
    public List<NurseProfileResponse> directory(String specialty, String serviceArea) {
        // Start with only approved profiles so unverified nurses never appear in search results.
        return nurseProfiles.findByVerificationStatus(VerificationStatus.APPROVED).stream()
                // The optional filters are applied only when the caller supplied a value.
                .filter(profile -> containsIgnoreCase(profile.getSpecialties(), specialty))
                .filter(profile -> containsIgnoreCase(profile.getServiceAreas(), serviceArea))
                .map(NurseProfileResponse::from)
                .toList();
    }

    private NurseProfile find(UUID id) {
        return nurseProfiles.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nurse profile not found"));
    }

    private void apply(NurseProfile profile, String firstName, String lastName, String title, String bio,
                       String licenseNumber, Integer experience, java.util.Set<String> specialties,
                       java.util.Set<String> areas, java.math.BigDecimal hourlyRate) {
        // Keeping shared field assignment here prevents create and update from drifting apart.
        profile.setFirstName(firstName.trim()); profile.setLastName(lastName.trim()); profile.setProfessionalTitle(title);
        profile.setBio(bio); profile.setLicenseNumber(licenseNumber); profile.setYearsOfExperience(experience);
        profile.setSpecialties(specialties); profile.setServiceAreas(areas); profile.setHourlyRate(hourlyRate);
    }

    private boolean containsIgnoreCase(java.util.Set<String> values, String query) {
        // A blank query means "do not filter". Locale.ROOT makes lower-casing predictable.
        return query == null || query.isBlank() || values.stream()
                .anyMatch(value -> value.toLowerCase(Locale.ROOT).contains(query.trim().toLowerCase(Locale.ROOT)));
    }
}
