package com.scheduling.repository;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.AppointmentStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository for storing appointments.
 */
public class AppointmentRepository {

    private final List<Appointment> appointments =
            new ArrayList<>();

    /**
     * Saves appointment.
     */
    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Returns all appointments.
     */
    public List<Appointment> findAll() {
        return appointments;
    }

    /**
     * Removes appointment.
     */
    public void delete(Appointment appointment) {
        appointments.remove(appointment);
    }
    /**
     * Returns only available appointments.
     */
    public List<Appointment> findAvailable() {

        List<Appointment> result = new ArrayList<>();

        for (Appointment a : appointments) {
            if (a.getStatus() == AppointmentStatus.AVAILABLE) {
                result.add(a);
            }
        }

        return result;
    }
    } 