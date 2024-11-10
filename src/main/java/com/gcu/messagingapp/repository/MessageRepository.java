package com.gcu.messagingapp.repository;

import com.gcu.messagingapp.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Repository interface for Message entity
 * Provides CRUD operations for Message documents in MongoDB
 */
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByReceiverIdOrderByTimestampAsc(String receiverId);
    List<Message> findBySenderIdAndReceiverIdOrderByTimestampAsc(String senderId, String receiverId);
}