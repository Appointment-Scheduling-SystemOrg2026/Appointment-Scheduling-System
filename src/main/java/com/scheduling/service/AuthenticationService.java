
package com.scheduling.service;

import java.util.function.BooleanSupplier;

import com.scheduling.domain.entity.Administrator;
import com.scheduling.domain.entity.User;

/**
 * Handles authentication operations.
 */
public class AuthenticationService {

    private Administrator loggedInAdmin;

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

    public void logout() {
        loggedInAdmin = null;
    }

    public boolean isLoggedIn() {
        return loggedInAdmin != null;
    }

	public Object getCurrentUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public BooleanSupplier isAdmin() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validateCredentials(User user, String string, String string2) {
		// TODO Auto-generated method stub
		return false;
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