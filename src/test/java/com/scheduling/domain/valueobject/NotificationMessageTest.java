package com.scheduling.domain.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationMessageTest {

    @Test
    void testConstructorWithContent() {
        NotificationMessage msg = new NotificationMessage("Test message");
        assertEquals("Test message", msg.getContent());
        assertEquals("Test message", msg.getMessage());
        assertEquals("unknown", msg.getRecipient());
    }

    @Test
    void testConstructorWithRecipientAndContent() {
        NotificationMessage msg = new NotificationMessage("test@mail.com", "Hello");
        assertEquals("test@mail.com", msg.getRecipient());
        assertEquals("Hello", msg.getContent());
        assertEquals("Hello", msg.getMessage());
    }

    @Test
    void testGetTimestamp() {
        NotificationMessage msg = new NotificationMessage("Test");
        assertTrue(msg.getTimestamp() > 0);
    }

    @Test
    void testToString() {
        NotificationMessage msg = new NotificationMessage("test@mail.com", "Hello");
        String str = msg.toString();
        assertTrue(str.contains("test@mail.com"));
        assertTrue(str.contains("Hello"));
    }
}