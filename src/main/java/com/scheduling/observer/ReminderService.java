/*package com.scheduling.observer;

import com.scheduling.domain.entity.Appointment;
import com.scheduling.domain.entity.User;
import com.scheduling.domain.valueobject.NotificationMessage;

import java.util.List;

/**
 * Subject class for the Observer Pattern.
 *
 * <p>Manages notification observers and sends reminders
 * as required by US3.1.</p>
 *
 * @author Tasneem
 * @version 1.0
 */
/*
public class ReminderService {

    private final List<Observer> observers;

    /**
     * Creates a ReminderService with specified observers.
     *
     * @param observers list of notification observers
     */
/*
    public ReminderService(List<Observer> observers) {
        this.observers = observers;
    }

    /**
     * Sends a reminder for an appointment.
     *
     * <p><b>User Story US3.1:</b> Sends appointment reminders
     * to notify users in advance.</p>
     *
     * @param user        the user to notify
     * @param appointment the appointment to remind about
     */
    /*
    public void sendReminder(User user, Appointment appointment) {
        String message = String.format(
                "Reminder: You have an appointment on %s. Type: %s",
                appointment.getDateTime().toString(),
                appointment.getType().getClass().getSimpleName()
        );

        NotificationMessage notification =
                new NotificationMessage(user.getUsername(), message);

        // Notify all observers
        for (Observer observer : observers) {
            observer.notify(user, message);
        }
    }

    /**
     * Adds a new observer to the notification list.
     *
     * @param observer the observer to add
     */
    /*
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the notification list.
     *
     * @param observer the observer to remove
     */
    /*
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}*/