package com.scheduling.domain.type;

/**
 * Represents an in-person (face-to-face) appointment.
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
	@Override
    public String getDescription() {
        return "Face-to-face appointment at the facility";
    }

    @Override
    public int getMaxDuration() {
        return 60;
    }

    @Override
    public int getMaxParticipants() {
        return 4;
    }

    public String getTypeName1() {
        return "IN_PERSON_1";
    }

    public String getTypeName() {
        return "IN_PERSON";
    }
}
	
	
	
	
	
	
	
	
	
   