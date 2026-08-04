package com.siddhi.incident_platform.repository;

import com.siddhi.incident_platform.entity.User;
import com.siddhi.incident_platform.entity.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientOrderByCreatedAtDesc(User recipient);

    List<Notification> findByRecipientAndReadStatusOrderByCreatedAtDesc(User recipient, Boolean readStatus);

}
