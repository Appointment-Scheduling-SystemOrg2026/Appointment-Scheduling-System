package com.scheduling.domain.valueobject;

/**
 * Value Object representing a notification message in the scheduling system.
 *
 * <p>This class is immutable and encapsulates the data for a notification:
 * recipient, content, and timestamp.</p>
 *
 * <p>It can be used for sending notifications to users, logging events,
 * or storing messages related to appointments.</p>
 *
 * <p>Examples of usage:</p>
 * <pre>
 * NotificationMessage msg = new NotificationMessage("JohnDoe", "Your appointment is confirmed.");
 * System.out.println(msg.getContent());
 * </pre>
 *
 * @author Tasneem
 * @version 1.0
 */
public class NotificationMessage {

    /** Identifier for the recipient (username, email, etc.) */
    private final String recipient;

    /** Content of the message */
    private final String content;

    /** Timestamp representing creation time of the message (milliseconds since epoch) */
    private final long timestamp;

    /**
     * Creates a NotificationMessage with content only.
     * Recipient defaults to "unknown".
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
     * Returns the recipient of the message.
     *
     * @return recipient identifier
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Returns the content of the message.
     *
     * @return message content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the message content.
     * This is an alias for {@link #getContent()}.
     *
     * @return message content
     */
    public String getMessage() {
        return content;
    }

    /**
     * Returns the creation timestamp of the message.
     *
     * @return timestamp in milliseconds since epoch
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns a string representation of the NotificationMessage.
     *
     * @return string in the format: NotificationMessage{to='recipient', content='content'}
     */
    @Override
    public String toString() {
        return "NotificationMessage{to='" + recipient + "', content='" + content + "'}";
    }
}