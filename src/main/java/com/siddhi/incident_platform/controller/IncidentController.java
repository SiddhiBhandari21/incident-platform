package com.siddhi.incident_platform.controller;


import com.siddhi.incident_platform.dto.IncidentResponse;
import com.siddhi.incident_platform.dto.CreateIncidentRequest;
import com.siddhi.incident_platform.dto.AssignIncidentRequest;
import com.siddhi.incident_platform.service.IncidentService;
import com.siddhi.incident_platform.dto.UpdateIncidentStatusRequest;
import com.siddhi.incident_platform.dto.IncidentCommentResponse;
import com.siddhi.incident_platform.dto.AddIncidentCommentRequest;
import com.siddhi.incident_platform.dto.AuditLogResponse;

import com.siddhi.incident_platform.enums.IncidentStatus;
import com.siddhi.incident_platform.enums.Severity;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    public IncidentResponse createIncident(@Valid @RequestBody CreateIncidentRequest request)
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

    @GetMapping("/page")
    public Page<IncidentResponse> getIncidentsWithPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size)
    {
        return incidentService.getIncidentsWithPagination(page,size);
    }


    @PatchMapping("/{incidentId}/status")
    public IncidentResponse updateIncidentStatus(@PathVariable Long incidentId, @Valid @RequestBody UpdateIncidentStatusRequest request)
    {
        return incidentService.updateIncidentStatus(incidentId,request);
    }

    @PatchMapping("/{incidentId}/assign")
    public IncidentResponse assignIncident(@PathVariable Long incidentId,@Valid @RequestBody AssignIncidentRequest request)
    {
        return incidentService.assignIncident(incidentId,request);
    }

    @PostMapping("/{incidentId}/comments")
    public IncidentCommentResponse addComment(@PathVariable Long incidentId,@Valid @RequestBody AddIncidentCommentRequest request)
    {
        return incidentService.addComment(incidentId,request);
    }

    @GetMapping("/{incidentId}/comments")
    public List<IncidentCommentResponse> getCommentByIncident(@PathVariable Long incidentId)
    {
        return incidentService.getCommentsByIncident(incidentId);
    }

    @GetMapping("{incidentId}/audit-logs")
    public List<AuditLogResponse> getAuditLogsByIncident(@PathVariable Long incidentId)
    {
        return incidentService.getAuditLogsByIncident(incidentId);
    }

    @GetMapping("/status/{incidentStatus}")
    public List<IncidentResponse> getIncidentByStatus(@PathVariable IncidentStatus incidentStatus)
    {
        return incidentService.getIncidentByStatus(incidentStatus);
    }

    @GetMapping("/severity/{severity}")
    public List<IncidentResponse> getIncidentBySeverity(@PathVariable Severity severity)
    {
        return incidentService.getIncidentBySeverity(severity);
    }

    @GetMapping("/assigned/{userId}")
    public List<IncidentResponse> getIncidentsByAssignedUserId(@PathVariable Long userId){
        return incidentService.getIncidentsByAssignedUserId(userId);
    }


}
