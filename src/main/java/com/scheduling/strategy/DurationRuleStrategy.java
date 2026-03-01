package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Validates appointment duration.
 */
public class DurationRuleStrategy
        implements BookingRuleStrategy {

    private static final int MAX_DURATION = 120;

    @Override
    public boolean isValid(Appointment appointment) {
        return appointment.getDuration()
                <= MAX_DURATION;
    }
}