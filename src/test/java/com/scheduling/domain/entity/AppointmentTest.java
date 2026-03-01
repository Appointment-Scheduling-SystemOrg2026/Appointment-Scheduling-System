package com.scheduling.domain.entity;

import com.scheduling.domain.type.InPersonAppointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void shouldCreateAppointment() {

        Appointment a =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        2,
                        new InPersonAppointment()
                );

        assertEquals(30, a.getDuration());
        assertEquals(2, a.getParticipants());
    }

    @Test
    void shouldCancelAppointment() {

        Appointment a =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        1,
                        new InPersonAppointment()
                );

        a.cancel();

        assertEquals(
                AppointmentStatus.CANCELLED,
                a.getStatus());
    }
}