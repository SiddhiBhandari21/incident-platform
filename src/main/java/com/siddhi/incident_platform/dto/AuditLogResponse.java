package com.siddhi.incident_platform.dto;

import com.siddhi.incident_platform.enums.AuditAction;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {

    private Long Id;

    private AuditAction action;

    private String oldValue;

    private String newValue;

    private UserResponse performedBy;

    private LocalDateTime performedAt;

}
