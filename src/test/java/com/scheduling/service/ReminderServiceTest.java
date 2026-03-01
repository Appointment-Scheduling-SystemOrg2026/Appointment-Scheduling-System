package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.User;
import com.scheduling.observer.Observer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;

/**
 * Tests reminder notifications using Mockito.
 */
class ReminderServiceTest {

    @Test
    void shouldSendReminderNotification() {

        Observer mockObserver = mock(Observer.class);

        ReminderService reminderService =
                new ReminderService(List.of(mockObserver));

        User user = new User("user", "1234");

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusHours(1),
                        30,
                        1,
                        new InPersonAppointment()
                );

        reminderService.sendReminder(user, appointment);

        verify(mockObserver)
                .notify(eq(user), contains("Reminder"));
    }
    @Test
    void shouldNotSendReminderForPastAppointment() {

        Observer observer = mock(Observer.class);

        ReminderService service =
                new ReminderService(List.of(observer));

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().minusHours(2),
                        30,
                        1,
                        new InPersonAppointment()
                );

        service.sendReminder(
                new User("u","p"),
                appointment);

        verify(observer, never())
                .notify(any(), any());
    }
}

