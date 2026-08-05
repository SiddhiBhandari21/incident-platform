package com.siddhi.incident_platform.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentCommentResponse {

    private Long Id;

    private String comment;

    private UserResponse commentedBy;

    private LocalDateTime commentedAt;

}
