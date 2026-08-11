package com.siddhi.incident_platform.service;

import com.siddhi.incident_platform.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotificationByUser(Long userId);

    List<NotificationResponse> getUnreadNotificationsByUser(Long userId);

    NotificationResponse markNotificationAsRead(Long notificationId);
}
