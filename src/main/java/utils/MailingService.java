package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailingService {
    private static final String USERNAME = "nickolas.mayer@ethereal.email";
    private static final String HOST = "smtp.ethereal.email";
    private static final String PORT = "587";
    private static final String PASSWORD = "CCaVSqRjzauCKyMBrM";

    public static void sendMail(String to, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Disable SSL certificate validation
        properties.put("mail.smtp.ssl.trust", "*");

        // Get the default Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(USERNAME));

            // Set To: header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Sent email successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            // Handle exception properly
        }
    }
}
