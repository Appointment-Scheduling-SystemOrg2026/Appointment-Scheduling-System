package com.scheduling.observer;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for sending email messages through SMTP.
 * This class uses Gmail's SMTP server to send emails.
 * It requires a valid sender email and password for authentication.
 * 
 * @author Tasneem
 * @version 1.0
 */
public class EmailSender {

    /** Logger for logging email sending errors. */
    private static final Logger logger = Logger.getLogger(EmailSender.class.getName());

    /** The email address used to send emails. */
    private final String senderEmail;

    /** The password for the sender's email account. */
    private final String senderPassword;

    /**
     * Constructs an EmailSender object with the provided sender's email and password.
     * This object will be used to send emails through Gmail's SMTP server.
     *
     * @param senderEmail The email address used to send emails.
     * @param senderPassword The password for the sender's email account.
     */
    public EmailSender(String senderEmail, String senderPassword) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    }

    /**
     * Sends an email to the specified recipient with the given subject and body.
     * This method uses Gmail's SMTP server to send the email.
     *
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param body The body of the email.
     * @return true if the email was sent successfully, false otherwise.
     */
    public boolean sendEmail(String to, String subject, String body) {

        // Set up the properties for the SMTP server.
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Set up the session with authentication.
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a message and set its properties.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Send the email.
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            // Log any errors that occur during the sending process.
            logger.log(Level.SEVERE, "Failed to send email", e);
            return false;
        }
    }
}