package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Strategy interface for booking rules.
 */
public interface BookingRuleStrategy {

    boolean isValid(Appointment appointment);
}