package com.scheduling.strategy;

import com.scheduling.domain.entity.*;
import com.scheduling.domain.type.*;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Full coverage tests for Strategy pattern classes.
 */
class StrategyFullCoverageTest {

    @Test
    @DisplayName("DurationRuleStrategy - should accept valid duration")
    void testDurationValid() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 60, 2, new GroupAppointment());

        assertTrue(strategy.isValid(apt));
    }

    @Test
    @DisplayName("DurationRuleStrategy - should reject duration exceeding max")
    void testDurationExceedsMax() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 200, 2, new GroupAppointment());

        assertFalse(strategy.isValid(apt));
    }

    @Test
    @DisplayName("DurationRuleStrategy - should reject zero duration")
    void testDurationZero() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 0, 2, new GroupAppointment());

        assertFalse(strategy.isValid(apt));
    }

    @Test
    @DisplayName("DurationRuleStrategy - should reject negative duration")
    void testDurationNegative() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), -10, 2, new GroupAppointment());

        assertFalse(strategy.isValid(apt));
    }

    @Test
    @DisplayName("DurationRuleStrategy - should accept duration at max")
    void testDurationAtMax() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 120, 2, new GroupAppointment());

        assertTrue(strategy.isValid(apt));
    }

    @Test
    @DisplayName("DurationRuleStrategy - default constructor")
    void testDurationDefaultConstructor() {
        DurationRuleStrategy strategy = new DurationRuleStrategy();
        assertEquals(120, strategy.getMaxDuration());
    }

    @Test
    @DisplayName("DurationRuleStrategy - getDescription")
    void testDurationGetDescription() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(90);
        assertEquals("Maximum duration: 90 minutes", strategy.getDescription());
    }

    @Test
    @DisplayName("DurationRuleStrategy - getMaxDurationMinutes")
    void testDurationGetMaxDuration() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(90);
        assertEquals(90, strategy.getMaxDuration());
    }

    @Test
    @DisplayName("DurationRuleStrategy - test type-specific limit")
    void testDurationTypeSpecificLimit() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(200);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 60, 2, new UrgentAppointment());

        assertFalse(strategy.isValid(apt));
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - should accept valid participants")
    void testParticipantsValid() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 5, new GroupAppointment());

        assertTrue(strategy.isValid(apt));
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - should reject participants exceeding max")
    void testParticipantsExceedsMax() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 15, new GroupAppointment());

        assertFalse(strategy.isValid(apt));
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - should reject zero participants")
    void testParticipantsZero() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 0, new GroupAppointment());

        assertFalse(strategy.isValid(apt));
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - should accept participants at max")
    void testParticipantsAtMax() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 10, new GroupAppointment());

        assertTrue(strategy.isValid(apt));
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - default constructor")
    void testParticipantsDefaultConstructor() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy();
        assertEquals(10, strategy.getMaxParticipants());
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - getDescription")
    void testParticipantsGetDescription() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(15);
        assertEquals("Maximum participants: 15", strategy.getDescription());
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - getMaxParticipants")
    void testParticipantsGetMaxParticipants() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(15);
        assertEquals(15, strategy.getMaxParticipants());
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - test type-specific limit")
    void testParticipantsTypeSpecificLimit() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(50);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 5, new IndividualAppointment());

        assertFalse(strategy.isValid(apt));
    }

    @Test
    @DisplayName("BookingRuleStrategy - polymorphism test")
    void testStrategyPolymorphism() {
        BookingRuleStrategy[] strategies = {
                new DurationRuleStrategy(120),
                new ParticipantLimitStrategy(10)
        };

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 60, 5, new GroupAppointment());

        for (BookingRuleStrategy strategy : strategies) {
            assertTrue(strategy.isValid(apt));
            assertNotNull(strategy.getDescription());
        }
    }

    @Test
    @DisplayName("Should combine multiple strategies")
    void testMultipleStrategies() {
        DurationRuleStrategy durationRule = new DurationRuleStrategy(120);
        ParticipantLimitStrategy participantRule = new ParticipantLimitStrategy(10);

        Appointment validApt = new Appointment(
                LocalDateTime.now().plusDays(1), 60, 5, new GroupAppointment());
        Appointment invalidDuration = new Appointment(
                LocalDateTime.now().plusDays(1), 200, 5, new GroupAppointment());
        Appointment invalidParticipants = new Appointment(
                LocalDateTime.now().plusDays(1), 60, 20, new GroupAppointment());

        assertTrue(durationRule.isValid(validApt));
        assertTrue(participantRule.isValid(validApt));

        assertFalse(durationRule.isValid(invalidDuration));
        assertTrue(participantRule.isValid(invalidDuration));

        assertTrue(durationRule.isValid(invalidParticipants));
        assertFalse(participantRule.isValid(invalidParticipants));
    }

    @Test
    @DisplayName("DurationRuleStrategy - minimum allowed duration")
    void testMinDuration() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 1, 2, new GroupAppointment());

        assertTrue(strategy.isValid(apt));
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - minimum allowed participants")
    void testMinParticipants() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 30, 1, new GroupAppointment());

        assertTrue(strategy.isValid(apt));
    }

    @Test
    @DisplayName("Should test strategy with very large limits")
    void testLargeLimits() {
        DurationRuleStrategy durationStrategy = new DurationRuleStrategy(1000);
        ParticipantLimitStrategy participantStrategy = new ParticipantLimitStrategy(1000);

        Appointment apt = new Appointment(
                LocalDateTime.now().plusDays(1), 120, 10, new GroupAppointment());

        assertTrue(durationStrategy.isValid(apt));
        assertTrue(participantStrategy.isValid(apt));
    }

    @Test
    @DisplayName("DurationRuleStrategy - should test with all appointment types")
    void testDurationAllTypes() {
        DurationRuleStrategy strategy = new DurationRuleStrategy(120);

        AppointmentType[] types = {
                new UrgentAppointment(),
                new FollowUpAppointment(),
                new AssessmentAppointment(),
                new VirtualAppointment(),
                new InPersonAppointment(),
                new IndividualAppointment(),
                new GroupAppointment()
        };

        int[] durations = {30, 45, 90, 45, 60, 60, 120};

        for (int i = 0; i < types.length; i++) {
            Appointment apt = new Appointment(
                    LocalDateTime.now().plusDays(1), durations[i], 1, types[i]);
            assertTrue(strategy.isValid(apt), "Failed for: " + types[i].getClass().getSimpleName());
        }
    }

    @Test
    @DisplayName("ParticipantLimitStrategy - should test with all appointment types")
    void testParticipantsAllTypes() {
        ParticipantLimitStrategy strategy = new ParticipantLimitStrategy(10);

        AppointmentType[] types = {
                new UrgentAppointment(),
                new FollowUpAppointment(),
                new AssessmentAppointment(),
                new VirtualAppointment(),
                new InPersonAppointment(),
                new IndividualAppointment(),
                new GroupAppointment()
        };

        int[] participants = {1, 2, 3, 5, 4, 1, 10};

        for (int i = 0; i < types.length; i++) {
            Appointment apt = new Appointment(
                    LocalDateTime.now().plusDays(1), 30, participants[i], types[i]);
            assertTrue(strategy.isValid(apt), "Failed for: " + types[i].getClass().getSimpleName());
        }
    }
}