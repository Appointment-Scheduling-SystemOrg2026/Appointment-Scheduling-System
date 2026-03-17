package com.scheduling.domain.type;

/**
 * Represents an assessment or evaluation appointment.
 *
 * <p><b>User Story US5.2:</b> Assessment appointments have:</p>
 * <ul>
 *   <li>Longer duration (up to 90 minutes)</li>
 *   <li>May include multiple participants</li>
 *   <li>Comprehensive evaluation focus</li>
 * </ul>
 *
 * @author Tasneem
 * @version 1.0
 */
public class AssessmentAppointment implements AppointmentType {

    @Override
    public String getDescription() {
        return "Comprehensive assessment and evaluation session";
    }

    @Override
    public int getMaxDuration() {
        return 90;
    }

    @Override
    public int getMaxParticipants() {
        return 3;
    }
}