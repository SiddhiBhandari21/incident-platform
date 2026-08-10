package com.siddhi.incident_platform.dto;

import com.siddhi.incident_platform.entity.Incident;
import com.siddhi.incident_platform.enums.IncidentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateIncidentStatusRequest {

    @NotNull(message = "Incident status is required")
    private IncidentStatus incidentStatus;

    @NotNull(message = "Updated by user ID is required")
    private Long updatedByUserId;

}
