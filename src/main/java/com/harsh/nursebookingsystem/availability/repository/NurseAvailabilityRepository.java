package com.harsh.nursebookingsystem.availability.repository;

import com.harsh.nursebookingsystem.availability.domain.NurseAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface NurseAvailabilityRepository extends JpaRepository<NurseAvailability, UUID> {
    List<NurseAvailability> findByNurseProfileIdOrderByDayOfWeek(UUID nurseProfileId);
    void deleteByNurseProfileId(UUID nurseProfileId);
}
