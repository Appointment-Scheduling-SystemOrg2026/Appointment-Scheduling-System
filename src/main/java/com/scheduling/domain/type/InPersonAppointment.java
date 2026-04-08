package com.scheduling.domain.type;

/**
 * Represents an in-person (face-to-face) appointment.
 * This type of appointment requires physical presence at a designated location,
 * and the duration and participant numbers are limited.
 *
 * <p><b>User Story US5.2:</b> In-person appointments have:</p>
 * <ul>
 *   <li>Standard duration (up to 60 minutes)</li>
 *   <li>Physical presence required</li>
 *   <li>Room/location assignment needed</li>
 * </ul>
 *
 * @author Tasneem
 * @version 1.0
 */
public class InPersonAppointment implements AppointmentType {

    /**
     * Returns the description of the appointment type.
     * In this case, it returns "Face-to-face appointment at the facility".
     *
     * @return A string description of the appointment type.
     */
    @Override
    public String getDescription() {
        return "Face-to-face appointment at the facility";
    }

    /**
     * Returns the maximum duration for this type of appointment.
     * In-person appointments have a standard duration of 60 minutes.
     *
     * @return The maximum duration in minutes.
     */
    @Override
    public int getMaxDuration() {
        return 60;
    }

    /**
     * Returns the maximum number of participants allowed for this type of appointment.
     * In-person appointments are limited to 4 participants.
     *
     * @return The maximum number of participants.
     */
    @Override
    public int getMaxParticipants() {
        return 4;
    }

    /**
     * Returns a specific name for this type of appointment.
     * This name is used for identification in the system.
     *
     * @return The type name for the in-person appointment (e.g., "IN_PERSON_1").
     */
    public String getTypeName1() {
        return "IN_PERSON_1";
    }

    /**
     * Returns the general name for this type of appointment.
     * This name is used for categorizing appointments.
     *
     * @return The general type name for in-person appointments.
     */
    public String getTypeName() {
        return "IN_PERSON";
    }
}   