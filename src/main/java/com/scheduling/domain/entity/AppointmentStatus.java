package com.scheduling.domain.entity;

/**
 * Enum representing appointment status.
 *
 * @author Tasneem
 * @version 1.0
 */
public enum AppointmentStatus {

    /** Appointment is available for booking */
    AVAILABLE("Available"),

    /** Appointment has been confirmed */
    CONFIRMED("Confirmed"),

    /** Appointment has been cancelled */
    CANCELLED("Cancelled"),

    /** Appointment has been completed */
    COMPLETED("Completed");

    private final String displayName;

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}