package com.scheduling.domain.valueobject;

/**
 * Value Object representing a notification message.
 *
 * @author Tasneem
 * @version 1.0
 */
public class NotificationMessage {

    private final String recipient;
    private final String content;
    private final long timestamp;

    /**
     * Creates a NotificationMessage with content only.
     *
     * @param content the message content
     */
    public NotificationMessage(String content) {
        this.recipient = "unknown";
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Creates a NotificationMessage with recipient and content.
     *
     * @param recipient the message recipient
     * @param content   the message content
     */
    public NotificationMessage(String recipient, String content) {
        this.recipient = recipient;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Returns the recipient.
     *
     * @return recipient identifier
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Returns the message content.
     *
     * @return message content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the message (alias for getContent).
     *
     * @return message content
     */
    public String getMessage() {
        return content;
    }

    /**
     * Returns the timestamp.
     *
     * @return creation timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "NotificationMessage{to='" + recipient + "', content='" + content + "'}";
    }
}