package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Handles email notifications for the scheduling system.
 * This service is responsible for sending appointment reminder emails.
 * It can be enabled or disabled, and supports both real email sending and test mode.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class EmailNotificationService implements Observer {
    
    /** The subject for appointment reminder emails. */
    private static final String APPOINTMENT_REMINDER_SUBJECT = "Appointment Reminder";

    /** The email sender instance used to send emails. */
    private EmailSender emailSender;

    /** A flag to determine if email notifications are enabled. */
    private boolean enabled;

    /** A static flag to enable test mode where no emails are sent. */
    private static boolean testMode = false;

    /**
     * Sets the test mode flag. When enabled, no emails will be sent.
     *
     * @param value true to enable test mode, false to disable.
     */
    public static void setTestMode(boolean value) {
        testMode = value;
    }

    /**
     * Default constructor that disables email notifications.
     */
    public EmailNotificationService() {
        this.enabled = false;
    }

    /**
     * Constructs an EmailNotificationService with the specified sender's email and password.
     * This constructor enables email notifications by creating an EmailSender.
     *
     * @param senderEmail The email address used to send emails.
     * @param senderPassword The password for the sender's email account.
     */
    public EmailNotificationService(String senderEmail, String senderPassword) {
        this.emailSender = new EmailSender(senderEmail, senderPassword);
        this.enabled = true;
    }

    /**
     * Sends a notification email to a user.
     * This method sends an appointment reminder email to the provided user.
     * If test mode is enabled, no email will be sent.
     *
     * @param user The user to send the notification to.
     * @param message The message to include in the email body.
     */
    @Override
    public void notify(User user, String message) {

        if (testMode) {
            return;
        }

        if (enabled && emailSender != null) {
            emailSender.sendEmail(
                    user.getUsername(),
                    APPOINTMENT_REMINDER_SUBJECT,
                    message
            );
        }
    }

    /**
     * Sends a notification email to the specified email address.
     * This method sends an appointment reminder email to the provided email.
     * If test mode is enabled, no email will be sent.
     *
     * @param email The email address to send the notification to.
     * @param message The message to include in the email body.
     */
    public void notifyEmail(String email, String message) {

        if (testMode) return;

        if (enabled && emailSender != null) {
            emailSender.sendEmail(email, APPOINTMENT_REMINDER_SUBJECT, message);
        }
    }

    /**
     * Enables the email sending functionality with the specified sender email and password.
     * This method creates a new EmailSender instance and sets the service to enabled.
     *
     * @param senderEmail The email address used to send emails.
     * @param senderPassword The password for the sender's email account.
     */
    public void enableRealEmail(String senderEmail, String senderPassword) {
        this.emailSender = new EmailSender(senderEmail, senderPassword);
        this.enabled = true;
    }

    /**
     * Sends a notification email to the specified email address.
     * If the email sending service is disabled, it will print the email to the console.
     * If test mode is enabled, no email will be sent.
     *
     * @param email The email address to send the notification to.
     * @param message The message to include in the email body.
     */
    public void notifyWithEmail(String email, String message) {

        if (testMode) {
            return;
        }

        if (enabled && emailSender != null) {
            emailSender.sendEmail(email, APPOINTMENT_REMINDER_SUBJECT, message);
        } else {
            System.out.println("   (Email would be sent to: " + email + ")");
        }
    }

    /**
     * Disables the email sending functionality.
     * This method sets the email service to disabled, preventing any emails from being sent.
     */
    public void disableRealEmail() {
        this.enabled = false;
    }
}