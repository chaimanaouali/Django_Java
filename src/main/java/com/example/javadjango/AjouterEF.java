package com.example.javadjango;
import Services.ServiceEvaluation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class AjouterEF {
        @FXML
        private TextField AvisE;
        @FXML
        private ComboBox<String> comboboxmec;
        @FXML
        private TextField MecanicienID;

        @FXML
        private TextField NomE;

        @FXML
        private TextField PrenomE;


    public Label getDateE() {
        return dateE;
    }

    public void setDateE(Label dateE) {
        this.dateE = dateE;
    }

    @FXML
    private Label dateE;

    public Label getNommecanicien() {
        return nommecanicien;
    }

    public void setNommecanicien(Label nommecanicien) {
        this.nommecanicien = nommecanicien;
    }

    @FXML
    private Label nommecanicien;
        private Connection connection;

    public int getId_mecanicien() {
        return id_mecanicien;
    }

    public void setId_mecanicien(int id_mecanicien) {
        this.id_mecanicien = id_mecanicien;
    }

    private int id_mecanicien;
        private final ServiceEvaluation re = new ServiceEvaluation();

        public void initialize() throws SQLException {

            LocalDate date = LocalDate.now(); // Obtenez la date actuelle
            String dateString = date.toString(); // Convertissez la date en une chaîne de caractères
            dateE.setText(dateString); // Assignez la chaîne de caractères au composant dateE

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:4306/assurance", "root", "");

            } catch (SQLException e) {
                e.printStackTrace();

            }

        }

    @FXML
    void btnAjouterE(ActionEvent event) {
        try {
            if (NomE.getText().isEmpty() || PrenomE.getText().isEmpty() || AvisE.getText().isEmpty() || dateE.getText() == null) {
                // Affichez une alerte si un champ est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
            } else {
                String dateEval = dateE.getText();
                String nomClient = NomE.getText();
                String prenomClient = PrenomE.getText();
                String avisClient = AvisE.getText();

                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:4306/assurance", "root", "");
                     PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO evaluation (mecanicien_id,nom_client, prenom_client, avis_client, date_eval) VALUES (?,?, ?, ?, ?)")) {
                    preparedStatement.setInt(1, id_mecanicien);
                    preparedStatement.setString(2, nomClient);
                    preparedStatement.setString(3, prenomClient);
                    preparedStatement.setString(4, avisClient);
                    preparedStatement.setDate(5, Date.valueOf(dateEval));

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        // Afficher une alerte de succès si l'ajout est réussi
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Succès");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("L'évaluation a été ajoutée avec succès.");
                        successAlert.showAndWait();

                        // Nettoyer les champs après l'ajout
                        NomE.clear();
                        PrenomE.clear();
                        AvisE.clear();

                        // comboboxmec.getSelectionModel().clearSelection();

                        // Ajouter la notification TrayNotification
                        TrayNotification tray = new TrayNotification();
                        tray.setTitle("Succès");
                        tray.setMessage("L'évaluation a été ajoutée avec succès.");
                        tray.setNotificationType(NotificationType.SUCCESS);
                        tray.showAndWait();

                    } else {
                        // Afficher une alerte si aucun enregistrement n'a été ajouté (ajout a échoué)
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Une erreur s'est produite lors de l'ajout de l'évaluation.");
                        errorAlert.showAndWait();
                    }
                }
            }
        } catch (NumberFormatException e) {
            // Afficher une alerte si une valeur numérique est invalide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer des valeurs numériques valides.");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }


    @FXML
        void btnaffichageajoutE(ActionEvent event) {
            try {
                // Charger le fichier FXML de la nouvelle scène
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherE.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Obtenir la référence à la scène actuelle
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Définir la nouvelle scène sur la fenêtre principale
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace(); // Gérer les erreurs de chargement du FXML
            }
        }


    }

