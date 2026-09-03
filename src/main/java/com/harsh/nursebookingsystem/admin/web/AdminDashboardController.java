package com.harsh.nursebookingsystem.admin.web;

import com.harsh.nursebookingsystem.admin.AdminDashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {
    private final AdminDashboardService service;

    public AdminDashboardController(AdminDashboardService service) {
        this.service = service;
    }

    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {
        return service.getDashboard();
    }

    @GetMapping("/patients")
    public List<AdminDashboardResponse.PatientAccountResponse> patients() {
        return service.getDashboard().patients();
    }

    @GetMapping("/nurses")
    public List<AdminDashboardResponse.NurseAccountResponse> nurses() {
        return service.getDashboard().nurses();
    }

    @GetMapping({"/bookings", "/bookings/active"})
    public List<AdminDashboardResponse.ActiveBookingResponse> activeBookings() {
        return service.getDashboard().activeBookings();
    }
}
