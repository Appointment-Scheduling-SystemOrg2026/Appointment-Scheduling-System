package com.scheduling.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserAndGetData() {
        User user = new User("ahmad", "1234");

        assertEquals("ahmad", user.getUsername());
        assertEquals("1234", user.getPassword());
        assertTrue(user.passwordMatches("1234"));
        assertFalse(user.passwordMatches("wrong"));
    }

    @Test
    void shouldUpdateUsernameSuccessfully() {
        User user = new User("old", "1234");

        user.setUsername("new");

        assertEquals("new", user.getUsername());
    }

    @Test
    void shouldNotUpdateUsernameWhenInvalid() {
        User user = new User("old", "1234");

        user.setUsername("");
        assertEquals("old", user.getUsername());

        user.setUsername(null);
        assertEquals("old", user.getUsername());
    }

    @Test
    void shouldUpdatePasswordSuccessfully() {
        User user = new User("user", "1234");

        user.setPassword("9999");

        assertEquals("9999", user.getPassword());
    }

    @Test
    void shouldNotUpdatePasswordWhenInvalid() {
        User user = new User("user", "1234");

        user.setPassword("");
        assertEquals("1234", user.getPassword());

        user.setPassword(null);
        assertEquals("1234", user.getPassword());
    }

    @Test
    void shouldDetectAdminUser() {
        User admin = new User("admin", "1234");
        assertTrue(admin.isAdmin().getAsBoolean());
    }

    @Test
    void shouldDetectNonAdminUser() {
        User user = new User("user1", "1234");
        assertFalse(user.isAdmin().getAsBoolean());
    }
}