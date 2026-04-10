package com.scheduling.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BooleanSupplier;

import com.scheduling.domain.type.AppointmentType;

/**
 * Represents an appointment in the scheduling system.
 * This class contains the details about an appointment such as the date, duration,
 * participants, status, and type.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class Appointment {

    /** The unique identifier for the appointment. */
    private String id;
    
    /** The date and time when the appointment is scheduled. */
    private LocalDateTime dateTime;
    
    /** The duration of the appointment in minutes. */
    private int duration;
    
    /** The number of participants involved in the appointment. */
    private int participants;
    
    /** The current status of the appointment (e.g., confirmed, cancelled, available). */
    private AppointmentStatus status;
    
    /** The type of the appointment (e.g., meeting, consultation, etc.). */
    private AppointmentType type;
    private String bookedBy;
    

    /**
     * Constructs an Appointment object with the provided details.
     * The ID is automatically generated as a random UUID.
     *
     * @param dateTime The date and time of the appointment.
     * @param duration The duration of the appointment in minutes.
     * @param participants The number of participants for the appointment.
     * @param type The type of the appointment (e.g., meeting, consultation).
     */
    public Appointment(LocalDateTime dateTime,
                       int duration,
                       int participants,
                       AppointmentType type) {

        this.id = UUID.randomUUID().toString();
        this.dateTime = dateTime;
        this.duration = duration;
        this.participants = participants;
        this.type = type;
        this.status = AppointmentStatus.AVAILABLE;
    }

    /**
     * Returns a string representation of the Appointment object.
     * Includes the date, duration, participants, status, and type.
     *
     * @return A string representing the Appointment.
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "dateTime=" + dateTime +
                ", duration=" + duration +
                ", participants=" + participants +
                ", status=" + status +
                ", type=" + type.getClass().getSimpleName() +
                '}';
    }

    /**
     * Gets the unique identifier for the appointment.
     *
     * @return The ID of the appointment.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the scheduled date and time of the appointment.
     *
     * @return The date and time of the appointment.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Gets the duration of the appointment in minutes.
     *
     * @return The duration of the appointment.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the number of participants involved in the appointment.
     *
     * @return The number of participants.
     */
    public int getParticipants() {
        return participants;
    }

    /**
     * Gets the current status of the appointment.
     *
     * @return The status of the appointment.
     */
    public AppointmentStatus getStatus() {
        return status;
    }
    
    /**
     * Confirms the appointment by setting its status to "CONFIRMED".
     */
    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param status The new status of the appointment.
     */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /**
     * Gets the type of the appointment (e.g., meeting, consultation).
     *
     * @return The type of the appointment.
     */
    public AppointmentType getType() {
        return type;
    }

    /**
     * Cancels the appointment by setting its status to "CANCELLED".
     */
    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    /**
     * Reschedules the appointment to a new date and time.
     *
     * @param newDateTime The new date and time for the appointment.
     */
    public void reschedule(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
    }

    /**
     * Sets the scheduled date and time for the appointment.
     *
     * @param dateTime The new date and time for the appointment.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Sets the duration of the appointment in minutes.
     *
     * @param duration The new duration for the appointment.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Sets the number of participants for the appointment.
     *
     * @param participants The number of participants for the appointment.
     */
    public void setParticipants(int participants) {
        this.participants = participants;
    }

    /**
     * Sets the type of the appointment (e.g., meeting, consultation).
     *
     * @param type The new type for the appointment.
     */
    public void setType(AppointmentType type) {
        this.type = type;
    }

    /**
     * Checks if the appointment is scheduled in the future.
     * Returns a BooleanSupplier that determines if the current appointment
     * is scheduled for a future date.
     *
     * @return A BooleanSupplier representing whether the appointment is in the future.
     */
    public BooleanSupplier isFuture() {
        return () -> dateTime.isAfter(LocalDateTime.now());
    }
    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }
}