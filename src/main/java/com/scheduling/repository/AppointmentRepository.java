package com.scheduling.repository;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.AppointmentStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository for appointment persistence.
 * This repository manages appointments using in-memory storage with an ArrayList.
 * It provides methods to save, find, update, delete, and count appointments.
 *
 * <p>Uses in-memory storage as a simple way to manage appointments. In a production system, 
 * this could be replaced by a database or other persistent storage system.</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public class AppointmentRepository {

    /** In-memory storage using ArrayList */
    private final List<Appointment> appointments = new ArrayList<>();

    /**
     * Saves an appointment to the repository.
     * The appointment is added to the in-memory list of appointments.
     *
     * @param appointment the appointment to save
     */
    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Finds all appointments.
     * Returns a list of all appointments currently stored in the repository.
     *
     * @return list of all appointments
     */
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments);
    }

    /**
     * Finds available appointments only.
     * <p>Used by US1.3 - View available appointment slots.</p>
     * Filters appointments to return only those that are in either 
     * "AVAILABLE" or "CONFIRMED" status.
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
     * Searches for an appointment with the specified ID and returns it.
     * If no appointment is found, returns null.
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
     * Deletes an appointment from the repository.
     * The appointment is removed from the in-memory list if it exists.
     *
     * @param appointment the appointment to delete
     * @return true if the appointment was deleted, false if not found
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

    /**
     * Deletes an appointment by its ID.
     * The appointment is removed based on its ID.
     *
     * @param id the appointment ID to delete
     * @return true if the appointment was deleted, false if not found
     */
    public boolean deleteById(String id) {
        return appointments.removeIf(apt -> apt.getId().equals(id));
    }

    /**
     * Updates an appointment in the repository.
     * Since the repository uses in-memory storage, the appointment is automatically 
     * updated in the list when its properties change.
     *
     * @param appointment the appointment to update
     */
    public void update(Appointment appointment) {
        // In-memory: appointment is already updated in list
    }

    /**
     * Clears all appointments from the repository.
     * This removes all appointments stored in the in-memory list.
     */
    public void clear() {
        appointments.clear();
    }

    /**
     * Returns the total count of appointments.
     * This method returns the number of appointments currently stored in the repository.
     *
     * @return total count of appointments
     */
    public int count() {
        return appointments.size();
    }
}