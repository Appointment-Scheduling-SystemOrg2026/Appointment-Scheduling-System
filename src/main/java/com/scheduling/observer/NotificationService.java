package com.scheduling.observer;

import com.scheduling.domain.entity.User;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * Sends real email notifications.
 */
public class NotificationService implements Observer {

    private final String senderEmail = "scheduling.project2026@gmail.com";
    private final String appPassword = "jgwgymubwqmxxylu";

    @Override
    public void notify(User user, String message) {

        String receiverEmail = senderEmail; 
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
                props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                senderEmail,
                                appPassword
                        );
                    }
                });

        try {

            Message mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(senderEmail));

            mimeMessage.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail)
            );

            mimeMessage.setSubject("Appointment Reminder");

            mimeMessage.setText(
                    "Hello " + user.getUsername()
                            + ",\n\n"
                            + message
            );

            Transport.send(mimeMessage);

            System.out.println("✅ Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

