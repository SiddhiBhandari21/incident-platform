package com.siddhi.incident_platform.controller;

import com.siddhi.incident_platform.dto.DashboardSummaryResponse;
import com.siddhi.incident_platform.service.DashboardService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService)
    {
        this.dashboardService=dashboardService;
    }

    @GetMapping("/summary")
    public DashboardSummaryResponse getDashboardSummary()
    {
        return dashboardService.getDashboardSummary();
    }
}
