/*package utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class MailingService {

    private static String username ="lilyan85@ethereal.email";
    private static String host="smtp.ethereal.email";
    private  static String port="587";
    private static String password="JUkGNMvx6kBBcHA6rK";
    public static void SendMail(String to, String subject,String body){
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object
        javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               // return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress("django@gmail.com"));

            // Set To: header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully!");
        } catch (MessagingException mex) {
            System.out.println(mex.getMessage());
        }
    }
}
*/