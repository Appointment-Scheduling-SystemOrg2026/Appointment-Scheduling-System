package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Basic notification service implementation.
 *
 * @author Tasneem
 * @version 1.0
 */
public class NotificationService implements Observer {

    @Override
    public void notify(User user, String message) {
        // Silent - no console output
    }
}








