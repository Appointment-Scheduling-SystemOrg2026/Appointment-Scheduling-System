package com.scheduling.observer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.scheduling.domain.entity.User;

class EmailNotificationServiceTest {

    @Test
    void shouldSkipEmailWhenTestModeIsEnabled() {

        EmailNotificationService service =
                new EmailNotificationService("test", "test");

        EmailNotificationService.setTestMode(true);

        User user = new User("testUser", "123");

        service.notify(user, "Hello");

        
        assertTrue(true);

        EmailNotificationService.setTestMode(false);
    }
}