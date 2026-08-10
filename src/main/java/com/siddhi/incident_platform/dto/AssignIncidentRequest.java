package com.siddhi.incident_platform.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignIncidentRequest {

    @NotNull(message = "Assigned to user ID is required")
    private Long assignedToUserId;

    @NotNull(message = "Assigned by user ID is required")
    private Long assignedByUserId;
}
