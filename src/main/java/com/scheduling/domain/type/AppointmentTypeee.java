package com.scheduling.domain.type;

import com.scheduling.domain.entity.Appointment;

public abstract class AppointmentTypeee {

    public static AppointmentType URGENT;

	public abstract boolean isValid(Appointment appointment);

    public abstract String getTypeName();

	public abstract int getMaxDuration();

	public int getMaxParticipants() {
		// TODO Auto-generated method stub
		return 0;
	}
}


