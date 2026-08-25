package com.harsh.nursebookingsystem.patient.domain;

import com.harsh.nursebookingsystem.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "patient_profiles")
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Size(max = 255)
    @Column(name = "default_address_line_1", length = 255)
    private String defaultAddressLine1;

    @Size(max = 255)
    @Column(name = "default_address_line_2", length = 255)
    private String defaultAddressLine2;

    @Size(max = 100)
    @Column(name = "default_city", length = 100)
    private String defaultCity;

    @Size(max = 100)
    @Column(name = "default_state", length = 100)
    private String defaultState;

    @Size(max = 20)
    @Column(name = "default_postal_code", length = 20)
    private String defaultPostalCode;

    @Size(max = 200)
    @Column(name = "emergency_contact_name", length = 200)
    private String emergencyContactName;

    @Size(max = 30)
    @Column(name = "emergency_contact_phone", length = 30)
    private String emergencyContactPhone;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected PatientProfile() {
    }

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getDefaultAddressLine1() { return defaultAddressLine1; }
    public void setDefaultAddressLine1(String value) { this.defaultAddressLine1 = value; }
    public String getDefaultAddressLine2() { return defaultAddressLine2; }
    public void setDefaultAddressLine2(String value) { this.defaultAddressLine2 = value; }
    public String getDefaultCity() { return defaultCity; }
    public void setDefaultCity(String value) { this.defaultCity = value; }
    public String getDefaultState() { return defaultState; }
    public void setDefaultState(String value) { this.defaultState = value; }
    public String getDefaultPostalCode() { return defaultPostalCode; }
    public void setDefaultPostalCode(String value) { this.defaultPostalCode = value; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String value) { this.emergencyContactName = value; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String value) { this.emergencyContactPhone = value; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
