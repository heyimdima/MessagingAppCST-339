package com.gcu.messagingapp.model;

/**
 * Enum representing the possible states of a friend request
 */
public enum FriendRequestStatus {
    PENDING,    // Request has been sent but not yet acted upon
    ACCEPTED,   // Request has been accepted by the receiver
    REJECTED    // Request has been rejected by the receiver
}