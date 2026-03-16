package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import com.scheduling.domain.type.GroupAppointment;
import com.scheduling.domain.type.InPersonAppointment;
import com.scheduling.domain.type.IndividualAppointment;
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
    @Test
    void testTypeSpecificLimit_Individual() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 
                60, 2, new IndividualAppointment()); // Individual max is 1
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testTypeSpecificLimit_Group() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);
        Appointment apt = new Appointment(LocalDateTime.now().plusDays(1), 
                60, 15, new GroupAppointment()); // Group max is 10
        assertFalse(strategy.isValid(apt));
    }

    @Test
    void testGetMaxParticipants() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(20);
        assertEquals(20, strategy.getMaxParticipants());
    }
}

