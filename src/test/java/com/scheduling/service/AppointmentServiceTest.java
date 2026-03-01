package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.strategy.DurationRuleStrategy;
import com.scheduling.strategy.ParticipantLimitStrategy;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests booking service.
 */
class AppointmentServiceTest {

    @Test
    void shouldBookSuccessfully() {

        AppointmentRepository repo =
                new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(
                        repo,
                        List.of(
                                new DurationRuleStrategy(),
                                new ParticipantLimitStrategy()
                        )
                );

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        60,
                        2,
                        new InPersonAppointment()
                );

        assertTrue(service.book(appointment));
    }

    @Test
    void shouldRejectLongDuration() {

        AppointmentRepository repo =
                new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(
                        repo,
                        List.of(
                                new DurationRuleStrategy(),
                                new ParticipantLimitStrategy()
                        )
                );

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        200,
                        2,
                        new InPersonAppointment()
                );

        assertFalse(service.book(appointment));
    }
    @Test
    void shouldReturnAvailableSlotsOnly() {

        AppointmentRepository repo =
                new AppointmentRepository();

        AppointmentService service =
                new AppointmentService(repo, List.of());

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        1,
                        new InPersonAppointment()
                );

        repo.save(appointment);

        assertEquals(1,
                service.viewAvailableSlots().size());
    }
}

