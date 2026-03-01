package com.scheduling.domain.valueobject;

/**
 * Represents notification content.
 */
public class NotificationMessage {

    private final String message;

    public NotificationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}