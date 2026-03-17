package com.scheduling.domain.type;

/**
 * Represents an urgent appointment requiring immediate attention.
 *
 * <p><b>User Story US5.2:</b> Urgent appointments have:</p>
 * <ul>
 *   <li>Shorter maximum duration (30 minutes)</li>
 *   <li>Limited to 1 participant</li>
 *   <li>Higher priority in scheduling</li>
 * </ul>
 *
 * @author Tasneem
 * @version 1.0
 */
public class UrgentAppointment implements AppointmentType {

    @Override
    public String getDescription() {
        return "Emergency appointment requiring immediate attention";
    }

    @Override
    public int getMaxDuration() {
        return 30; // Urgent appointments are shorter
    }

    @Override
    public int getMaxParticipants() {
        return 1; // Usually one-on-one for urgent cases
    }

    @Override
    public boolean isUrgent() {
        return true;
    }
}