
package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.AppointmentStatus;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.strategy.BookingRuleStrategy;

import java.util.List;

/**
 * Handles appointment booking, modification, and cancellation logic.
 * This service interacts with the repository to persist appointments
 * and applies booking rules to ensure validity during booking and modification.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class AppointmentService {

    /** Repository for managing appointment persistence. */
    private final AppointmentRepository repository;

    /** List of booking rules to validate appointments. */
    private final List<BookingRuleStrategy> rules;

    /**
     * Constructs an AppointmentService with the specified repository and rules.
     *
     * @param repository The repository for managing appointments.
     * @param rules A list of booking rules to apply during booking and modification.
     */
    public AppointmentService(
            AppointmentRepository repository,
            List<BookingRuleStrategy> rules) {

        this.repository = repository;
        this.rules = rules;
    }

    /**
     * Books an appointment if all booking rules are satisfied.
     * This method checks if the appointment is valid based on the provided rules, 
     * confirms the appointment, and saves it in the repository.
     *
     * @param appointment The appointment to book.
     * @return true if the appointment was successfully booked, false otherwise.
     */
    public boolean book(Appointment appointment) {

        // Validate the appointment with all the booking rules
        for (BookingRuleStrategy rule : rules) {
            if (!rule.isValid(appointment)) {
                return false; // If any rule is not satisfied, booking fails
            }
        }

        // Confirm the appointment status
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointment.confirm();

        // Save the appointment in the repository
        repository.save(appointment);
        return true;
    }

    /**
     * Returns available appointment slots.
     * This method retrieves all appointments that are either available or confirmed.
     *
     * @return a list of available appointments
     */
    public List<Appointment> viewAvailableSlots() {
        return repository.findAvailable();
    }

    /**
     * Modifies an existing appointment if the provided updated appointment is valid.
     * It checks if the appointment's date and time is in the future and validates the updated details.
     * 
     * @param appointment The existing appointment to modify.
     * @param updated The updated appointment details.
     * @return true if the appointment was successfully modified, false otherwise.
     */
    public boolean modifyAppointment(Appointment appointment,
                                     Appointment updated) {

        // Check if the current appointment's date and time is in the future
        if (!appointment.getDateTime().isAfter(java.time.LocalDateTime.now())) {
            return false; // Cannot modify past appointments
        }

        // Validate the updated appointment using the rules
        for (BookingRuleStrategy rule : rules) {
            if (!rule.isValid(updated)) {
                return false; // If any rule is not satisfied, modification fails
            }
        }

        // Update the appointment details
        appointment.setDateTime(updated.getDateTime());
        appointment.setDuration(updated.getDuration());
        appointment.setParticipants(updated.getParticipants());
        appointment.setType(updated.getType());

        return true;
    }

    /**
     * Cancels an appointment if its scheduled date is in the future.
     * This method cancels the appointment and removes it from the repository.
     *
     * @param appointment The appointment to cancel.
     * @return true if the appointment was successfully canceled, false otherwise.
     */
    public boolean cancelAppointment(Appointment appointment) {
        // Ensure the appointment is in the future before canceling
        if (!appointment.getDateTime().isAfter(java.time.LocalDateTime.now())) {
            return false; // Cannot cancel past appointments
        }

        // Cancel the appointment and remove it from the repository
        appointment.cancel();
        repository.deleteById(appointment.getId());
        return true;
    }

    /**
     * Finds an appointment by its ID.
     * This method is a placeholder and needs to be implemented.
     *
     * @param string The ID of the appointment to find.
     * @return the appointment if found, null otherwise.
     */
    public Appointment findById(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Deletes an appointment.
     * This method is a placeholder and needs to be implemented.
     *
     * @param apt The appointment to delete.
     * @return true if the appointment was deleted, false otherwise.
     */
    public boolean deleteAppointment(Appointment apt) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Finds all appointments.
     * This method is a placeholder and needs to be implemented.
     *
     * @return a list of all appointments.
     */
    public List<Appointment> findAllAppointments() {
        // TODO Auto-generated method stub
        return null;
    }
}