
package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.AppointmentStatus;
import com.scheduling.repository.AppointmentRepository;
import com.scheduling.strategy.BookingRuleStrategy;

import java.util.List;

/**
 * Handles appointment booking logic.
 */

public class AppointmentService {

    private final AppointmentRepository repository;
    private final List<BookingRuleStrategy> rules;

    public AppointmentService(
            AppointmentRepository repository,
            List<BookingRuleStrategy> rules) {

        this.repository = repository;
        this.rules = rules;
    }

    public boolean book(Appointment appointment) {

        for (BookingRuleStrategy rule : rules) {
            if (!rule.isValid(appointment)) {
                return false;
            }
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);

 
        appointment.confirm();
        repository.save(appointment);
        return true;
    }
    /**
     * Returns available appointment slots.
     */

    public List<Appointment> viewAvailableSlots() {
        return repository.findAvailable();
    }
    /**
     * Modifies an existing appointment.
     */
    public boolean modifyAppointment(Appointment appointment,
                                     Appointment updated) {

        
        if (!appointment.getDateTime()
                .isAfter(java.time.LocalDateTime.now())) {
            return false;
        }

        
        for (BookingRuleStrategy rule : rules) {
            if (!rule.isValid(updated)) {
                return false;
            }
        }

        
        appointment.setDateTime(updated.getDateTime());
        appointment.setDuration(updated.getDuration());
        appointment.setParticipants(updated.getParticipants());
        appointment.setType(updated.getType());

        return true;
    }
    /**
     * Cancels appointment.
     */
    public boolean cancelAppointment(Appointment appointment) {

        if (!appointment.getDateTime()
                .isAfter(java.time.LocalDateTime.now())) {
            return false;
        }

        repository.delete(appointment);
        return true;
    }
}

