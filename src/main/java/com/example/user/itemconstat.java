package com.example.user;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.constat;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;

public class itemconstat {


    private Image image;
    @FXML
    private Button details;
    private String prod_image;
    @FXML
    private Label ccondition;

    @FXML
    private Label cdate;

    @FXML
    private Label cid;

    @FXML
    private Label clieu;
    @FXML
    private AnchorPane card;
    @FXML
    private Button btwhats;




    @FXML
    private ImageView prod_imageView;

    private constat constat;

    public void setData(constat c){
        String imagePath = c.getPhoto();

        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                prod_imageView.setImage(image);
            } else {

                System.out.println("L'image n'existe pas : " + imagePath);
            }
        } else {
            System.out.println("Le chemin de l'image est vide.");
        }

        clieu.setText(c.getLieu());
        ccondition.setText(c.getConditionroute());

        // Formatting the date for better readability
        String formattedDate = "";
        if (c.getDate() != null) {
            formattedDate = c.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        }
        cdate.setText(formattedDate);

        // Setting the ID directly
        cid.setText(String.valueOf(c.getId()));
        this.constat=c;
    }





    private void generateAndShowQRCode(constat c) {
        try {


            // Generate the QR code for the event
            byte[] qrCodeImage = Qrcode.generateQRCode("http://127.0.0.1:8000/showfront", c.getId());

            // Convert the byte array to an Image
            InputStream inputStream = new ByteArrayInputStream(qrCodeImage);
            Image qrCode = new Image(inputStream);

            // Display the QR code in a dialog
            Dialog<Image> dialog = new Dialog<>();
            dialog.setTitle("QR Code");
            dialog.setHeaderText("QR Code for " + constat.getId());
            dialog.setResizable(true);

            // Set the image as dialog content
            dialog.getDialogPane().setContent(new ImageView(qrCode));

            // Add a close button
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().add(closeButton);

            // Show the dialog
            dialog.showAndWait();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate QR code.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }


    @FXML
    private void handleShowID(ActionEvent event) {
        if (constat != null) {
            int constatId = constat.getId();
            showAlert(Alert.AlertType.INFORMATION, "Consta ID", "The ID of this constat is: " + constatId);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Constat is null.");
        }
    }




    public void generate(ActionEvent actionEvent) {
        generateAndShowQRCode(this.constat);

    }

    public void shareViaWhatsApp(String message) {
        try {
            Desktop.getDesktop().browse(new URI("https://api.whatsapp.com/send?text=" + URLEncoder.encode(message, "UTF-8")));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Assuming this method is called on some kind of action event
    public void shareViaWhatsApp(ActionEvent event) {
        String message = "on a recu votre constat  en vous contatcte le plustot possible chere client"; // Provide the message you want to share
        shareViaWhatsApp(message);
    }

    }

