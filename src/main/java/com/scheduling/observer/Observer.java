package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Observer interface for notifications.
 */
public interface Observer {

    void notify(User user, String message);
}