package com.example.assurance;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Devis;
import services.ServiceDevis;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AjouterDevis implements Initializable {

    @FXML
    private Button boutonAjouter;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField tfAdresse;

    @FXML
    private DatePicker tfDate;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfModele;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfNumtel;

    @FXML
    private TextField tfPrenom;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfPuissance;
    @FXML
    private ImageView brandingImageView;


    @FXML
    void insertOne(ActionEvent event) {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfAdresse.getText().isEmpty() ||
                tfEmail.getText().isEmpty() || tfDate.getValue() == null || tfModele.getText().isEmpty() ||
                tfPuissance.getText().isEmpty() || tfPrix.getText().isEmpty() || tfNumtel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs obligatoires!");
            alert.show();
        } else {
            try {
                // Vérification de l'e-mail
                if (!isValidEmail(tfEmail.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Veuillez saisir une adresse e-mail valide!");
                    alert.show();
                    return; // Arrête le traitement si l'e-mail n'est pas valide
                }

                // Vérification du numéro de téléphone
                if (!isValidPhoneNumber(tfNumtel.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Veuillez saisir un numéro de téléphone valide (8 chiffres)!");
                    alert.show();
                    return; // Arrête le traitement si le numéro de téléphone n'est pas valide
                }

                // Vérification de la date de naissance
                if (!isValidAge(tfDate.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Vous devez avoir au moins 18 ans!");
                    alert.show();
                    return; // Arrête le traitement si l'âge n'est pas valide
                }

                // Si toutes les validations sont réussies, procédez à l'insertion
                String selectedNom = tfNom.getText();
                String selectedPrenom = tfPrenom.getText();
                String selectedAdresse = tfAdresse.getText();
                String selectedEmail = tfEmail.getText();
                LocalDate selectedDatenaiss = tfDate.getValue(); // Assuming tfDate is a DatePicker
                String selectedModele = tfModele.getText();
                String selectedPuissance = tfPuissance.getText();
                double selectedPrix = Double.parseDouble(tfPrix.getText());
                int selectedNumtel = Integer.parseInt(tfNumtel.getText());

                Devis d = new Devis(selectedNom, selectedPrenom, selectedAdresse, selectedEmail, selectedDatenaiss, selectedNumtel, selectedModele, selectedPuissance, selectedPrix);
                ServiceDevis sp = new ServiceDevis();
                sp.insertOne(d);

                showNotification("Success", "Email reçus !!");

                Stage stage = (Stage) cancelButton.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherDevis.fxml"));
                Parent root = loader.load();

                // Show the scene containing the UpdateUser.fxml file
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root));

                stage1.show();
                stage.close();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private boolean isValidEmail(String email) {
        // Expression régulière pour valider l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Vérifie si la longueur du numéro de téléphone est de 8 chiffres
        return phoneNumber.matches("\\d{8}");
    }

    private boolean isValidAge(LocalDate birthDate) {
        // Calcul de l'âge à partir de la date de naissance
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.minusYears(18).compareTo(birthDate);
        return age >= 0; // Renvoie vrai si l'âge est de 18 ans ou plus
    }





    @FXML
    void cancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherDevis.fxml"));
        Parent root = loader.load();

        // Access the controller and pass the selected user to it


        // Show the scene containing the UpdateUser.fxml file
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
        stage1.close();
    }

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image brandingImage = new Image(getClass().getResource("/images/django-removebg-preview.png").toString());
            brandingImageView.setImage(brandingImage);
        } catch (Exception e) {
            e.printStackTrace();
        }}

    private void showNotification(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null); // No header text
            alert.setContentText(content);
            alert.showAndWait(); // Wait for user to close the dialog
        });


    }
}
