package com.scheduling.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUser() {
        User user = new User("tasneem", "1234");

        assertEquals("tasneem", user.getUsername());
    }

    @Test
    void shouldMatchPasswordCorrectly() {
        User user = new User("user", "pass");

        assertTrue(user.passwordMatches("pass"));
        assertFalse(user.passwordMatches("wrong"));
    }

    @Test
    void shouldReturnPassword() {
        User user = new User("user", "pass");

        assertEquals("pass", user.getPassword());
    }

    @Test
    void shouldDetectAdminUser() {
        User user = new User("admin", "123");

        assertTrue(user.isAdmin().getAsBoolean());
    }

    @Test
    void shouldDetectNonAdminUser() {
        User user = new User("user", "123");

        assertFalse(user.isAdmin().getAsBoolean());
    }
    @Test
    void shouldUpdateUsernameAndPassword() {
        User user = new User("old", "123");

        user.setUsername("new");
        user.setPassword("456");

        assertEquals("new", user.getUsername());
        assertEquals("456", user.getPassword());
    }
}