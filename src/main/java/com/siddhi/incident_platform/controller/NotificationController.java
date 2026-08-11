package com.siddhi.incident_platform.controller;

import com.siddhi.incident_platform.dto.NotificationResponse;
import com.siddhi.incident_platform.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/api/users/{userId}/notifications")
    public List<NotificationResponse> getNotificationByUser(@PathVariable Long userId)
    {
        return notificationService.getNotificationByUser(userId);
    }

    @GetMapping("/api/users/{userId}/notifications/unread")
    public List<NotificationResponse> getUnreadNotificationsByUSer(@PathVariable Long userId)
    {
        return notificationService.getUnreadNotificationsByUser(userId);
    }

    @PatchMapping("/api/notifications/{notificationId}/read")
    public NotificationResponse markNotificationAsRead(@PathVariable Long notificationId)
    {
        return notificationService.markNotificationAsRead(notificationId);
    }

}
