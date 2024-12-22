package com.example.lms.repository;

import com.example.lms.model.Notification;
import com.example.lms.model.user_related.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndIsRead(User user, boolean isRead);
    Optional<Notification> findById(int id);}
