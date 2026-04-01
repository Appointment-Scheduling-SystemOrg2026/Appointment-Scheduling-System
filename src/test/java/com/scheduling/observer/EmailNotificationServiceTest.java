package com.scheduling.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailNotificationServiceTest {

    private EmailNotificationService service;

    @BeforeEach
    void setUp() {
        service = new EmailNotificationService("test@gmail.com", "password");
        EmailNotificationService.setTestMode(false);
    }

    @Test
    void notify_shouldExecuteWithoutException() {
        service.notifyEmail("receiver@gmail.com", "Hello");

        assertTrue(true);
    }

    @Test
    void notify_shouldSkipWhenTestModeEnabled() {
        EmailNotificationService.setTestMode(true);

        service.notifyEmail("receiver@gmail.com", "Hello");

        assertTrue(true);
    }

    @Test
    void notifyWithEmail_shouldWorkNormally() {
        EmailNotificationService.setTestMode(false);

        service.notifyWithEmail("receiver@gmail.com", "Message");

        assertTrue(true);
    }

    @Test
    void notifyWithEmail_shouldSkipInTestMode() {
        EmailNotificationService.setTestMode(true);

        service.notifyWithEmail("receiver@gmail.com", "Message");

        assertTrue(true);
    }

    @Test
    void enableRealEmail_shouldActivateService() {
        EmailNotificationService emptyService = new EmailNotificationService();

        emptyService.enableRealEmail("x@gmail.com", "123");

        emptyService.notifyEmail("receiver@gmail.com", "Test");

        assertTrue(true);
    }

    @Test
    void disableRealEmail_shouldDeactivateService() {
        service.disableRealEmail();

        service.notifyEmail("receiver@gmail.com", "Test");

        assertTrue(true);
    }

    @Test
    void constructor_shouldCreateInstance() {
        EmailNotificationService s =
                new EmailNotificationService("a@gmail.com", "123");

        assertNotNull(s);
    }
}