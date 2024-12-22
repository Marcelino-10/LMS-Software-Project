package com.example.lms.model;

import com.example.lms.model.user_related.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    @JsonIgnore
    private boolean isRead = false;
    private LocalDateTime Timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

//    private int sender;
//
//    public int getSender() {
//        return sender;
//    }
//
//    public  void setSender(int sender) {
//        this.sender = sender;
//    }

    public boolean isRead() {
        return isRead;
    }



}