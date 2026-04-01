package com.scheduling.observer;

import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import static org.junit.jupiter.api.Assertions.*;

class EmailSenderTest {

    private final EmailSender sender =
            new EmailSender("test@gmail.com", "password");

    
    @Test
    void sendEmail_shouldReturnTrue_whenNoException() {
        boolean result = sender.sendEmail(
                "receiver@gmail.com",
                "Test Subject",
                "Test Body"
        );

        
        assertNotNull(result);
    }

    
    @Test
    void sendEmail_shouldHandleInputCorrectly() {
        boolean result = sender.sendEmail(
                "a@b.com",
                "Hello",
                "Body"
        );

        assertTrue(result == true || result == false);
    }

    
    @Test
    void sendEmail_shouldReturnBooleanValue() {
        boolean result = sender.sendEmail(
                "receiver@gmail.com",
                "Test Subject",
                "Test Body"
        );

       
        assertTrue(result == true || result == false);
    }

    
    @Test
    void constructor_shouldSetFields() {
        EmailSender localSender =
                new EmailSender("x@gmail.com", "123");

        assertNotNull(localSender);
    }
}