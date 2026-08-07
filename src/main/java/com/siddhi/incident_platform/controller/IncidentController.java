package com.siddhi.incident_platform.controller;

import com.siddhi.incident_platform.dto.IncidentResponse;
import com.siddhi.incident_platform.dto.CreateIncidentRequest;
import com.siddhi.incident_platform.dto.AssignIncidentRequest;
import com.siddhi.incident_platform.service.IncidentService;
import com.siddhi.incident_platform.dto.UpdateIncidentStatusRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    public IncidentResponse createIncident(@RequestBody CreateIncidentRequest request)
    {
        return incidentService.createIncident(request);

    }

    @GetMapping("/{incidentId}")
    public IncidentResponse getIncidentById(@PathVariable Long incidentId)
    {
        return incidentService.getIncidentById(incidentId);
    }

    @GetMapping
    public List<IncidentResponse> getAllIncidents(){
        return incidentService.getAllIncidents();
    }

    @PatchMapping("/{incidentId}/status")
    public IncidentResponse updateIncidentStatus(@PathVariable Long incidentId, @RequestBody UpdateIncidentStatusRequest request)
    {
        return incidentService.updateIncidentStatus(incidentId,request);
    }

    @PatchMapping("/{incidentId}/assign")
    public IncidentResponse assignIncident(@PathVariable Long incidentId, @RequestBody AssignIncidentRequest request)
    {
        return incidentService.assignIncident(incidentId,request);
    }
}
