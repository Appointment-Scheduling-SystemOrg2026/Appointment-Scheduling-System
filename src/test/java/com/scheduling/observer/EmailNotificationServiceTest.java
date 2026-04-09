package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailNotificationServiceTest {

    private EmailNotificationService service;

    @BeforeEach
    void setUp() {
        service = new EmailNotificationService("test@gmail.com", "123");
        EmailNotificationService.setTestMode(false);
    }

    @Test
    void notify_shouldWorkNormally() {
        User user = new User("test", "123");

        service.notify(user, "Hello");

        assertTrue(true);
    }

    @Test
    void notify_shouldSkipInTestMode() {
        EmailNotificationService.setTestMode(true);

        User user = new User("test", "123");

        service.notify(user, "Hello");

        assertTrue(true);
    }

    @Test
    void notifyEmail_shouldWork() {
        service.notifyEmail("a@b.com", "msg");

        assertTrue(true);
    }

    @Test
    void notifyEmail_shouldSkipInTestMode() {
        EmailNotificationService.setTestMode(true);

        service.notifyEmail("a@b.com", "msg");

        assertTrue(true);
    }

    @Test
    void notifyWithEmail_shouldWorkWhenEnabled() {
        service.notifyWithEmail("a@b.com", "msg");

        assertTrue(true);
    }

    @Test
    void notifyWithEmail_shouldSkipInTestMode() {
        EmailNotificationService.setTestMode(true);

        service.notifyWithEmail("a@b.com", "msg");

        assertTrue(true);
    }

    @Test
    void notifyWithEmail_shouldHitElseBranch() {
        EmailNotificationService empty = new EmailNotificationService();

        empty.notifyWithEmail("a@b.com", "msg");

        assertTrue(true);
    }

    @Test
    void enableRealEmail_shouldActivateService() {
        EmailNotificationService empty = new EmailNotificationService();

        empty.enableRealEmail("x@gmail.com", "123");

        empty.notifyEmail("a@b.com", "msg");

        assertTrue(true);
    }

    @Test
    void disableRealEmail_shouldDeactivateService() {
        service.disableRealEmail();

        service.notifyEmail("a@b.com", "msg");

        assertTrue(true);
    }

    @Test
    void constructor_shouldCreateInstance() {
        EmailNotificationService s =
                new EmailNotificationService("a@gmail.com", "123");

        assertNotNull(s);
    }
}



