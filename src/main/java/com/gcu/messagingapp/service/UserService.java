package com.gcu.messagingapp.service;

import com.gcu.messagingapp.model.User;
import com.gcu.messagingapp.repository.UserRepository;
import com.gcu.messagingapp.repository.FriendRequestRepository;
import com.gcu.messagingapp.model.FriendRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class handling user-related business logic
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    /**
     * Loads user by username for Spring Security authentication
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Registers a new user
     * @param user User to register
     * @return Saved user
     */
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Gets list of users available to add as friends
     * Excludes current user, existing friends, and users with pending requests
     */
    public List<User> getAvailableUsers(String currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .filter(user -> !user.getId().equals(currentUserId)) // Exclude current user
                .filter(user -> !currentUser.getFriends().contains(user.getId())) // Exclude friends
                .filter(user -> !hasPendingFriendRequest(currentUserId, user.getId())) // Exclude users with pending requests
                .toList();
    }

    /**
     * Checks if there's a pending friend request between users
     */
    private boolean hasPendingFriendRequest(String senderId, String receiverId) {
        return friendRequestRepository.findBySenderIdAndReceiverIdAndStatus(
                senderId, receiverId, FriendRequestStatus.PENDING).isPresent();
    }
}