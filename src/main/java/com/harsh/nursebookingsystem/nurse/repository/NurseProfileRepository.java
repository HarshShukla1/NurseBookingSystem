package com.harsh.nursebookingsystem.nurse.repository;

import com.harsh.nursebookingsystem.nurse.domain.NurseProfile;
import com.harsh.nursebookingsystem.nurse.domain.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface NurseProfileRepository extends JpaRepository<NurseProfile, UUID> {
    // Spring Data derives the SQL query from this method name. "UserId" follows
    // the NurseProfile.user -> User.id relationship without writing SQL manually.
    Optional<NurseProfile> findByUserId(UUID userId);

    List<NurseProfile> findAllByOrderByCreatedAtDesc();

    // The public directory should expose only nurses approved by an administrator.
    List<NurseProfile> findByVerificationStatus(VerificationStatus verificationStatus);
}
