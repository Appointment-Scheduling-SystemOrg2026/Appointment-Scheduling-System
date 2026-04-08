package com.scheduling.service;

import java.util.function.BooleanSupplier;

import com.scheduling.domain.entity.Administrator;
import com.scheduling.domain.entity.User;

/**
 * Provides authentication services for the scheduling system.
 * This service handles user login, logout, and checks if a user is logged in.
 * It specifically manages authentication for Administrator users.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class AuthenticationService {

    /** The currently logged-in Administrator. */
    private Administrator loggedInAdmin;

    /**
     * Attempts to log in a user by matching their username and password.
     * If the credentials match, the user is cast to an Administrator and logged in.
     *
     * @param user The user attempting to log in.
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return true if the login is successful, false otherwise.
     */
    public boolean login(User user,
                         String username,
                         String password) {

        if (user.getUsername().equals(username)
                && user.passwordMatches(password)) {

            loggedInAdmin = (Administrator) user;
            return true;
        }

        return false;
    }

    /**
     * Logs out the currently logged-in administrator by setting {@link #loggedInAdmin} to null.
     */
    public void logout() {
        loggedInAdmin = null;
    }

    /**
     * Checks if there is a user currently logged in.
     *
     * @return true if an administrator is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return loggedInAdmin != null;
    }

    /**
     * Returns the currently logged-in user (if any).
     * This method is a placeholder and needs to be implemented.
     *
     * @return The current user object or null if no user is logged in.
     */
    public Object getCurrentUser() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Checks if the current user is an administrator.
     * This method is a placeholder and needs to be implemented.
     *
     * @return A BooleanSupplier representing whether the current user is an admin.
     */
    public BooleanSupplier isAdmin() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Validates the credentials of the provided user.
     * This method is a placeholder and needs to be implemented.
     *
     * @param user The user whose credentials are being validated.
     * @param string An additional parameter (possibly for password).
     * @param string2 Another additional parameter (possibly for username).
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean validateCredentials(User user, String string, String string2) {
        // TODO Auto-generated method stub
        return false;
    }
}