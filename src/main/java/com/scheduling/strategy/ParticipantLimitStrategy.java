package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Validates participant limit.
 */
public class ParticipantLimitStrategy
        implements BookingRuleStrategy {

    private static final int MAX_PARTICIPANTS = 5;

    @Override
    public boolean isValid(Appointment appointment) {
        return appointment.getParticipants()
                <= MAX_PARTICIPANTS;
    }
}