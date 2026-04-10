package com.scheduling.domain.type;

/**
 * Represents a group appointment with multiple participants.
 *
 * <p><b>User Story US5.2:</b> Group appointments have:</p>
 * <ul>
 *   <li>Longer duration (up to 120 minutes)</li>
 *   <li>Multiple participants allowed (up to 10)</li>
 *   <li>Larger venue requirements</li>
 * </ul>
 *
 * @author Tasneem
 * @version 1.0
 */
public class GroupAppointment implements AppointmentType {

    @Override
    public String getDescription() {
        return "Group session with multiple participants";
    }

    @Override
    public int getMaxDuration() {
        return 120;
    }

    @Override
    public int getMaxParticipants() {
        return 10;
    }

    public String getTypeName() {
        return "Group";
    }

	
}