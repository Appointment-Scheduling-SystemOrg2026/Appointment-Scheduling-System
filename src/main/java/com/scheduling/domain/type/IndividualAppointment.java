package com.scheduling.domain.type;

/**
 * Represents an individual (one-on-one) appointment.
 * This type of appointment is for a single participant and offers personalized attention.
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

    /**
     * Returns the description of the individual appointment type.
     * In this case, it returns "One-on-one private appointment".
     *
     * @return A string description of the appointment type.
     */
    @Override
    public String getDescription() {
        return "One-on-one private appointment";
    }

    /**
     * Returns the maximum duration for this type of appointment.
     * Individual appointments have a standard duration of 60 minutes.
     *
     * @return The maximum duration in minutes.
     */
    @Override
    public int getMaxDuration() {
        return 60;
    }

    /**
     * Returns the maximum number of participants allowed for this type of appointment.
     * Individual appointments are strictly limited to one participant.
     *
     * @return The maximum number of participants (1 for individual appointments).
     */
    @Override
    public int getMaxParticipants() {
        return 1; // Strictly one person
    }
}