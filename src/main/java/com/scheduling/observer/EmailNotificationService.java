package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

/**
 * Sends real email reminders.
 */
public class EmailNotificationService implements Observer {

    private final String senderEmail =
            "scheduling.project2026@gmail.com";

    private final String appPassword =
            "jgwg ymub wqmx xylu";

    @Override
    public void notify(User user, String message) {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
                props,
                new Authenticator() {
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                senderEmail,
                                appPassword);
                    }
                });

        try {

            Message email = new MimeMessage(session);

            email.setFrom(
                    new InternetAddress(senderEmail));

            email.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getUsername())
            );

            email.setSubject("Appointment Reminder");

            email.setText(message);

            Transport.send(email);

            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

