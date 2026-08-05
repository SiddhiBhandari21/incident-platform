package com.siddhi.incident_platform.dto;

import com.siddhi.incident_platform.enums.NotificationType;
import lombok.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private long Id;

    private NotificationType notificationType;

    private String message;

    private Long incidentId;

    private UserResponse recipient;

    private Boolean readStatus;

    private LocalDateTime createdAt;

}
