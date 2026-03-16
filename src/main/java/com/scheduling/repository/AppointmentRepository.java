package com.scheduling.repository;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.AppointmentStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for appointment persistence.
 *
 * <p>Uses in-memory storage with LinkedList as specified.</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public class AppointmentRepository {

    /** In-memory storage using LinkedList */
    private final List<Appointment> appointments = new ArrayList<>();

    /**
     * Saves an appointment to the repository.
     *
     * @param appointment the appointment to save
     */
    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Finds all appointments.
     *
     * @return list of all appointments
     */
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments);
    }

    /**
     * Finds available appointments only.
     *
     * <p>Used by US1.3 - View available appointment slots.</p>
     *
     * @return list of available appointments
     */
    public List<Appointment> findAvailable() {
        return appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.AVAILABLE ||
                        a.getStatus() == AppointmentStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    /**
     * Finds an appointment by ID.
     *
     * @param id the appointment ID
     * @return the appointment, or null if not found
     */
    public Appointment findById(String id) {
        return appointments.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Deletes an appointment.
     *
     * @param appointment the appointment to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(Appointment appointment) {
        
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId().equals(appointment.getId())) {
                appointments.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean deleteById(String id) {
        return appointments.removeIf(apt -> apt.getId().equals(id));
    }

    /**
     * Updates an appointment.
     *
     * @param appointment the appointment to update
     */
    public void update(Appointment appointment) {
        // In-memory: appointment is already updated in list
    }

    /**
     * Clears all appointments.
     */
    public void clear() {
        appointments.clear();
    }

    /**
     * Returns the count of appointments.
     *
     * @return total count
     */
    public int count() {
        return appointments.size();
    }
} 