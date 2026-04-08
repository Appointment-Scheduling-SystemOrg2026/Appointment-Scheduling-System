package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Strategy for enforcing maximum participant limit for appointments.
 *
 * <p><b>User Story US2.3:</b> Enforce participant limit.</p>
 *
 * <p><b>Acceptance Criteria:</b></p>
 * <ul>
 *   <li>Maximum number of participants enforced</li>
 *   <li>Error shown if limit exceeded</li>
 * </ul>
 *
 * <p>This strategy also considers type-specific participant limits (US5.2).</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public class ParticipantLimitStrategy implements BookingRuleStrategy {

    /** The maximum allowed number of participants. */
    private final int maxParticipants;

    /**
     * Creates a ParticipantLimitStrategy with a specified maximum number of participants.
     *
     * @param maxParticipants The maximum allowed number of participants for the appointment.
     */
    public ParticipantLimitStrategy(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    /**
     * Creates a ParticipantLimitStrategy with a default maximum of 10 participants.
     * This constructor is useful when no specific limit is provided.
     */
    public ParticipantLimitStrategy() {
        this(10); // Default maximum of 10 participants
    }

    /**
     * Validates the number of participants for the given appointment.
     * This method checks that the number of participants does not exceed the allowed limit
     * and that the type-specific participant limit is also respected.
     *
     * @param appointment The appointment to validate
     * @return true if the appointment's participant count is valid, false otherwise
     */
    @Override
    public boolean isValid(Appointment appointment) {
        int participants = appointment.getParticipants();

        // Check if participants are within the allowed range
        if (participants <= 0 || participants > maxParticipants) {
            return false; // Invalid if participants are out of range
        }

        // Check if participants exceed the type-specific limit
        int typeMax = appointment.getType().getMaxParticipants();
        if (participants > typeMax) {
            System.out.println("⚠️ Participants exceed type limit: " + typeMax);
            return false; // Invalid if exceeding type-specific limit
        }

        return true; // Valid if all checks pass
    }

    /**
     * Returns a description of the participant limit rule.
     * This description provides the maximum participant count enforced by this rule.
     *
     * @return A string description of the rule
     */
    @Override
    public String getDescription() {
        return "Maximum participants: " + maxParticipants;
    }

    /**
     * Returns the maximum allowed number of participants for the appointment.
     *
     * @return The maximum number of participants
     */
    public int getMaxParticipants() {
        return maxParticipants;
    }
}