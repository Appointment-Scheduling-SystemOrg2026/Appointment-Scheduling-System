package com.scheduling.service;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.User;
import com.scheduling.observer.Observer;

import java.util.List;

/**
 * Sends reminder notifications using Observer pattern.
 */
public class ReminderService {

    private final List<Observer> observers;

    public ReminderService(List<Observer> observers) {
        this.observers = observers;
    }

    /**
     * Sends reminder notification.
     */
    public void sendReminder(User user, Appointment appointment) {

       
        if (appointment.getDateTime().isBefore(java.time.LocalDateTime.now())) {
            return;
        }

        String message =
                "Reminder: You have an appointment at "
                        + appointment.getDateTime();

        notifyObservers(user, message);
    }

    /**
     * Notify all observers.
     */
    public void notifyObservers(User user, String message) {

        for (Observer observer : observers) {
            observer.notify(user, message);
        }
    }
}