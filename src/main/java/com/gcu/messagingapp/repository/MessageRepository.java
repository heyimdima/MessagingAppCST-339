package com.gcu.messagingapp.repository;

import com.gcu.messagingapp.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Repository interface for Message entity.
 * Provides CRUD operations and custom queries for Message documents in MongoDB.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
public interface MessageRepository extends MongoRepository<Message, String> {
    /**
     * Finds all messages received by a specific user, ordered by timestamp
     * 
     * @param receiverId ID of the receiving user
     * @return List of messages ordered by timestamp
     */
    List<Message> findByReceiverIdOrderByTimestampAsc(String receiverId);

    /**
     * Finds all messages between two specific users, ordered by timestamp
     * 
     * @param senderId ID of the sending user
     * @param receiverId ID of the receiving user
     * @return List of messages between the users
     */
    List<Message> findBySenderIdAndReceiverIdOrderByTimestampAsc(String senderId, String receiverId);
}