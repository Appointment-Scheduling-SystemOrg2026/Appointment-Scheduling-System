package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
class NotificationCoverageTest {
	@BeforeEach
	void setup() {
	    EmailNotificationService.setTestMode(true);
	}
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