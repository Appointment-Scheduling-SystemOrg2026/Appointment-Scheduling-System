package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.Test;

class NotificationCoverageTest {

    @Test
    void shouldCallNotification() {

        NotificationService service =
                new NotificationService();

        service.notify(
                new User("test@test.com","1234"),
                "hello"
        );
    }
}