package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Strategy for enforcing the maximum appointment duration.
 *
 * <p><b>User Story US2.2:</b> Enforce visit duration rule.</p>
 *
 * <p><b>Acceptance Criteria:</b></p>
 * <ul>
 *   <li>Maximum duration enforced (e.g., 1/2 hours)</li>
 *   <li>Invalid durations are rejected</li>
 * </ul>
 *
 * <p>This strategy also considers type-specific duration limits (US5.2).</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public class DurationRuleStrategy implements BookingRuleStrategy {

    /** The maximum allowed duration for the appointment in minutes. */
    private final int maxDurationMinutes;

    /**
     * Creates a DurationRuleStrategy with the specified maximum duration.
     *
     * @param maxDurationMinutes The maximum allowed duration for the appointment in minutes.
     */
    public DurationRuleStrategy(int maxDurationMinutes) {
        this.maxDurationMinutes = maxDurationMinutes;
    }

    /**
     * Creates a DurationRuleStrategy with a default limit of 120 minutes (2 hours).
     * This constructor is useful when no specific limit is provided.
     */
    public DurationRuleStrategy() {
        this(120); // Default maximum duration of 120 minutes (2 hours)
    }

    /**
     * Validates the appointment duration.
     * This method checks that the appointment's duration is within the allowed global limit
     * and also ensures it doesn't exceed the type-specific duration limit.
     *
     * @param appointment The appointment to validate
     * @return true if the appointment duration is valid, false otherwise
     */
    @Override
    public boolean isValid(Appointment appointment) {
        int duration = appointment.getDuration();

        // Check if the duration is within the global maximum limit
        if (duration <= 0 || duration > maxDurationMinutes) {
            return false; // Invalid if the duration is out of the allowed range
        }

        // Check if the duration exceeds the type-specific maximum limit (US5.2)
        int typeMax = appointment.getType().getMaxDuration();
        if (duration > typeMax) {
            System.out.println("⚠️ Duration exceeds type limit: " + typeMax + " minutes");
            return false; // Invalid if the duration exceeds the type limit
        }

        return true; // Valid if all checks pass
    }

    /**
     * Returns a description of the duration rule.
     * This description provides the maximum duration enforced by this rule.
     *
     * @return A string description of the rule
     */
    @Override
    public String getDescription() {
        return "Maximum duration: " + maxDurationMinutes + " minutes";
    }

    /**
     * Returns the maximum allowed duration for appointments.
     *
     * @return The maximum allowed duration in minutes
     */
    public int getMaxDuration() {
        return maxDurationMinutes;
    }
}