package com.harsh.nursebookingsystem.availability;

import com.harsh.nursebookingsystem.availability.domain.NurseAvailability;
import com.harsh.nursebookingsystem.availability.repository.NurseAvailabilityRepository;
import com.harsh.nursebookingsystem.availability.web.*;
import com.harsh.nursebookingsystem.nurse.domain.NurseProfile;
import com.harsh.nursebookingsystem.nurse.repository.NurseProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
public class AvailabilityService {
    private final NurseAvailabilityRepository availability;
    private final NurseProfileRepository nurses;
    public AvailabilityService(NurseAvailabilityRepository availability, NurseProfileRepository nurses) { this.availability = availability; this.nurses = nurses; }

    @Transactional(readOnly = true)
    public List<AvailabilityPeriodResponse> get(UUID nurseId) { return availability.findByNurseProfileIdOrderByDayOfWeek(nurseId).stream().map(AvailabilityPeriodResponse::from).toList(); }

    public List<AvailabilityPeriodResponse> replace(UUID nurseId, UpdateAvailabilityRequest request) {
        NurseProfile nurse = nurses.findById(nurseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nurse profile not found"));
        Set<java.time.DayOfWeek> days = new HashSet<>();
        for (AvailabilityPeriodRequest period : request.periods()) {
            if (!period.startTime().isBefore(period.endTime())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Availability start time must be before end time");
            if (!days.add(period.dayOfWeek())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only one availability period is allowed per day");
        }
        availability.deleteByNurseProfileId(nurseId);
        List<NurseAvailability> values = request.periods().stream().map(period -> new NurseAvailability(nurse, period.dayOfWeek(), period.startTime(), period.endTime())).toList();
        return availability.saveAll(values).stream().map(AvailabilityPeriodResponse::from).toList();
    }
}
