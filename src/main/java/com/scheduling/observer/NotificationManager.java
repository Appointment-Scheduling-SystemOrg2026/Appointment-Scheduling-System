package com.scheduling.observer;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.User;

import java.util.List;

/**
 * Manages notifications using Observer Pattern.
 *
 * @author Tasneem
 * @version 1.0
 */
public class NotificationManager {

    private final List<Observer> observers;

    public NotificationManager(List<Observer> observers) {
        this.observers = observers;
    }

    public void sendReminder(User user, Appointment appointment) {
        String message = String.format(
                "Reminder: You have an appointment on %s. Type: %s",
                appointment.getDateTime().toString(),
                appointment.getType().getClass().getSimpleName()
        );

        for (Observer observer : observers) {
            observer.notify(user, message);
        }
    }
    public void sendReminderToEmail(String email, Appointment appointment) {
        String message = String.format(
                "Reminder: You have an appointment on %s. Type: %s",
                appointment.getDateTime().toString(),
                appointment.getType().getClass().getSimpleName()
        );

        for (Observer observer : observers) {
            if (observer instanceof EmailNotificationService) {
                ((EmailNotificationService) observer).notifyWithEmail(email, message);
            } else {
                observer.notify(null, message);
            }
        }
    }
    

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}