package com.siddhi.incident_platform.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignIncidentRequest {

    private Long assignedToUserId;

    private Long assignedByUserId;
}
