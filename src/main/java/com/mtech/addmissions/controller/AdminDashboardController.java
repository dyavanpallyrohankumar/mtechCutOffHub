package com.mtech.addmissions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.service.implementation.DashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() throws ResourseNotExist {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/yearly-allotments")
    public ResponseEntity<?> getYearlyAllotments() throws ResourseNotExist {
        return ResponseEntity.ok(dashboardService.getYearlyAllotments());
    }

    @GetMapping("/recent-activity")
    public ResponseEntity<?> getRecentActivity() throws ResourseNotExist {
        return ResponseEntity.ok(dashboardService.getRecentActivity());
    }

    @GetMapping("/data-health")
    public ResponseEntity<?> getDataHealth() throws ResourseNotExist {
        return ResponseEntity.ok(dashboardService.getDataHealth());
    }
}
