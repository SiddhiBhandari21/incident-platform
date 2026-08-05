package com.siddhi.incident_platform.dto;

import com.siddhi.incident_platform.enums.SourceType;
import com.siddhi.incident_platform.enums.Severity;
import com.siddhi.incident_platform.enums.IncidentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentResponse {

    private Long Id;

    private String title;

    private String description;

    private Severity severity;

    private IncidentStatus incidentStatus;

    private SourceType sourceType;

    private String externalId;

    private String affectedAsset;

    private String detectionLink;

    private UserResponse assignedTo;

    private UserResponse createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime resolvedAt;

    private LocalDateTime closedAt;
}
