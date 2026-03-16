


package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Email notification service that sends real emails.
 *
 * @author Tasneem
 * @version 2.0
 */
public class EmailNotificationService implements Observer {

    private EmailSender emailSender;
    private boolean enabled;

    public EmailNotificationService() {
        this.enabled = false;
    }

    public EmailNotificationService(String senderEmail, String senderPassword) {
        this.emailSender = new EmailSender(senderEmail, senderPassword);
        this.enabled = true;
    }

    @Override
    public void notify(User user, String message) {
        if (enabled && emailSender != null) {
            emailSender.sendEmail(
                    user.getUsername(),
                    "Appointment Reminder",
                    message
            );
        }
    }
    public void notifyEmail(String email, String message) {
        if (enabled && emailSender != null) {
            emailSender.sendEmail(email, "Appointment Reminder", message);
        }
    }

    public void enableRealEmail(String senderEmail, String senderPassword) {
        this.emailSender = new EmailSender(senderEmail, senderPassword);
        this.enabled = true;
    }
    public void notifyWithEmail(String email, String message) {
        if (enabled && emailSender != null) {
            emailSender.sendEmail(email, "Appointment Reminder", message);
        } else {
            System.out.println("   (Email would be sent to: " + email + ")");
        }
    }

    public void disableRealEmail() {
        this.enabled = false;
    }
}





/*package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Email notification service that sends real emails.
 *
 * @author Tasneem
 * @version 2.0
 */
/*public class EmailNotificationService implements Observer {

    private EmailSender emailSender;
    private boolean enabled;

    /**
     * Creates service without real email (disabled mode).
     */
   /* public EmailNotificationService() {
        this.enabled = false;
    }

    /**
     * Creates service with real email sending enabled.
     *
     * @param senderEmail    your Gmail address
     * @param senderPassword your Gmail App Password
     */
    /*public EmailNotificationService(String senderEmail, String senderPassword) {
        this.emailSender = new EmailSender(senderEmail, senderPassword);
        this.enabled = true;
    }

    @Override
    public void notify(User user, String message) {
        System.out.println("📬 [EmailService] Preparing email to: " + user.getUsername());
        System.out.println("   Subject: Appointment Reminder");
        System.out.println("   Body: " + message);

        if (enabled && emailSender != null) {
            emailSender.sendEmail(
                    user.getUsername(),
                    "Appointment Reminder",
                    message
            );
        } else {
            System.out.println("   (Email sending is disabled - Mock mode)");
        }
    }

    /**
     * Enables real email sending.
     *
     * @param senderEmail    your Gmail address
     * @param senderPassword your Gmail App Password
     */
    /*public void enableRealEmail(String senderEmail, String senderPassword) {
        this.emailSender = new EmailSender(senderEmail, senderPassword);
        this.enabled = true;
    }

    /**
     * Disables real email sending (mock mode).
     */
    /*public void disableRealEmail() {
        this.enabled = false;
    }
}*/