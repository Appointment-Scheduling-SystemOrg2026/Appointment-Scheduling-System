package com.scheduling.domain.entity;

import java.util.function.BooleanSupplier;

public class User {

    private final String username;
    private final String password;

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

	public Object getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUsername(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setPassword(String string) {
		// TODO Auto-generated method stub
		
	}

	public BooleanSupplier isAdmin() {
		// TODO Auto-generated method stub
		return null;
	}
}




/*package com.scheduling.domain.entity;

/**
 * Represents a system user.
 *
 * @author Tasneem
 * @version 1.0
 */
/*
public class User {

    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Validates login credentials.
     *
     * @param username input username
     * @param password input password
     * @return true if credentials match
     */
/*
    public boolean login(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }
}
*/
