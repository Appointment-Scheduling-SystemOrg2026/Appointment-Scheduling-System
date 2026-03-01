package com.scheduling.repository;

import com.scheduling.domain.entity.Appointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;

/**
 * Tests for AppointmentRepository.
 */
class AppointmentRepositoryTest {

    @Test
    void shouldSaveAppointment() {

        AppointmentRepository repo =
                new AppointmentRepository();

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        1,
                        new InPersonAppointment()                );

        repo.save(appointment);

        assertEquals(1, repo.findAll().size());
    }

    @Test
    void shouldDeleteAppointment() {

        AppointmentRepository repo =
                new AppointmentRepository();

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        1,
                        new InPersonAppointment()
                );

        repo.save(appointment);
        repo.delete(appointment);

        assertTrue(repo.findAll().isEmpty());
    }
}

