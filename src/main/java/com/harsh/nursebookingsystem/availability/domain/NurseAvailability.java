package com.harsh.nursebookingsystem.availability.domain;

import com.harsh.nursebookingsystem.nurse.domain.NurseProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "nurse_availability", uniqueConstraints = @UniqueConstraint(columnNames = {"nurse_profile_id", "day_of_week"}))
public class NurseAvailability {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nurse_profile_id", nullable = false)
    private NurseProfile nurseProfile;
    @NotNull @Enumerated(EnumType.STRING) @Column(name = "day_of_week", nullable = false, length = 10)
    private DayOfWeek dayOfWeek;
    @NotNull @Column(name = "start_time", nullable = false)
    private LocalTime startTime;
    @NotNull @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    protected NurseAvailability() { }
    public NurseAvailability(NurseProfile nurseProfile, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) { this.nurseProfile = nurseProfile; this.dayOfWeek = dayOfWeek; this.startTime = startTime; this.endTime = endTime; }
    public UUID getId() { return id; }
    public NurseProfile getNurseProfile() { return nurseProfile; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
}
