package com.example.lms.service;

import com.example.lms.model.Notification;
import com.example.lms.model.user_related.User;
import com.example.lms.repository.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private final NotificationRepo notificationRepo;

    @Override
    public List<Notification> getNotificationsByUser(User user) {
        return notificationRepo.findByUser(user);
    }


    @Override
    public List<Notification> getUnreadNotificationsByUser(User user) {
        return notificationRepo.findByUserAndIsRead(user, false);
    }

    public void markAsRead(int notificationId) {
        Notification notification = notificationRepo.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepo.save(notification);
    }

    @Override
    public void sendNotification(User user, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setRead(false);
        notification.setUser(user);
        notificationRepo.save(notification);
    }

}
