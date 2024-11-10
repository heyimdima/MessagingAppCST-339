package com.gcu.messagingapp.repository;

import com.gcu.messagingapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

/**
 * Repository interface for User entity
 * Provides CRUD operations for User documents in MongoDB
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}