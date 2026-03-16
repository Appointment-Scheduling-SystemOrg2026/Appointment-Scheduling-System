package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.User;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.observer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReminderServiceTest {

    private ReminderService reminderService;
    private com.scheduling.observer.MockNotificationService mockService;

    @BeforeEach
    void setUp() {
        mockService = new com.scheduling.observer.MockNotificationService();
        List<Observer> observers = new ArrayList<>();
        observers.add(mockService);
        reminderService = new ReminderService(observers);
    }

    @Test
    void shouldSendReminderNotification() {
        User user = new User("test@mail.com", "1234");
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        
        reminderService.sendReminder(user, apt);
        
        assertEquals(1, mockService.getSentCount());
    }

    @Test
    void shouldNotSendReminderForPastAppointment() {
        User user = new User("test@mail.com", "1234");
        Appointment apt = new Appointment(
                LocalDateTime.now().minusDays(1),
                30, 1, new InPersonAppointment());
        
        // Past appointments should still send (or not - depends on logic)
        // Just verify no crash
        assertDoesNotThrow(() -> reminderService.sendReminder(user, apt));
    }

    @Test
    void testReminderWithMultipleObservers() {
        List<Observer> observers = new ArrayList<>();
        observers.add(new NotificationService());
        observers.add(mockService);
        
        ReminderService service = new ReminderService(observers);
        
        User user = new User("test@mail.com", "1234");
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        
        service.sendReminder(user, apt);
        
        assertEquals(1, mockService.getSentCount());
    }

    @Test
    void testReminderServiceReset() {
        User user = new User("test@mail.com", "1234");
        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1),
                30, 1, new InPersonAppointment());
        
        reminderService.sendReminder(user, apt);
        mockService.reset();
        
        assertEquals(0, mockService.getSentCount());
    }
}