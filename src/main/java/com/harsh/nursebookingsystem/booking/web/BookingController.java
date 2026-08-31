package com.harsh.nursebookingsystem.booking.web;
import com.harsh.nursebookingsystem.booking.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class BookingController {
    private final BookingService service;
    public BookingController(BookingService service) { this.service = service; }
    @GetMapping("/nurses/{nurseId}/slots")
    public List<AvailableSlotResponse> availableSlots(@PathVariable UUID nurseId, @RequestParam LocalDate from, @RequestParam LocalDate to) { return service.availableSlots(nurseId, from, to); }
    @PostMapping("/nurses/{nurseId}/bookings") @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@PathVariable UUID nurseId, @Valid @RequestBody CreateBookingRequest request) { return service.create(nurseId, request); }
    @GetMapping("/patients/{patientId}/bookings")
    public List<BookingResponse> forPatient(@PathVariable UUID patientId) { return service.forPatient(patientId); }
}
