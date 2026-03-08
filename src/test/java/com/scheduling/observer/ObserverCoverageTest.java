package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.Test;

class ObserverCoverageTest {

    @Test
    void shouldExecuteNotification() {

        NotificationService service =
                new NotificationService();

        service.notify(
                new User("test@test.com","1234"),
                "Test Message"
        );
    }
}


/*package com.scheduling.observer;

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
}*/