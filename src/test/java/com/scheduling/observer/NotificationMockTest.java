package com.scheduling.observer;

import com.scheduling.domain.entity.User;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class NotificationMockTest {
	@BeforeEach
	void setup() {
	    EmailNotificationService.setTestMode(true);
	}
	@Test
	void shouldRecordLogAndCount() {

	    MockNotificationService mock = new MockNotificationService();

	    User user = new User("Tasneem", "123");

	    mock.notify(user, "Appointment tomorrow");

	    assertEquals(1, mock.getSentCount());
	    assertTrue(mock.wasCalled());
	    assertNotNull(mock.getLog());
	    assertTrue(mock.getLog().contains("Appointment tomorrow"));
	}

  
}