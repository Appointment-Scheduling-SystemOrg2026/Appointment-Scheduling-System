package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.Test;

class ObserverCoverageTest {

    @Test
    void shouldExecuteNotifyWithoutError() {

        NotificationService service =
                new NotificationService();

        User user = new User("test@mail.com","123");

        service.notify(user,"Test message");
    }
}