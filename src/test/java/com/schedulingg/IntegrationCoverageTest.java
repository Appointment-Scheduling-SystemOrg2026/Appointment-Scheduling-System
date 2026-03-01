package com.schedulingg;


import com.scheduling.domain.entity.*;
import com.scheduling.domain.type.*;
import com.scheduling.domain.valueobject.NotificationMessage;
import com.scheduling.observer.NotificationService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class IntegrationCoverageTest {

    @Test
    void shouldTouchAllLayers() {

        User user = new User("test@mail.com", "1234");

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusHours(2),
                        30,
                        1,
                        new InPersonAppointment()
                );

        appointment.confirm();
        appointment.cancel();

        new InPersonAppointment();
        new VirtualAppointment();
        new GroupAppointment();

        NotificationMessage msg =
                new NotificationMessage("Reminder");

        NotificationService service =
                new NotificationService();

        service.notify(user, msg.getMessage());
    }
}