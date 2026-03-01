package com.scheduling.service;

import com.scheduling.domain.entity.User;
import com.scheduling.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock notification used for testing.
 */
public class MockNotificationService implements Observer {

    private final List<String> sentMessages = new ArrayList<>();

    @Override
    public void notify(User user, String message) {

        sentMessages.add(
                user.getUsername() + ":" + message
        );
    }

    public List<String> getSentMessages() {
        return sentMessages;
    }
}