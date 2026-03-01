package com.scheduling.domain.type;

import com.scheduling.domain.entity.Appointment;

public abstract class AppointmentType {

    public static AppointmentType URGENT;

	public abstract boolean isValid(Appointment appointment);

    public abstract String getTypeName();
}