package com.scheduling.domain.type;

import com.scheduling.domain.type.AppointmentType;
import com.scheduling.domain.entity.Appointment;

public class VirtualAppointment extends AppointmentType {

    @Override
    public boolean isValid(Appointment appointment) {
        return true; // no location limit
    }

    @Override
    public String getTypeName() {
        return "VIRTUAL";
    }
}