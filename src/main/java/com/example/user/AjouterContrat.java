package com.example.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.Contrat;
import models.Type;
import services.ServiceContrat;
import services.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterContrat implements Initializable {

    @FXML
    private TextField adresseTextField;
    @FXML
    private Button addButton;
    @FXML
    private DatePicker datedebutDatePicker;

    @FXML
    private DatePicker datefinDatePicker;
    @FXML
    private ImageView brandingImageView;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nomTextField;
    @FXML
    private TextField numeroTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox typedecouvertureChoiceBox;

    private List<Popup> currentPopups = new ArrayList<>();


    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle ressourceBundle) {
        try {
            System.out.println("yes");
            Image brandingImage = new Image(getClass().getResource("/images/thumbnail_image.png").toString());
            brandingImageView.setImage(brandingImage);
            ServiceType sc = new ServiceType();
            List<Type> t = sc.recuperer();
            List<String> desc = new ArrayList<>();
            for (Type type: t
                 ) {
                desc.add(type.getDescription());
            }
            for (String e:desc
                 ) {
                typedecouvertureChoiceBox.getItems().add(e);
            }
            typedecouvertureChoiceBox.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }}
    public void ajouter(ActionEvent event) throws SQLException {
        LocalDate dated = datedebutDatePicker.getValue();
        LocalDate datef = datefinDatePicker.getValue();
        String adress_assur = adresseTextField.getText();
        String numero = numeroTextField.getText();
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String email = emailTextField.getText();
        String selecteddescription = (String) typedecouvertureChoiceBox.getValue();

        // Retrieve and find the corresponding type ID
        ServiceType sty = new ServiceType();
        List<Type> tt = sty.recuperer();
        int selectedDesc = 1;



        clearPopups();  // Clear any existing popups

        // Validation regex patterns
        String nameRegex = "^[A-Z][a-z]+$";
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String phoneRegex = "^[529][0-9]{7}$";

        try {
            // Check for required fields
            boolean valid = true;
            if (dated == null || datef == null || adress_assur.isEmpty() || numero.isEmpty() ||
                    nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || selecteddescription == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
                return;
            }

            // Validate name and surname
            if (!nom.matches(nameRegex)) {
                showErrorPopup(nomTextField, "Le nom doit commencer par une majuscule et contenir uniquement des lettres.");
                valid = false;
            }
            if (!prenom.matches(nameRegex)) {
                showErrorPopup(prenomTextField, "Le prénom doit commencer par une majuscule et contenir uniquement des lettres.");
                valid = false;
            }

            // Validate email
            if (!email.matches(emailRegex)) {
                showErrorPopup(emailTextField, "L'adresse email n'est pas valide.");
                valid = false;
            }

            // Validate phone number
            if (!numero.matches(phoneRegex)) {
                showErrorPopup(numeroTextField, "Le numéro de téléphone doit commencer par 5, 2 ou 9 et contenir exactement 8 chiffres.");
                valid = false;
            }

            // Check date order
            if (datef.isBefore(dated)) {
                showErrorPopup(datefinDatePicker, "La date de fin doit être après la date de début.");
                valid = false;
            }

            if (!valid) {
                return;
            }

            // Proceed with creating a contract object and adding to the database
            Contrat contrat = new Contrat(selectedDesc, dated, datef, adress_assur, numero, nom, prenom, email);
            ServiceContrat st = new ServiceContrat();
            st.ajouter(contrat);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Contrat ajouté avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du contrat : " + e.getMessage());
        }
    }

    private void showErrorPopup(Control field, String message) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label(message);
        label.setStyle("-fx-background-color: white; -fx-text-fill: red; -fx-padding: 5px; -fx-border-color: red; -fx-border-width: 2px;");
        popup.getContent().add(label);
        Bounds bounds = field.localToScreen(field.getBoundsInLocal());
        popup.show(field, bounds.getMinX() - 5, bounds.getMaxY());
        currentPopups.add(popup);
    }

    private void clearPopups() {
        for (Popup popup : currentPopups) {
            if (popup.isShowing()) {
                popup.hide();
            }
        }
        currentPopups.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


