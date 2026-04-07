package com.scheduling.observer;

import com.scheduling.domain.entity.User;

public class EmailNotificationService implements Observer {
	  private static final String APPOINTMENT_REMINDER_SUBJECT = "Appointment Reminder";

    private EmailSender emailSender;
    private boolean enabled;

    
    private static boolean testMode = false;

    public static void setTestMode(boolean value) {
        testMode = value;
    }

    public EmailNotificationService() {
        this.enabled = false;
    }

    public EmailNotificationService(String senderEmail, String senderPassword) {
        this.emailSender = new EmailSender(senderEmail, senderPassword);
        this.enabled = true;
    }

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

    public void notifyEmail(String email, String message) {

        if (testMode) return;

        if (enabled && emailSender != null) {
        	 emailSender.sendEmail(email, APPOINTMENT_REMINDER_SUBJECT, message);
        }
    }

    public void enableRealEmail(String senderEmail, String senderPassword) {
        this.emailSender = new EmailSender(senderEmail, senderPassword);
        this.enabled = true;
    }

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

    public void disableRealEmail() {
        this.enabled = false;
    }
}









