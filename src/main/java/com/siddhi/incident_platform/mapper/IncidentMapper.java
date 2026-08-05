package com.siddhi.incident_platform.mapper;

import com.siddhi.incident_platform.entity.Incident;
import com.siddhi.incident_platform.dto.IncidentResponse;

public class IncidentMapper {

    public static IncidentResponse toIncidentResponse(Incident incident)
    {
        if(incident == null){
            return null;
        }

        return IncidentResponse.builder()
                .Id(incident.getId())
                .title(incident.getTitle())
                .description(incident.getDescription())
                .severity(incident.getSeverity())
                .incidentStatus(incident.getIncidentStatus())
                .sourceType(incident.getSourceType())
                .externalId(incident.getExternalId())
                .affectedAsset(incident.getAffectedAsset())
                .detectionLink(incident.getDetectionLink())
                .assignedTo(UserMapper.toUserResponse(incident.getAssignedTo()))
                .createdBy(UserMapper.toUserResponse(incident.getCreatedBy()))
                .createdAt(incident.getCreatedAt())
                .updatedAt(incident.getUpdatedAt())
                .resolvedAt(incident.getResolvedAt())
                .closedAt(incident.getClosedAt())
                .build();

    }



}
