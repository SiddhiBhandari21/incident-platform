package com.siddhi.incident_platform.service.impl;

import com.siddhi.incident_platform.entity.Notification;
import com.siddhi.incident_platform.exception.ResourceNotFoundException;
import com.siddhi.incident_platform.mapper.NotificationMapper;
import com.siddhi.incident_platform.service.NotificationService;
import com.siddhi.incident_platform.dto.NotificationResponse;
import com.siddhi.incident_platform.repository.NotificationRepository;
import com.siddhi.incident_platform.repository.UserRepository;
import com.siddhi.incident_platform.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<NotificationResponse> getNotificationByUser(Long userId) {

        User user=userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + userId));

        return notificationRepository.findByRecipientOrderByCreatedAtDesc(user)
                .stream()
                .map(NotificationMapper::toNotificationResponse)
                .toList();
    }

    @Override
    public List<NotificationResponse> getUnreadNotificationsByUser(Long userId) {

        User user=userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + userId));

        return notificationRepository.findByRecipientAndReadStatusOrderByCreatedAtDesc(user,false)
                .stream()
                .map(NotificationMapper::toNotificationResponse)
                .toList();
    }

    @Override
    public NotificationResponse markNotificationAsRead(Long notificationId) {

        Notification notification=notificationRepository.findById(notificationId)
                .orElseThrow(()->new ResourceNotFoundException("Notification not found with id:" + notificationId));

        notification.setReadStatus(true);

        Notification updatedNotification=notificationRepository.save(notification);

        return NotificationMapper.toNotificationResponse(updatedNotification);
    }
}
