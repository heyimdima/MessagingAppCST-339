package com.gcu.messagingapp.service;

import com.gcu.messagingapp.model.Message;
import com.gcu.messagingapp.model.User;
import com.gcu.messagingapp.repository.MessageRepository;
import com.gcu.messagingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

/**
 * Service class handling all message-related business logic.
 * Manages message creation, retrieval, and validation between users.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Sends a new message between users
     * Validates that users are friends before allowing message transmission
     * 
     * @param message The message object containing sender, receiver, and content
     * @return The saved message object
     * @throws RuntimeException if users are not friends
     */
    public Message sendMessage(Message message) {
        User sender = userRepository.findById(message.getSenderId()).orElseThrow();
        if (!sender.getFriends().contains(message.getReceiverId())) {
            throw new RuntimeException("You can only send messages to friends");
        }
        return messageRepository.save(message);
    }

    /**
     * Gets all messages between two users
     * Orders them by timestamp ascending (oldest first)
     */
    public List<Message> getMessagesBetweenUsers(String userId1, String userId2) {
        List<Message> sentMessages = messageRepository
                .findBySenderIdAndReceiverIdOrderByTimestampAsc(userId1, userId2);
        List<Message> receivedMessages = messageRepository
                .findBySenderIdAndReceiverIdOrderByTimestampAsc(userId2, userId1);

        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sentMessages);
        allMessages.addAll(receivedMessages);

        allMessages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        return allMessages;
    }

    /**
     * Gets all messages received by a user
     */
    public List<Message> getMessagesForUser(String userId) {
        return messageRepository.findByReceiverIdOrderByTimestampAsc(userId);
    }
}