package com.siddhi.incident_platform.service;

import com.siddhi.incident_platform.dto.CreateIncidentRequest;
import com.siddhi.incident_platform.dto.IncidentResponse;

import java.util.List;

public interface IncidentService {

    IncidentResponse createIncident(CreateIncidentRequest request);

    IncidentResponse getIncidentById(Long incidentId);

    List<IncidentResponse> getAllIncidents();

}
