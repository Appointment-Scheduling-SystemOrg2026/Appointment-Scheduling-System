package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Strategy for enforcing maximum appointment duration.
 *
 * <p><b>User Story US2.2:</b> Enforce visit duration rule.</p>
 *
 * <p><b>Acceptance Criteria:</b></p>
 * <ul>
 *   <li>Maximum duration enforced (e.g., 1/2 hours)</li>
 *   <li>Invalid durations are rejected</li>
 * </ul>
 *
 * <p>Also considers type-specific duration limits (US5.2).</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public class DurationRuleStrategy implements BookingRuleStrategy {

    private final int maxDurationMinutes;

    /**
     * Creates a DurationRuleStrategy with specified maximum.
     *
     * @param maxDurationMinutes maximum allowed duration
     */
    public DurationRuleStrategy(int maxDurationMinutes) {
        this.maxDurationMinutes = maxDurationMinutes;
    }

    /**
     * Creates a DurationRuleStrategy with default 120 minute limit.
     */
    public DurationRuleStrategy() {
        this(120);
    }

    @Override
    public boolean isValid(Appointment appointment) {
        int duration = appointment.getDuration();

        // Check global maximum
        if (duration <= 0 || duration > maxDurationMinutes) {
            return false;
        }

        // Check type-specific maximum (US5.2)
        int typeMax = appointment.getType().getMaxDuration();
        if (duration > typeMax) {
            System.out.println("⚠️ Duration exceeds type limit: " + typeMax + " minutes");
            return false;
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Maximum duration: " + maxDurationMinutes + " minutes";
    }

    /**
     * Returns the maximum allowed duration.
     *
     * @return max duration in minutes
     */
    public int getMaxDuration() {
        return maxDurationMinutes;
    }
}