package com.example.finalpidev;


import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.constat;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import models.constat;
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
    private ImageView prod_imageView;

    private constat constat;

    public void setData(constat c){
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
            byte[] qrCodeImage = Qrcode.generateQRCode("http://127.0.0.1:8000/eventdetails", c.getId());

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
}

