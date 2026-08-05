package com.siddhi.incident_platform.dto;

import com.siddhi.incident_platform.entity.Incident;
import com.siddhi.incident_platform.enums.IncidentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateIncidentStatusRequest {

    private IncidentStatus incidentStatus;

    private Long updatedByUserId;

}
