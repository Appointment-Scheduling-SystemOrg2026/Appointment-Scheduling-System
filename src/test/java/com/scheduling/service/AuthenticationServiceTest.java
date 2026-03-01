package com.scheduling.service;

import com.scheduling.domain.entity.Administrator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AuthenticationService.
 */
class AuthenticationServiceTest {

	@Test
	void shouldLoginSuccessfully() {
	    Administrator admin = new Administrator("admin", "1234");
	    AuthenticationService service = new AuthenticationService();

	    boolean result = service.login(admin, "admin", "1234");

	    assertTrue(result);
	    assertTrue(service.isLoggedIn());
	}

	@Test
	void shouldFailLoginWithWrongPassword() {
	    Administrator admin = new Administrator("admin", "1234");
	    AuthenticationService service = new AuthenticationService();

	    boolean result = service.login(admin, "admin", "wrong");

	    assertFalse(result);
	    assertFalse(service.isLoggedIn());
	}
	@Test
	void shouldLogoutSuccessfully() {
	    Administrator admin = new Administrator("admin", "1234");
	    AuthenticationService service = new AuthenticationService();

	    service.login(admin, "admin", "1234");
	    service.logout();

	    assertFalse(service.isLoggedIn());
	}
	@Test
	void logoutWithoutLogin() {

	    AuthenticationService service =
	            new AuthenticationService();

	    service.logout();

	    assertFalse(service.isLoggedIn());
	}
}