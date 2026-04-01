package com.scheduling.observer;

import com.scheduling.domain.entity.Appointment;
import java.time.format.DateTimeFormatter;
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
                "🔔 Appointment Reminder%n%n" +
                "📅 Date & Time: %s%n" +
                "📌 Type: %s%n",
                appointment.getDateTime(),
                appointment.getType().getClass().getSimpleName()
        );

        for (Observer observer : observers) {
            observer.notify(user, message);
        }
    }
   
    public void sendReminderToEmail(String email, Appointment appointment) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String message = String.format(
                "🔔 Appointment Reminder%n%n" +
                "📅 Date: %s%n" +
                "⏰ Time: %s%n" +
                "📌 Type: %s%n",
                appointment.getDateTime().format(dateFormatter),
                appointment.getDateTime().format(timeFormatter),
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