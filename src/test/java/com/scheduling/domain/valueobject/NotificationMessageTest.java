package com.scheduling.domain.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationMessageTest {

    @Test
    void shouldCreateMessage() {

        NotificationMessage msg =
                new NotificationMessage("Hello");

        assertEquals("Hello", msg.getMessage());
    }
}