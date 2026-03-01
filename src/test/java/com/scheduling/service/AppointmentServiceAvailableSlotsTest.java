package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.AppointmentStatus;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceAvailableSlotsTest {

    @Test
    void shouldShowOnlyAvailableSlots() {

        AppointmentRepository repo =
                new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of());

        Appointment available =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        1,
                        new InPersonAppointment()
                );

        Appointment booked =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        1,
                        new InPersonAppointment()
                );

        booked.setStatus(AppointmentStatus.CONFIRMED);

        repo.save(available);
        repo.save(booked);

        assertEquals(1,
                service.viewAvailableSlots().size());
    }
}