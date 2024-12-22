package com.example.lms.service;

import com.example.lms.model.Notification;
import com.example.lms.model.user_related.User;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationsByUser(User user);
    List<Notification> getUnreadNotificationsByUser(User user);
    void markAsRead(int notificationId);
    void sendNotification( User user, String message) ;

}