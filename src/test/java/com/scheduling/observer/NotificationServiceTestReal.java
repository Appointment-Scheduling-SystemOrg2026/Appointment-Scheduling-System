/*package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTestReal {

    @Test
    void shouldSendNotificationWithoutCrash() {

        NotificationService service =
                new NotificationService();

        User user =
                new User("test@email.com", "1234");

        assertDoesNotThrow(() ->
                service.notify(user, "Hello")
        );
    }
}*/