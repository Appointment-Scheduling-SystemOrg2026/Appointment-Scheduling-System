package com.scheduling.domain.entity;

/**
 * Enum representing the status of an appointment in the scheduling system.
 * This enum contains the possible states an appointment can have during its lifecycle.
 * 
 * @author Tasneem
 * @version 1.0
 */
public enum AppointmentStatus {

    /** 
     * Appointment is available for booking.
     * This status indicates that the appointment slot is open and can be reserved by users. 
     */
    AVAILABLE("Available"),

    /** 
     * Appointment has been confirmed.
     * This status indicates that the appointment has been successfully booked and is now confirmed.
     */
    CONFIRMED("Confirmed"),

    /** 
     * Appointment has been cancelled.
     * This status indicates that the appointment has been cancelled and is no longer valid.
     */
    CANCELLED("Cancelled"),

    /** 
     * Appointment has been completed.
     * This status indicates that the appointment has occurred and the scheduled task has been finished.
     */
    COMPLETED("Completed");

    /** The display name for each status. */
    private final String displayName;

    /**
     * Constructor for the enum values.
     * 
     * @param displayName The string representation of the appointment status.
     */
    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the string representation of the appointment status.
     * This will return the display name associated with the status.
     *
     * @return The display name of the appointment status.
     */
    @Override
    public String toString() {
        return displayName;
    }
}