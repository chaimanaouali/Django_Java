package com.example.user;

import com.example.user.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import static utils.SessionManager.clearSession;

public class Home {

    @FXML
    private Button PostButton;

    @FXML
    private Button contratButton;

    @FXML
    private Button devisButton;

    @FXML
    private Button mecanicienButton;

    @FXML
    private Button voitureButton;
    @FXML
    private Button evaluationButton;
    @FXML
    private Button commentaireButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button constat;


    @FXML
    void evaluationButtonOnAction(ActionEvent event){

        Stage stage = (Stage) evaluationButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToEvaluation();    }
    private void navigateToEvaluation() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherE.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AfficherE controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void mecanicienButtonOnAction(ActionEvent event){

        Stage stage = (Stage) mecanicienButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToMecanicien();    }
    private void navigateToMecanicien() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherM.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AfficherM controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void devisButtonOnAction(ActionEvent event){

        Stage stage = (Stage) devisButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToDevis();    }
    private void navigateToDevis() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            GestionDevis controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void contratButtonOnAction(ActionEvent event){

        Stage stage = (Stage) contratButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToContrat();    }
    private void navigateToContrat() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("test2.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            GestionContrat controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void CommentaireButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) commentaireButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToCommentaire();
    }

    private void navigateToCommentaire() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCommentaire.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AfficherCommentaire controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void PostButtonOnAction(ActionEvent event){

        Stage stage = (Stage) PostButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToPost();    }
    private void navigateToPost() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherPost.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AfficherPostXML controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void voitureButtonOnAction(ActionEvent event){

        Stage stage = (Stage) voitureButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToVoiture();    }
    private void navigateToVoiture() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListVoiture.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            ListVoitureController controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void logoutButtonOnAction(ActionEvent event) {
        // Create a confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Êtes-vous sûr de vouloir vous déconnecter?");

        // Show the confirmation dialog
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // User confirmed, clear the session and navigate to the login window
                clearSession();
                // Close the current window
                Stage stage = (Stage) logoutButton.getScene().getWindow();
                stage.close();
                // Navigate to the login window
                navigateToLogin();
            }
        });
    }
    private void navigateToLogin() {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller and pass the selected user to it
            LoginController controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void constatButtonOnAction(ActionEvent event){

        Stage stage = (Stage) constat.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToConstat();    }
    private void navigateToConstat() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageConstat.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AffichageConstat controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
