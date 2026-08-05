package com.siddhi.incident_platform.mapper;

import com.siddhi.incident_platform.dto.NotificationResponse;
import com.siddhi.incident_platform.entity.Notification;

public class NotificationMapper {

    public static NotificationResponse toNotificationResponse(Notification notification)
    {
        if(notification==null){
            return null;
        }

        return NotificationResponse.builder()
                .Id(notification.getId())
                .notificationType(notification.getNotificationType())
                .message(notification.getMessage())
                .incidentId(notification.getIncident()!=null ? notification.getIncident().getId(): null )
                .recipient(UserMapper.toUserResponse(notification.getRecipient()))
                .readStatus(notification.getReadStatus())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
