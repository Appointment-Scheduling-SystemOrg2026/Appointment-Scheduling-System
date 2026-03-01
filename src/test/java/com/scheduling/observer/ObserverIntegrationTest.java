package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import com.scheduling.service.ReminderService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class ObserverIntegrationTest {

    @Test
    void shouldNotifyAllObservers() {

        Observer observer1 = mock(Observer.class);
        Observer observer2 = mock(Observer.class);

        ReminderService reminderService =
                new ReminderService(List.of(observer1, observer2));

        User user = new User("user", "1234");

        reminderService.notifyObservers(user, "Reminder");

        verify(observer1).notify(user, "Reminder");
        verify(observer2).notify(user, "Reminder");
    }
}