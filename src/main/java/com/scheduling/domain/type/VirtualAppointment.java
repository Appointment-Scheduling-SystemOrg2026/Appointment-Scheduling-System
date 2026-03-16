package com.scheduling.domain.type;

/**
 * Represents a virtual (remote/online) appointment.
 *
 * <p><b>User Story US5.2:</b> Virtual appointments have:</p>
 * <ul>
 *   <li>Standard duration (up to 45 minutes)</li>
 *   <li>Conducted via video conferencing</li>
 *   <li>Meeting link provided</li>
 * </ul>
 *
 * @author Tasneem
 * @version 1.0
 */
public class VirtualAppointment implements AppointmentType {

    @Override
    public String getDescription() {
        return "Remote appointment via video conferencing";
    }

    @Override
    public int getMaxDuration() {
        return 45;
    }

    @Override
    public int getMaxParticipants() {
        return 5;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

	public void getTypeName() {
		// TODO Auto-generated method stub
		
	}
}
