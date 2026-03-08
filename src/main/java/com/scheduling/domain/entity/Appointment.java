package com.scheduling.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.scheduling.domain.type.AppointmentType;

/**
 * Represents an appointment in the scheduling system.
 *
 * @author Tasneem
 * @version 1.0
 */
public class Appointment {

    private String id;
    private LocalDateTime dateTime;
    private int duration;
    private int participants;
    private AppointmentStatus status;
    private AppointmentType type;
    

    public Appointment(LocalDateTime dateTime,
                       int duration,
                       int participants,
                        AppointmentType  type) {

        this.id = UUID.randomUUID().toString();
        this.dateTime = dateTime;
        this.duration = duration;
        this.participants = participants;
        this.type = type;
        this.status = AppointmentStatus.AVAILABLE;
    }
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

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public int getParticipants() {
        return participants;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentType getType() {
        return type;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public void reschedule(LocalDateTime newDateTime) {
        this.dateTime = newDateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }
    
}