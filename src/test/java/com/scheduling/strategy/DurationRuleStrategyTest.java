package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.UrgentAppointment;
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
    @Test
    void testTypeSpecificDuration_Urgent() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 
                60, 1, new UrgentAppointment()); // Urgent max is 30
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testTypeSpecificDuration_Virtual() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 
                100, 2, new VirtualAppointment()); // Virtual max is 45
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testGetMaxDuration() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(150);
        assertEquals(150, strategy.getMaxDuration());
    }
}

