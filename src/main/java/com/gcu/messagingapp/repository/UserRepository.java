package com.gcu.messagingapp.repository;

import com.gcu.messagingapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides CRUD operations and custom queries for User documents in MongoDB.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a user by their username
     * 
     * @param username The username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a username already exists
     * 
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    boolean existsByUsername(String username);
}