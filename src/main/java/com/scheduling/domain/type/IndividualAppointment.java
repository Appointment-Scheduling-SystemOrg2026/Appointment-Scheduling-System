package com.scheduling.domain.type;

/**
 * Represents an individual (one-on-one) appointment.
 *
 * <p><b>User Story US5.2:</b> Individual appointments have:</p>
 * <ul>
 *   <li>Standard duration (up to 60 minutes)</li>
 *   <li>Strictly one participant</li>
 *   <li>Personalized attention</li>
 * </ul>
 *
 * @author Tasneem
 * @version 1.0
 */
public class IndividualAppointment implements AppointmentType {

    @Override
    public String getDescription() {
        return "One-on-one private appointment";
    }

    @Override
    public int getMaxDuration() {
        return 60;
    }

    @Override
    public int getMaxParticipants() {
        return 1; // Strictly one person
    }
}