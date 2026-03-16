package com.scheduling.strategy;

import com.scheduling.domain.entity.Appointment;

/**
 * Strategy for enforcing maximum participant limit.
 *
 * <p><b>User Story US2.3:</b> Enforce participant limit.</p>
 *
 * <p><b>Acceptance Criteria:</b></p>
 * <ul>
 *   <li>Maximum number of participants enforced</li>
 *   <li>Error shown if limit exceeded</li>
 * </ul>
 *
 * <p>Also considers type-specific participant limits (US5.2).</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public class ParticipantLimitStrategy implements BookingRuleStrategy {

    private final int maxParticipants;

    /**
     * Creates a ParticipantLimitStrategy with specified maximum.
     *
     * @param maxParticipants maximum allowed participants
     */
    public ParticipantLimitStrategy(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    /**
     * Creates a ParticipantLimitStrategy with default 10 participants.
     */
    public ParticipantLimitStrategy() {
        this(10);
    }

    @Override
    public boolean isValid(Appointment appointment) {
        int participants = appointment.getParticipants();

       
        if (participants <= 0 || participants > maxParticipants) {
            return false;
        }

        
        int typeMax = appointment.getType().getMaxParticipants();
        if (participants > typeMax) {
            System.out.println("⚠️ Participants exceed type limit: " + typeMax);
            return false;
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Maximum participants: " + maxParticipants;
    }

    /**
     * Returns the maximum allowed participants.
     *
     * @return max participants
     */
    public int getMaxParticipants() {
        return maxParticipants;
    }
}