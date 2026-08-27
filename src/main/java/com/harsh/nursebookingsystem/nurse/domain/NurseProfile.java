package com.harsh.nursebookingsystem.nurse.domain;

import com.harsh.nursebookingsystem.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Professional information belonging to a user with the NURSE role.
 *
 * Verification, qualifications, availability, and services will be modelled
 * in later features so this first entity remains focused on core profile data.
 */
@Entity
@Table(name = "nurse_profiles")
public class NurseProfile {

    // UUID primary key for this profile table.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // This profile owns the relationship: nurse_profiles.user_id is the foreign key.
    // LAZY prevents loading the User object unless it is actually needed.
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotBlank
    @Size(max = 100)
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Size(max = 150)
    @Column(name = "professional_title", length = 150)
    private String professionalTitle;

    // PostgreSQL TEXT allows a longer professional introduction than a short VARCHAR field.
    @Column(columnDefinition = "TEXT")
    private String bio;

    @Size(max = 100)
    @Column(name = "license_number", length = 100)
    private String licenseNumber;

    // Validation prevents negative experience values before data reaches the database.
    @Min(0)
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    // These are simple strings, not full entities, so JPA stores them in a
    // separate collection table rather than creating a Specialty class.
    @ElementCollection
    @CollectionTable(name = "nurse_specialties", joinColumns = @JoinColumn(name = "nurse_profile_id"))
    @Column(name = "specialty", nullable = false, length = 100)
    private Set<String> specialties = new HashSet<>();

    // A nurse can serve several locations, and each location can contain many nurses.
    @ElementCollection
    @CollectionTable(name = "nurse_service_areas", joinColumns = @JoinColumn(name = "nurse_profile_id"))
    @Column(name = "service_area", nullable = false, length = 150)
    private Set<String> serviceAreas = new HashSet<>();

    // BigDecimal is used for money; float/double can introduce rounding errors.
    @Column(name = "hourly_rate", precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    // Store readable values such as PENDING, rather than fragile numeric enum positions.
    @Enumerated(jakarta.persistence.EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 20)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    // Notes explain an approval or tell the nurse what needs correcting after a rejection.
    @Column(name = "verification_notes", columnDefinition = "TEXT")
    private String verificationNotes;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    // Audit fields are set and maintained automatically by Hibernate.
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // JPA requires a no-argument constructor and the service creates new profiles.
    public NurseProfile() {
    }

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getProfessionalTitle() { return professionalTitle; }
    public void setProfessionalTitle(String professionalTitle) { this.professionalTitle = professionalTitle; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    public Set<String> getSpecialties() { return specialties; }
    public void setSpecialties(Set<String> specialties) { this.specialties = specialties == null ? new HashSet<>() : new HashSet<>(specialties); }
    public Set<String> getServiceAreas() { return serviceAreas; }
    public void setServiceAreas(Set<String> serviceAreas) { this.serviceAreas = serviceAreas == null ? new HashSet<>() : new HashSet<>(serviceAreas); }
    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }
    public VerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(VerificationStatus verificationStatus) { this.verificationStatus = verificationStatus; }
    public String getVerificationNotes() { return verificationNotes; }
    public void setVerificationNotes(String verificationNotes) { this.verificationNotes = verificationNotes; }
    public Instant getVerifiedAt() { return verifiedAt; }
    public void setVerifiedAt(Instant verifiedAt) { this.verifiedAt = verifiedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
