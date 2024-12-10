package com.gcu.messagingapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Message entity class representing a message between users in the system.
 * Stores message content, sender/receiver information, and timestamp.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private String content;
    private LocalDateTime timestamp;

    /**
     * Default constructor that initializes timestamp to current time
     */
    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Gets the message's unique identifier
     * @return The message ID
     */
    public String getId() { return id; }

    /**
     * Sets the message's unique identifier
     * @param id The ID to set
     */
    public void setId(String id) { this.id = id; }

    /**
     * Gets the sender's user ID
     * @return The sender's ID
     */
    public String getSenderId() { return senderId; }

    /**
     * Sets the sender's user ID
     * @param senderId The sender's ID to set
     */
    public void setSenderId(String senderId) { this.senderId = senderId; }

    /**
     * Gets the receiver's user ID
     * @return The receiver's ID
     */
    public String getReceiverId() { return receiverId; }

    /**
     * Sets the receiver's user ID
     * @param receiverId The receiver's ID to set
     */
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }

    /**
     * Gets the message content
     * @return The message content
     */
    public String getContent() { return content; }

    /**
     * Sets the message content
     * @param content The content to set
     */
    public void setContent(String content) { this.content = content; }

    /**
     * Gets the timestamp of the message
     * @return The timestamp
     */
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * Sets the timestamp of the message
     * @param timestamp The timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}