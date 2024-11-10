package com.gcu.messagingapp.service;

import com.gcu.messagingapp.model.FriendRequest;
import com.gcu.messagingapp.model.FriendRequestStatus;
import com.gcu.messagingapp.model.User;
import com.gcu.messagingapp.repository.FriendRequestRepository;
import com.gcu.messagingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class handling friend-related business logic
 */
@Service
public class FriendService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * DTO class to combine FriendRequest with user information
     */
    public static class FriendRequestDTO {
        private final FriendRequest request;
        private final User requester;

        public FriendRequestDTO(FriendRequest request, User requester) {
            this.request = request;
            this.requester = requester;
        }

        public FriendRequest getRequest() { return request; }
        public User getRequester() { return requester; }
        public String getRequestId() { return request.getId(); }
    }

    /**
     * Sends a friend request from one user to another
     */
    public FriendRequest sendFriendRequest(String senderId, String receiverId) {
        Optional<FriendRequest> existingRequest = friendRequestRepository
                .findBySenderIdAndReceiverId(senderId, receiverId);

        if (existingRequest.isPresent()) {
            throw new RuntimeException("Friend request already exists");
        }

        User sender = userRepository.findById(senderId).orElseThrow();
        if (sender.getFriends().contains(receiverId)) {
            throw new RuntimeException("Users are already friends");
        }

        FriendRequest request = new FriendRequest();
        request.setSenderId(senderId);
        request.setReceiverId(receiverId);
        request.setStatus(FriendRequestStatus.PENDING);
        return friendRequestRepository.save(request);
    }

    /**
     * Handles accepting or rejecting a friend request
     */
    public FriendRequest handleFriendRequest(String requestId, boolean accept) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (accept) {
            request.setStatus(FriendRequestStatus.ACCEPTED);

            User sender = userRepository.findById(request.getSenderId()).orElseThrow();
            User receiver = userRepository.findById(request.getReceiverId()).orElseThrow();

            sender.getFriends().add(request.getReceiverId());
            receiver.getFriends().add(request.getSenderId());

            userRepository.save(sender);
            userRepository.save(receiver);
        } else {
            request.setStatus(FriendRequestStatus.REJECTED);
        }

        return friendRequestRepository.save(request);
    }

    /**
     * Gets all pending friend requests for a user
     */
    public List<FriendRequestDTO> getPendingRequestsForUser(String userId) {
        List<FriendRequest> requests = friendRequestRepository
                .findByReceiverIdAndStatus(userId, FriendRequestStatus.PENDING);
        return requests.stream()
                .map(request -> {
                    User sender = userRepository.findById(request.getSenderId()).orElseThrow();
                    return new FriendRequestDTO(request, sender);
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets all sent friend requests by a user
     */
    public List<FriendRequestDTO> getSentRequestsForUser(String userId) {
        List<FriendRequest> requests = friendRequestRepository
                .findBySenderIdAndStatus(userId, FriendRequestStatus.PENDING);
        return requests.stream()
                .map(request -> {
                    User receiver = userRepository.findById(request.getReceiverId()).orElseThrow();
                    return new FriendRequestDTO(request, receiver);
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets list of friends for a user
     */
    public List<User> getFriendsList(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return userRepository.findAllById(user.getFriends()).stream().toList();
    }

    /**
     * Gets a specific friend by ID
     */
    public User getFriendById(String friendId) {
        return userRepository.findById(friendId).orElse(null);
    }
}