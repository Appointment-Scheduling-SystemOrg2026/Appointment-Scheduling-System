package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {

    static class TestObserver implements Observer {

        User receivedUser;
        String receivedMessage;

        @Override
        public void notify(User user, String message) {
            receivedUser = user;
            receivedMessage = message;
        }
    }

    @Test
    void observerShouldReceiveNotification() {

        TestObserver observer = new TestObserver();

        User user = new User("test", "1234");

        observer.notify(user, "Appointment Booked");

        assertEquals("Appointment Booked", observer.receivedMessage);
        assertEquals(user, observer.receivedUser);
    }
}