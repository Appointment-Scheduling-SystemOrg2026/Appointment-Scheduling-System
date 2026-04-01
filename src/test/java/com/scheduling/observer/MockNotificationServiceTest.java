package com.scheduling.observer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.scheduling.domain.entity.User;

class MockNotificationServiceTest {

    @Test
    void shouldCoverAllMethods() {

        MockNotificationService mock = new MockNotificationService();

        User user = new User("test", "123");

        // trigger notify
        mock.notify(user, "Hello");

        // getters
        assertEquals(1, mock.getSentCount());
        assertEquals(1, mock.getCallCount());
        assertTrue(mock.wasCalled());
        assertEquals("Hello", mock.getLastMessage());

        // log check
        assertTrue(mock.getLog().contains("Hello"));

        // clear / reset coverage
        mock.clear();
        assertEquals(0, mock.getSentCount());

        mock.notify(user, "Again");
        mock.reset();
        assertEquals(0, mock.getSentCount());
    }
}