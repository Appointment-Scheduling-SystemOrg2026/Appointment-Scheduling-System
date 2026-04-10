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
    
    @Test
    void shouldConfirmAppointment() {
        Appointment a = new Appointment(
                LocalDateTime.now(),
                30,
                1,
                new InPersonAppointment()
        );

        a.confirm();

        assertEquals(AppointmentStatus.CONFIRMED, a.getStatus());
    }

    @Test
    void shouldRescheduleAppointment() {
        Appointment a = new Appointment(
                LocalDateTime.now(),
                30,
                1,
                new InPersonAppointment()
        );

        LocalDateTime newDate = LocalDateTime.now().plusDays(2);
        a.reschedule(newDate);

        assertEquals(newDate, a.getDateTime());
    }

    @Test
    void shouldUpdateFieldsUsingSetters() {
        Appointment a = new Appointment(
                LocalDateTime.now(),
                30,
                1,
                new InPersonAppointment()
        );

        a.setDuration(60);
        a.setParticipants(5);

        assertEquals(60, a.getDuration());
        assertEquals(5, a.getParticipants());
    }

    @Test
    void shouldReturnTrueWhenFuture() {
        Appointment a = new Appointment(
                LocalDateTime.now().plusDays(1),
                30,
                1,
                new InPersonAppointment()
        );

        assertTrue(a.isFuture().getAsBoolean());
    }

    @Test
    void shouldReturnFalseWhenPast() {
        Appointment a = new Appointment(
                LocalDateTime.now().minusDays(1),
                30,
                1,
                new InPersonAppointment()
        );

        assertFalse(a.isFuture().getAsBoolean());
    }

    @Test
    void shouldReturnStringRepresentation() {
        Appointment a = new Appointment(
                LocalDateTime.now(),
                30,
                1,
                new InPersonAppointment()
        );

        String result = a.toString();

        assertTrue(result.contains("Appointment"));
    }
}