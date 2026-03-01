package com.scheduling.domain.type;

import com.scheduling.domain.entity.Appointment;

public class InPersonAppointment extends AppointmentType {

    @Override
    public boolean isValid(Appointment appointment) {
        return appointment.getParticipants() <= 5;
    }

    @Override
    public String getTypeName() {
        return "IN_PERSON";
    }
}