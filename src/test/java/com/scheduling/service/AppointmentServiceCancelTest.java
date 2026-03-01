package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceCancelTest {

    @Test
    void shouldCancelFutureAppointment() {

        AppointmentRepository repo =
                new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of());

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now().plusDays(1),
                        30,1,new InPersonAppointment());

        repo.save(appointment);

        boolean result =
                service.cancelAppointment(appointment);

        assertTrue(result);
        assertTrue(repo.findAll().isEmpty());
    }
}

