package com.scheduling.observer;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.User;
import com.scheduling.domain.type.InPersonAppointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ObserverIntegrationTest {
	@BeforeEach
	void setup() {
	    EmailNotificationService.setTestMode(true);
	}
    @Test
    void testObserverIntegration() {
        List<Observer> observers = new ArrayList<>();
        observers.add(new NotificationService());
        
        NotificationManager manager = new NotificationManager(observers);
        
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        
        assertDoesNotThrow(() -> manager.sendReminderToEmail("test@mail.com", apt));
    }

    @Test
    void testMultipleObserversIntegration() {
        List<Observer> observers = new ArrayList<>();
        com.scheduling.observer.MockNotificationService mock = new com.scheduling.observer.MockNotificationService();
        observers.add(new NotificationService());
        observers.add(mock);
        
        NotificationManager manager = new NotificationManager(observers);
        
        User user = new User("test@mail.com", "1234");
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        
        manager.sendReminder(user, apt);
        
        assertEquals(1, mock.getSentCount());
    }
}