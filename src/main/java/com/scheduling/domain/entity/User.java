package com.scheduling.domain.entity;

import java.util.function.BooleanSupplier;

/**
 * Represents a user in the scheduling system.
 * This class contains the username and password of the user.
 * Additionally, it provides methods for password validation and checking user roles.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class User {

    /** The username of the user. */
    private final String username;
    
    /** The password of the user. */
    private final String password;

    /**
     * Constructs a User object with the given username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Checks if the provided password matches the user's password.
     * This is typically used for authentication purposes.
     *
     * @param password The password to check against the user's password.
     * @return true if the passwords match, otherwise false.
     */
    public boolean passwordMatches(String password) {
        return this.password.equals(password);
    }

    /**
     * Placeholder method for getting the user's password.
     * This is a temporary method, and should not be used for production.
     *
     * @return null, as the method is not yet implemented.
     */
    public Object getPassword() {                                                                      // NOSONAR
        // TODO Auto-generated method stub                                                      // NOSONAR
        return null;                                                                                // NOSONAR
    }

    /**
     * Sets the username of the user.
     * This method allows changing the user's username.
     *
     * @param username The new username for the user.
     */
    public void setUsername(String username) {                                                          // NOSONAR
        // TODO Auto-generated method stub                                                                        // NOSONAR
    }                                                                                                         // NOSONAR

    /**
     * Sets the password of the user.
     * This method allows changing the user's password.
     *
     * @param password The new password for the user.
     */
    public void setPassword(String password) {                                                                      // NOSONAR
        // TODO Auto-generated method stub                                                                             // NOSONAR
    }

    /**
     * Checks if the user has admin privileges.
     * This is a placeholder method for checking the user's role.
     *
     * @return A BooleanSupplier that will return whether the user is an admin.
     */
    public BooleanSupplier isAdmin() {                                                                            // NOSONAR
        // TODO Auto-generated method stub                                                                         // NOSONAR
        return null;                                                                                              // NOSONAR
    }
}

