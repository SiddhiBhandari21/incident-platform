package com.siddhi.incident_platform.service;

import com.siddhi.incident_platform.dto.CreateIncidentRequest;
import com.siddhi.incident_platform.dto.IncidentResponse;
import com.siddhi.incident_platform.dto.UpdateIncidentStatusRequest;
import com.siddhi.incident_platform.dto.AssignIncidentRequest;
import com.siddhi.incident_platform.dto.AddIncidentCommentRequest;
import com.siddhi.incident_platform.dto.IncidentCommentResponse;
import com.siddhi.incident_platform.dto.AuditLogResponse;
import com.siddhi.incident_platform.enums.IncidentStatus;
import com.siddhi.incident_platform.enums.Severity;


import org.springframework.data.domain.Page;

import java.util.List;

public interface IncidentService {

    IncidentResponse createIncident(CreateIncidentRequest request);

    IncidentResponse getIncidentById(Long incidentId);

    List<IncidentResponse> getAllIncidents();

    IncidentResponse updateIncidentStatus(Long incidentId, UpdateIncidentStatusRequest request);

    IncidentResponse assignIncident(Long incidentId, AssignIncidentRequest request);

    IncidentCommentResponse addComment(Long incidentId, AddIncidentCommentRequest request );

    List<IncidentCommentResponse> getCommentsByIncident(Long incidentId);

    List<AuditLogResponse> getAuditLogsByIncident(Long incidentId);

    List<IncidentResponse> getIncidentByStatus(IncidentStatus incidentStatus);

    List<IncidentResponse> getIncidentBySeverity(Severity severity);

    List<IncidentResponse> getIncidentsByAssignedUserId(Long userId);

    Page<IncidentResponse> getIncidentsWithPagination(int page, int size);
}
