package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.VirtualAppointment;

class DurationRuleStrategyTest {

    @Test
    void shouldAcceptValidDuration() {

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        60,
                        1,
                        new InPersonAppointment()
                );

        DurationRuleStrategy rule =
                new DurationRuleStrategy();

        assertTrue(rule.isValid(appointment));
    }

    @Test
    void shouldRejectInvalidDuration() {

        Appointment appointment =
                new Appointment(
                        LocalDateTime.now(),
                        300,
                        1,
                        new InPersonAppointment()
                );

        DurationRuleStrategy rule =
                new DurationRuleStrategy();

        assertFalse(rule.isValid(appointment));
    }
}

