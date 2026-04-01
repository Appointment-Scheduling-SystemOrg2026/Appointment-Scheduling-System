package com.scheduling.domain.entity;

import java.util.function.BooleanSupplier;

public class Administrator extends User {

    public Administrator(String username, String password) {
        super(username, password);
    }

    @Override
    public BooleanSupplier isAdmin() {
        return () -> true;
    }
}



/*package com.scheduling.domain.entity;

import java.util.function.BooleanSupplier;

/**
 * Administrator user with management privileges.
 *
 * @author Tasneem
 * @version 1.0
 */
/*public class Administrator extends User {

    public Administrator(String username, String password) {
        super(username, password);
    }

	public BooleanSupplier isAdmin() {
		// TODO Auto-generated method stub
		return null;
	}
}*/



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
