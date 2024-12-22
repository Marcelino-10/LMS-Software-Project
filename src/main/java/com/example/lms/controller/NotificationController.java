package com.example.lms.controller;

import com.example.lms.model.Notification;
import com.example.lms.model.user_related.User;
import com.example.lms.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping("/{userId}")
    public List<Notification> getNotificationsByUser(@PathVariable int userId) {
        User user = new User();
        user.setId(userId);

        List<Notification> allNotifications = notificationService.getNotificationsByUser(user);

        for (Notification notification : allNotifications) {
            if (!notification.isRead()) {
                notificationService.markAsRead(notification.getId());
            }
        }

        return allNotifications;
    }
    @GetMapping("/{userId}/unread")
    public List<Notification> getUnreadNotificationsByUser(@PathVariable int userId) {
        User user = new User();
        user.setId(userId);
        List<Notification> unreadNotifications = notificationService.getUnreadNotificationsByUser(user);

        for (Notification notification : unreadNotifications) {
            notificationService.markAsRead(notification.getId());
        }

        return unreadNotifications;
    }
    @PutMapping("/{notificationId}/read")
    public void markAsRead(@PathVariable int notificationId) {
        notificationService.markAsRead(notificationId);
    }

    @PostMapping("/send")
    public void sendNotification(@RequestParam("userId") int userId, @RequestBody String message) {
        User user = new User();
        user.setId(userId);
        notificationService.sendNotification(user, message);
    }
//http://localhost:8080/notifications/send?userId=106 && message => string in Body
}