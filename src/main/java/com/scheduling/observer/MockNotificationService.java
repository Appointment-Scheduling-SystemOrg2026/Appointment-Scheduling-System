package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Mock notification service used for testing purposes (US3.1).
 * This service records sent messages in test mode without actually sending any notifications.
 * It helps in testing scenarios by logging the notifications that would have been sent.
 * 
 * @author Tasneem
 * @version 1.1
 */
public class MockNotificationService implements Observer {

    /** Counter for the number of messages sent. */
    private int sentCount = 0;

    /** Log for storing the messages sent during testing. */
    private StringBuilder log = new StringBuilder();

    /** The last message that was sent. */
    private String lastMessage = null;

    /** Counter for the number of times the notify method was called. */
    private int callCount = 0;  

    /**
     * Records a notification message for a user.
     * This method increments the sent count, call count, and appends the message to the log.
     * The message is also saved as the last message sent.
     * 
     * @param user The user to whom the message is sent.
     * @param message The message to be logged.
     */
    @Override
    public void notify(User user, String message) {
        sentCount++;
        callCount++;              
        lastMessage = message;

        // Append the notification to the log in the format: "[sentCount] To: user - message"
        log.append(String.format("[%d] To: %s - %s%n",
                sentCount, user.getUsername(), message));
    }

    /**
     * Gets the number of messages sent.
     * 
     * @return The number of messages that have been sent.
     */
    public int getSentCount() {
        return sentCount;
    }

    /**
     * Gets the entire log of messages sent.
     * 
     * @return The log containing all sent messages.
     */
    public String getLog() {
        return log.toString();
    }

    /**
     * Resets the service by clearing the sent count, call count, log, and last message.
     * This method is typically used for starting fresh in testing scenarios.
     */
    public void reset() {
        sentCount = 0;
        callCount = 0;
        log = new StringBuilder();
        lastMessage = null;
    }

    /**
     * Checks if the notify method was called at least once.
     * 
     * @return true if at least one message has been sent, false otherwise.
     */
    public boolean wasCalled() {
        return sentCount > 0;
    }

    /**
     * Gets the last message that was sent.
     * 
     * @return The last notification message.
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Clears all recorded information by resetting the service.
     */
    public void clear() {
        reset();
    }

    /**
     * Gets the number of times the notify method has been called.
     * 
     * @return The count of calls to the notify method.
     */
    public int getCallCount() {
        return callCount;
    }
}