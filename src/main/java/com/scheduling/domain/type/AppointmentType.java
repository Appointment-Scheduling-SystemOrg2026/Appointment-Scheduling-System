package com.scheduling.domain.type;

/**
 * Base interface for all appointment types.
 *
 * <p>This interface enables polymorphism for different appointment types
 * as required by US5.1 and US5.2.</p>
 *
 * <p>Supported types include:</p>
 * <ul>
 *   <li>Urgent - Emergency appointments requiring immediate attention</li>
 *   <li>Follow-up - Post-treatment check appointments</li>
 *   <li>Assessment - Evaluation or assessment appointments</li>
 *   <li>Virtual - Remote/online appointments</li>
 *   <li>In-Person - Face-to-face appointments</li>
 *   <li>Individual - One-on-one appointments</li>
 *   <li>Group - Multi-participant appointments</li>
 * </ul>
 *
 * @author Tasneem
 * @version 1.0
 * @since 2025
 */
public interface AppointmentType {

    /**
     * Returns a description of this appointment type.
     *
     * @return description string
     */
    String getDescription();

    /**
     * Returns the maximum allowed duration for this appointment type.
     *
     * @return maximum duration in minutes
     */
    int getMaxDuration();

    /**
     * Returns the maximum number of participants for this type.
     *
     * @return maximum participants
     */
    int getMaxParticipants();

    /**
     * Checks if this appointment type requires immediate attention.
     *
     * @return true if urgent, false otherwise
     */
    default boolean isUrgent() {
        return false;
    }

    /**
     * Checks if this appointment type is conducted remotely.
     *
     * @return true if virtual, false otherwise
     */
    default boolean isVirtual() {
        return false;
    }
}