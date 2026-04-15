package com.scheduling.observer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailSenderTest {

    @Test
    void testConstructor() {
        EmailSender sender = new EmailSender("test@example.com", "password");
        assertNotNull(sender);
    }

    @Test
    void testSendEmail_Failure_InvalidCredentials() {
        
        EmailSender sender = new EmailSender("invalid_user@gmail.com", "wrong_password");

        boolean result = sender.sendEmail("recipient@example.com", "Test Subject", "Test Body");

        assertFalse(result, "Email sending should fail with invalid credentials");
    }
    
    @Test
    void testSendEmail_Failure_NoNetwork() {
        
        EmailSender sender = new EmailSender("test@gmail.com", "password");
        
        
        boolean result = sender.sendEmail("to@example.com", "Subject", "Body");
        
        assertFalse(result);
    }
}
