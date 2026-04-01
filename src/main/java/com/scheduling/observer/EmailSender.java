package com.scheduling.observer;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

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
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}







/*package com.scheduling.observer;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Sends real emails using SMTP.
 *
 * @author Tasneem
 * @version 1.0
 */
/*public class EmailSender {

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
        props.put("mail.smtp.debug", "false");

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
        	 System.out.println("ERROR: " + e.getMessage());
             e.printStackTrace();
            return false;
        }
    }
}*/





/*package com.scheduling.observer;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Sends real emails using SMTP.
 *
 * @author Tasneem
 * @version 1.0
 */
/*public class EmailSender {

    private final String senderEmail;
    private final String senderPassword;

    /**
     * Creates an EmailSender with Gmail SMTP.
     *
     * @param senderEmail    your Gmail address
     * @param senderPassword your Gmail App Password
     */
    /*public EmailSender(String senderEmail, String senderPassword) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    }

    /**
     * Sends an email notification.
     *
     * @param to      recipient email
     * @param subject email subject
     * @param body    email body
     * @return true if sent successfully
     */
   /* public boolean sendEmail(String to, String subject, String body) {
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
            System.out.println("✅ Email sent successfully to: " + to);
            return true;

        } catch (MessagingException e) {
            System.out.println("❌ Failed to send email: " + e.getMessage());
            return false;
        }
    }
}*/