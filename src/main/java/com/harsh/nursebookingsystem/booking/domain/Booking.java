package com.harsh.nursebookingsystem.booking.domain;

import com.harsh.nursebookingsystem.nurse.domain.NurseProfile;
import com.harsh.nursebookingsystem.user.domain.User;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "bookings", uniqueConstraints = @UniqueConstraint(columnNames = {"nurse_profile_id", "starts_at"}))
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "nurse_profile_id", nullable = false) private NurseProfile nurseProfile;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "patient_user_id", nullable = false) private User patient;
    @Column(name = "starts_at", nullable = false) private LocalDateTime startsAt;
    @Column(name = "ends_at", nullable = false) private LocalDateTime endsAt;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private BookingStatus status = BookingStatus.CONFIRMED;
    @CreationTimestamp @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    protected Booking() { }
    public Booking(NurseProfile nurseProfile, User patient, LocalDateTime startsAt, LocalDateTime endsAt) { this.nurseProfile = nurseProfile; this.patient = patient; this.startsAt = startsAt; this.endsAt = endsAt; }
    public UUID getId() { return id; } public NurseProfile getNurseProfile() { return nurseProfile; } public User getPatient() { return patient; } public LocalDateTime getStartsAt() { return startsAt; } public LocalDateTime getEndsAt() { return endsAt; } public BookingStatus getStatus() { return status; } public Instant getCreatedAt() { return createdAt; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
