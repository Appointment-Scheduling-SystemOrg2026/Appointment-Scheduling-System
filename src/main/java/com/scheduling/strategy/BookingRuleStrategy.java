package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Strategy interface for booking rules.
 * This interface defines the contract for implementing various booking rules
 * that can be applied to appointments, providing flexibility to the system.
 * 
 * <p>Part of the Strategy Pattern for flexible business rules.</p>
 * <p>This allows different rules to be applied based on appointment type without modifying core booking logic.</p>
 *
 * <p><b>User Story US5.2:</b> Allows different rules to be applied based on appointment type
 * without modifying core booking logic. Each specific rule can implement its validation logic independently.</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public interface BookingRuleStrategy {

    /**
     * Validates an appointment against this rule.
     * The implementation of this method should include the logic to check
     * whether the appointment satisfies the conditions defined by the specific rule.
     *
     * @param appointment the appointment to validate
     * @return true if the appointment is valid according to the rule, false otherwise
     */
    boolean isValid(Appointment appointment);

    /**
     * Returns a description of this rule.
     * The description provides details on the specific conditions that the rule checks
     * or any business logic it enforces for an appointment.
     *
     * @return a string description of the rule
     */
    String getDescription();
}