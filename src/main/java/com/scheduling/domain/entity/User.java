package com.scheduling.domain.entity;

import java.util.function.BooleanSupplier;

/**
 * Represents a user in the scheduling system.
 */
public class User {

    private String username;   
    private String password;   

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean passwordMatches(String password) {
        return this.password.equals(password);
    }

    /**
     * Returns the password (for testing purposes).
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates username.
     */
    public void setUsername(String username) {
        if (username != null && !username.isBlank()) {
            this.username = username;
        }
    }

    /**
     * Updates password.
     */
    public void setPassword(String password) {
        if (password != null && !password.isBlank()) {
            this.password = password;
        }
    }

    /**
     * Checks if user is admin.
     */
    public BooleanSupplier isAdmin() {
        return () -> "admin".equalsIgnoreCase(username);
    }
}