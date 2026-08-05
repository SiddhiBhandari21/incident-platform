package com.siddhi.incident_platform.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddIncidentCommentRequest {
    private String comment;

    private Long commentedByUserId;

}
