package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import com.scheduling.service.MockNotificationService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationMockTest {

    @Test
    void shouldRecordReminderMessage() {

        MockNotificationService mock =
                new MockNotificationService();

        User user = new User("Tasneem", "123");

        mock.notify(user, "Appointment tomorrow");

        assertEquals(1, mock.getSentMessages().size());

        assertTrue(
                mock.getSentMessages()
                        .get(0)
                        .contains("Appointment tomorrow")
        );
    }
}