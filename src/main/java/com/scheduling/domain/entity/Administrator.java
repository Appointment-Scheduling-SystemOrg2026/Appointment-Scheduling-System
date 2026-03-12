package com.scheduling.domain.entity;

/**
 * Administrator user with management privileges.
 *
 * @author Tasneem
 * @version 1.0
 */
public class Administrator extends User {

    public Administrator(String username, String password) {
        super(username, password);
    }
}



/*
public class Administrator extends User {

    private boolean loggedIn = false;

    public Administrator(String username, String password) {
        super(username, password);
    }

    public boolean login(String username, String password) {
        loggedIn = super.login(username, password);
        return loggedIn;
    }

    public void logout() {
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
*/
