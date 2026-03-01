
package com.scheduling.service;

import com.scheduling.domain.entity.Administrator;

/**
 * Handles authentication operations.
 */
public class AuthenticationService {

    private Administrator loggedInAdmin;

    public boolean login(Administrator admin,
                         String username,
                         String password) {

        if (admin.getUsername().equals(username)
                && admin.passwordMatches(password)) {

            loggedInAdmin = admin;
            return true;
        }

        return false;
    }

    public void logout() {
        loggedInAdmin = null;
    }

    public boolean isLoggedIn() {
        return loggedInAdmin != null;
    }
    
}







/*
public class AuthenticationService {

    public boolean login(Administrator admin,
                         String username,
                         String password) {

        return admin.login(username, password);
    }

    public void logout(Administrator admin) {
        admin.logout();
    }

    public boolean isLoggedIn(Administrator admin) {
        return admin.isLoggedIn();
    }
}
*/