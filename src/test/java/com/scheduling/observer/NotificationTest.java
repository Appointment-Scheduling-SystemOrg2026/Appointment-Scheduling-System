package com.scheduling.observer;

import com.scheduling.domain.entity.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationTest {
	@BeforeEach
	void setup() {
	    EmailNotificationService.setTestMode(true);
	}
    @Test
    void shouldSendEmailReminder() {

        User user = new User("Tasneem", "1234");

        NotificationService service =
                new NotificationService();

        service.notify(
                user,
                "Your appointment is tomorrow at 10 AM"
        );
    }
}
