package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.strategy.BookingRuleStrategy;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceModifyTest {

    @Test
    void shouldModifyFutureAppointment() {

        AppointmentRepository repo = new AppointmentRepository();

        BookingRuleStrategy rule = mock(BookingRuleStrategy.class);
        when(rule.isValid(any())).thenReturn(true);

        AppointmentService service =
                new AppointmentService(repo, List.of(rule));

        Appointment original =
                new Appointment(
                        LocalDateTime.now().plusDays(1),
                        30,1,new InPersonAppointment());

        Appointment updated =
                new Appointment(
                        LocalDateTime.now().plusDays(2),
                        45,2,new VirtualAppointment());

        boolean result =
                service.modifyAppointment(original, updated);

        assertTrue(result);
    }
}

