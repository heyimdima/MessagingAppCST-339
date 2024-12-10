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
    /**
     * Finds friend requests received by a user with specific status
     * @param receiverId ID of the receiving user
     * @param status Status of the requests to find
     * @return List of matching friend requests
     */
    List<FriendRequest> findByReceiverIdAndStatus(String receiverId, FriendRequestStatus status);

    /**
     * Finds friend requests sent by a user with specific status
     * @param senderId ID of the sending user
     * @param status Status of the requests to find
     * @return List of matching friend requests
     */
    List<FriendRequest> findBySenderIdAndStatus(String senderId, FriendRequestStatus status);

    /**
     * Finds a specific friend request between two users
     * @param senderId ID of the sending user
     * @param receiverId ID of the receiving user
     * @return Optional containing the friend request if found
     */
    Optional<FriendRequest> findBySenderIdAndReceiverId(String senderId, String receiverId);

    /**
     * Finds a specific friend request between two users with a specific status
     * @param senderId ID of the sending user
     * @param receiverId ID of the receiving user
     * @param status Status of the request to find
     * @return Optional containing the friend request if found
     */
    Optional<FriendRequest> findBySenderIdAndReceiverIdAndStatus(
            String senderId, String receiverId, FriendRequestStatus status);
}