package com.gcu.messagingapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity class that represents a user in the messaging system.
 * Implements UserDetails for Spring Security integration.
 * This class stores user information including credentials and relationships.
 * 
 * @author Dima Bondar and Keelia Mattison
 * @version 1.0
 */
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String displayName;
    private Set<String> friends = new HashSet<>();

    /**
     * Gets the user's unique identifier
     * @return The user's ID
     */
    public String getId() { return id; }

    /**
     * Sets the user's unique identifier
     * @param id The ID to set
     */
    public void setId(String id) { this.id = id; }

    /**
     * Sets the username for the user
     * @param username The username to set
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Sets the password for the user
     * @param password The password to set
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Gets the user's email address
     * @return The user's email
     */
    public String getEmail() { return email; }

    /**
     * Sets the user's email address
     * @param email The email to set
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets the user's display name, falls back to username if not set
     * @return The user's display name or username
     */
    public String getDisplayName() { return displayName != null ? displayName : username; }

    /**
     * Sets the user's display name
     * @param displayName The display name to set
     */
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    /**
     * Gets the set of friend IDs for this user
     * @return Set of friend user IDs
     */
    public Set<String> getFriends() { return friends; }

    /**
     * Sets the friend IDs for this user
     * @param friends Set of friend user IDs to set
     */
    public void setFriends(Set<String> friends) { this.friends = friends; }

    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }
}