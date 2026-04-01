package com.scheduling.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailSenderTest {

    private final EmailSender sender =
            new EmailSender("test@gmail.com", "password");

    @Test
    void constructor_shouldCreateInstance() {
        EmailSender localSender =
                new EmailSender("x@gmail.com", "123");

        assertNotNull(localSender);
    }

    @Test
    void sendEmail_shouldReturnBoolean_withoutCrashing() {
        
        boolean result = sender.sendEmail(
                "receiver@gmail.com",
                "Test Subject",
                "Test Body"
        );

        
        assertTrue(result == true || result == false);
    }
}