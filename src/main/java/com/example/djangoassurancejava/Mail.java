package com.example.djangoassurancejava;

import java.io.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import com.sun.mail.smtp.SMTPTransport;
import java.lang.reflect.InvocationTargetException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Mail {

    @FXML
    private Button btn;

    @FXML
    private TextArea messageArea;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField toField;

    @FXML
    void sendEmail(ActionEvent event) {
        String to = toField.getText();
        String subject = subjectField.getText();
        //String mmessage = messageArea.getText();
        String username = "zahra.zellazi@esprit.tn";
        String password = "lfpl lmhr jeru enfs";

        Properties props = System.getProperties();
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("zahra.zellazi@esprit.tn", "lfpl lmhr jeru enfs");
                    }
                });

        String messageEmail=messageArea.getText();
        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("zahra.zellazi@esprit.tn"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject); // Set the subject of the email
            message.setText(messageEmail); // Set the content of the email
            //message.setContent(messageEmail,"text/html");
            // Enable debugging
            session.setDebug(true);

            // Send the message
            Transport.send(message);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Email sent successfully.");
            alert.showAndWait();

        } catch (MessagingException e) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }

    }

    public void start(Stage stage) throws Exception {


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // To
        Label toLabel = new Label("To:");
        GridPane.setConstraints(toLabel, 0, 0);
        toField = new TextField();
        toField.setPromptText("Recipient's Email");
        GridPane.setConstraints(toField, 1, 0);

        // Subject
        Label subjectLabel = new Label("Subject:");
        GridPane.setConstraints(subjectLabel, 0, 1);
        subjectField = new TextField();
        subjectField.setPromptText("Subject");
        GridPane.setConstraints(subjectField, 1, 1);

        // Message
        Label messageLabel = new Label("Message:");
        GridPane.setConstraints(messageLabel, 0, 2);
        messageArea = new TextArea();
        messageArea.setPromptText("Type your message here");
        messageArea.setPrefRowCount(5);
        GridPane.setConstraints(messageArea, 1, 2);

        // Send Button
        Button sendButton = new Button("Send");
        //sendButton.setOnAction(e -> sendEmail());
        GridPane.setConstraints(sendButton, 1, 3);

        grid.getChildren().addAll(toLabel, toField, subjectLabel, subjectField, messageLabel, messageArea, sendButton);

        Scene scene = new Scene(grid, 400, 250);


    }
}