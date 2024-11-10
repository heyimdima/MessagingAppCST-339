package com.gcu.messagingapp.repository;

import com.gcu.messagingapp.model.FriendRequest;
import com.gcu.messagingapp.model.FriendRequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for FriendRequest entity
 * Provides CRUD operations for FriendRequest documents in MongoDB
 */
public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {
    List<FriendRequest> findByReceiverIdAndStatus(String receiverId, FriendRequestStatus status);
    List<FriendRequest> findBySenderIdAndStatus(String senderId, FriendRequestStatus status);
    Optional<FriendRequest> findBySenderIdAndReceiverId(String senderId, String receiverId);
    Optional<FriendRequest> findBySenderIdAndReceiverIdAndStatus(
            String senderId, String receiverId, FriendRequestStatus status);
}