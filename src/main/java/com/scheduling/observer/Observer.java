package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Observer interface for the Observer Pattern.
 *
 * <p>Used for notification system (US3.1) to allow multiple
 * notification channels (email, SMS, calendar, etc.).</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public interface Observer {

    /**
     * Called when a notification should be sent.
     *
     * @param user    the user to notify
     * @param message the notification message
     */
    void notify(User user, String message);
}