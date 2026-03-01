package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;

class ParticipantLimitStrategyTest {

    @Test
    void shouldAcceptAllowedParticipants() {

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        2,
                        new InPersonAppointment()
                );

        ParticipantLimitStrategy rule =
                new ParticipantLimitStrategy();

        assertTrue(rule.isValid(appointment));
    }

    @Test
    void shouldRejectTooManyParticipants() {

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        30,
                        50,
                        new InPersonAppointment()
                );

        ParticipantLimitStrategy rule =
                new ParticipantLimitStrategy();

        assertFalse(rule.isValid(appointment));
    }
}

