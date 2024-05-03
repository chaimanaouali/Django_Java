package com.example.djangoassurancejava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Type;
import services.ServiceType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterType implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField typedecouvertureTextField;


    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
        try {
            Image brandingImage = new Image(getClass().getResource("/images/thumbnail_image.png").toString());
            brandingImageView.setImage(brandingImage);
        } catch (Exception e) {
            e.printStackTrace();
        }}
    @FXML
    void returnList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ListType.fxml"));
        Stage stageu = new Stage();
        stageu.initStyle(StageStyle.UNDECORATED);
        stageu.setScene(new Scene(root, 1020,800));
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
        stageu.show();
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
/*
    @FXML
    void addButtonOnAction(ActionEvent event) {
       String type_de_couverture = typedecouvertureTextField.getText().trim();
        String description = descriptionTextField.getText().trim();

        if (type_de_couverture.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Stop execution if any field is empty
        }
        System.out.println("Type de couverture: " + type_de_couverture + ", Description: " + description);

    }*/
    @FXML
    void ajouter(ActionEvent event) {
        String selectedtypedecouverture = typedecouvertureTextField.getText();
        String selecteddescription = descriptionTextField.getText();

        // Regex to match only letters (and spaces to allow multi-word entries)


        // Check if any field is empty
        if (selectedtypedecouverture.isEmpty() || selecteddescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
            return;
        }


        // Create a new Type object with retrieved values
        Type type = new Type(selecteddescription, selectedtypedecouverture);
        ServiceType st = new ServiceType();

        try {
            st.ajouter(type);
            System.out.println("Type added successfully.");

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Type ajouté avec succès.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to add type: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du type : " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
