package com.gcu.messagingapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * FriendRequest entity class representing a friend request between users.
 * Tracks the status of friendship requests in the system.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
@Document(collection = "friendRequests")
public class FriendRequest {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private FriendRequestStatus status;
    private LocalDateTime createdAt;

    /**
     * Default constructor that initializes timestamp and sets status to PENDING
     */
    public FriendRequest() {
        this.createdAt = LocalDateTime.now();
        this.status = FriendRequestStatus.PENDING;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    public FriendRequestStatus getStatus() { return status; }
    public void setStatus(FriendRequestStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}