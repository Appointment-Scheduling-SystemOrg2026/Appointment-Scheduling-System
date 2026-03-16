package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Strategy interface for booking rules.
 *
 * <p>Part of the Strategy Pattern for flexible business rules.</p>
 *
 * <p><b>User Story US5.2:</b> Allows different rules to be applied
 * based on appointment type without modifying core booking logic.</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public interface BookingRuleStrategy {

    /**
     * Validates an appointment against this rule.
     *
     * @param appointment the appointment to validate
     * @return true if valid, false otherwise
     */
    boolean isValid(Appointment appointment);

    /**
     * Returns a description of this rule.
     *
     * @return rule description
     */
    String getDescription();
}