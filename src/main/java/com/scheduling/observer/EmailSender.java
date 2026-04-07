package com.scheduling.observer;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailSender {

    private static final Logger logger = Logger.getLogger(EmailSender.class.getName());

    private final String senderEmail;
    private final String senderPassword;

    public EmailSender(String senderEmail, String senderPassword) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    }

    public boolean sendEmail(String to, String subject, String body) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Failed to send email", e);
            return false;
        }
    }
}



