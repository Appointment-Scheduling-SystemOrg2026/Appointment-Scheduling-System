package com.scheduling.observer;

import com.scheduling.domain.entity.Appointment;
import java.time.format.DateTimeFormatter;
import com.scheduling.domain.entity.User;
import java.util.List;

/**
 * Manages notifications using the Observer design pattern.
 * This class is responsible for sending reminders to a list of observers,
 * such as sending appointment reminders via different channels like email.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class NotificationManager {

    /** List of observers that will receive the notifications. */
    private final List<Observer> observers;

    /**
     * Constructs a NotificationManager with a list of observers.
     * 
     * @param observers A list of observers that will receive notifications.
     */
    public NotificationManager(List<Observer> observers) {
        this.observers = observers;
    }

    /**
     * Sends an appointment reminder to all observers.
     * The reminder contains the appointment's date, time, and type.
     * 
     * @param user The user to whom the reminder is sent.
     * @param appointment The appointment details to include in the reminder.
     */
    public void sendReminder(User user, Appointment appointment) {

        // Format the message with the appointment details.
        String message = String.format(
                "🔔 Appointment Reminder%n%n" +
                "📅 Date & Time: %s%n" +
                "📌 Type: %s%n",
                appointment.getDateTime(),
                appointment.getType().getClass().getSimpleName()
        );

        // Send the message to each observer.
        for (Observer observer : observers) {
            observer.notify(user, message);
        }
    }

    /**
     * Sends an appointment reminder to a specified email address.
     * The reminder contains the appointment's date, time, and type.
     * 
     * @param email The email address to which the reminder will be sent.
     * @param appointment The appointment details to include in the reminder.
     */
    public void sendReminderToEmail(String email, Appointment appointment) {

        // Format the appointment date and time.
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Format the message with the appointment details.
        String message = String.format(
                "🔔 Appointment Reminder%n%n" +
                "📅 Date: %s%n" +
                "⏰ Time: %s%n" +
                "📌 Type: %s%n",
                appointment.getDateTime().format(dateFormatter),
                appointment.getDateTime().format(timeFormatter),
                appointment.getType().getClass().getSimpleName()
        );

        // Send the message to the appropriate observer (EmailNotificationService for email).
        for (Observer observer : observers) {
            if (observer instanceof EmailNotificationService) {
                ((EmailNotificationService) observer).notifyWithEmail(email, message);
            } else {
                observer.notify(null, message);
            }
        }
    }

    /**
     * Adds a new observer to the list of observers.
     * 
     * @param observer The observer to be added.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers.
     * 
     * @param observer The observer to be removed.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}