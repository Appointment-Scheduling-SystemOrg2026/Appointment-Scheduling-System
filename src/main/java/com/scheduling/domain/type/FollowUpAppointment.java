package com.scheduling.domain.type;

/**
 * Represents a follow-up appointment after initial treatment.
 *
 * <p><b>User Story US5.2:</b> Follow-up appointments have:</p>
 * <ul>
 *   <li>Standard duration (up to 45 minutes)</li>
 *   <li>Limited participants</li>
 *   <li>Focus on progress review</li>
 * </ul>
 *
 * @author Tasneem
 * @version 1.0
 */
public class FollowUpAppointment implements AppointmentType {

    @Override
    public String getDescription() {
        return "Post-treatment follow-up and progress review";
    }

    @Override
    public int getMaxDuration() {
        return 45;
    }

    @Override
    public int getMaxParticipants() {
        return 2;
    }
}