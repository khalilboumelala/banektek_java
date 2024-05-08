package com.inventorymanagementsystem.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailUtil {
    public static void sendEmail(String senderName, String recipient, String subject, String body) throws MessagingException {
        // Configure properties for the SMTP session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Replace with the appropriate SMTP server
        props.put("mail.smtp.port", "587"); // Replace with the appropriate SMTP port

        // Create an authenticated SMTP session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Ebanking.Society@gmail.com", "ypbuklkwyqlktqmi"); // Replace with your email credentials
            }
        });

        try {
            // Create a MIME message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("imen.belhoula@esprit.tn", senderName)); // Set sender's email and name
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);

            // Create HTML content for the email body
            String htmlBody = "<html><body>"; // Opening body tag
            htmlBody += "<div style='background-color: #FFC0CB; padding: 20px;'>"; // Pink container with padding
            htmlBody += "<h1 style='color:purple;text-align:center;'>Banektek</h1>"; // Title in purple, centered
            htmlBody += "<p>Bonjour,</p>"; // Greeting
            htmlBody += "<p>Votre nouvelle transaction a ete creee avec succes.</p>"; // Message
            htmlBody += "<table border='1' align='center'><tr><th>Type</th><th>Montant</th></tr>"; // Table start, centered
            htmlBody += "<tr><td>" + "Type de virement" + "</td><td>" + "100.0" + "</td></tr>"; // Table row with type and amount
            htmlBody += "</table>"; // Table end
            htmlBody += "<p>Cordialement,<br/>" + senderName + "</p>"; // Sign-off
            htmlBody += "</div>"; // Closing pink container
            htmlBody += "</body></html>"; // Closing body tag

            // Set the email content as HTML
            message.setContent(htmlBody, "text/html");

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully to: " + recipient);
        } catch (MessagingException e) {
            throw new MessagingException("Error sending email: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
