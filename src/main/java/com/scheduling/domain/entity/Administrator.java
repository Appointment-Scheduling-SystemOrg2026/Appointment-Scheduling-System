package com.scheduling.domain.entity;

import java.util.function.BooleanSupplier;

/**
 * Represents an Administrator in the scheduling system.
 * This class extends the User class and provides additional functionality specific to administrators.
 * It overrides the {@link User#isAdmin()} method to always return true, indicating the user is an administrator.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class Administrator extends User {

    /**
     * Constructs an Administrator object with the specified username and password.
     * This constructor calls the superclass constructor to initialize the username and password.
     * 
     * @param username The username of the administrator.
     * @param password The password of the administrator.
     */
    public Administrator(String username, String password) {
        super(username, password);
    }

    /**
     * Returns a BooleanSupplier that indicates whether this user is an administrator.
     * This method always returns true for the Administrator class.
     *
     * @return A BooleanSupplier that returns true, indicating this is an administrator.
     */
    @Override
    public BooleanSupplier isAdmin() {
        return () -> true;
    }
}

