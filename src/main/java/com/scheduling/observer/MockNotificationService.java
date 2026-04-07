package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Mock notification service for testing (US3.1).
 *
 * <p>Records sent messages in test mode without actually sending.</p>
 *
 * @author Tasneem
 * @version 1.1
 */
public class MockNotificationService implements Observer {

    private int sentCount = 0;
    private StringBuilder log = new StringBuilder();
    private String lastMessage = null;
    private int callCount = 0;  

    @Override
    public void notify(User user, String message) {
        sentCount++;
        callCount++;              
        lastMessage = message;

        log.append(String.format("[%d] To: %s - %s%n",
                sentCount, user.getUsername(), message));
    }

    public int getSentCount() {
        return sentCount;
    }

    public String getLog() {
        return log.toString();
    }

    public void reset() {
        sentCount = 0;
        callCount = 0;
        log = new StringBuilder();
        lastMessage = null;
    }

    public boolean wasCalled() {
        return sentCount > 0;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void clear() {
        reset();
    }

    public int getCallCount() {
        return callCount;
    }
}