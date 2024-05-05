package com.example.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Commentaire;
import models.User;
import services.ServiceCommentaire;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static utils.SessionManager.clearSession;


public class AfficherCommentaire {
    @FXML
    private Button PostButton;
    @FXML
    private Button voitureButton;
    User currentUser;

    @FXML
    private Button userButton;
    @FXML
    private Button evaluationButton;
    @FXML
    private Button mecanicienButton;


    @FXML
    private TableColumn<Commentaire, String> auteur;

    @FXML
    private TableColumn<Commentaire, String> contenu;
    @FXML
    private Button btajouterCommentaire;
    @FXML
    private Button supprimerButton;

    @FXML
    private AnchorPane mainForm;

    @FXML
    private TableView<Commentaire> table;

    @FXML
    private Button logoutButton;
    @FXML
    private Button homeBt;

    public void initialize() {
        // Set cell value factories for the columns
        auteur.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuteur()));
        contenu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContenu()));

        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView() throws SQLException {
        // Retrieve data from the database
        ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
        List<Commentaire> commentaires = serviceCommentaire.selectAll();

        // Clear existing items in the TableView
        table.getItems().clear();

        // Add the retrieved data to the TableView
        table.getItems().addAll(commentaires);

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Commentaire selectedCommentaire = table.getSelectionModel().getSelectedItem();
                if (selectedCommentaire != null) {
                    // Navigate to UpdateUser.fxml
                    navigateToUpdatePostXML(selectedCommentaire);
                }
            }
        });
    }



    @FXML
    void deleteOne(ActionEvent event) {
        // Get the selected commentaire from the table view
        Commentaire selectedCommentaire = table.getSelectionModel().getSelectedItem();
        if (selectedCommentaire != null) {
            try {
                // Call the deleteOne method with the selected commentaire
                ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
                serviceCommentaire.deleteOne(selectedCommentaire);
                // Show a confirmation message
                showAlert("Delete Success", "Commentaire deleted successfully.");
                // Refresh the table view after deletion
                populateTableView();
            } catch (SQLException ex) {
                // Show an error message if deletion fails
                showAlert("Delete Error", "Error deleting commentaire: " + ex.getMessage());
            }
        } else {
            // Show an alert if no commentaire is selected
            showAlert("No Selection", "Please select a Commentaire to delete.");
        }
    }

    /*@FXML
    void redirectToAjouterCommentaire(ActionEvent event) {
        try {
            // Load ajouterCommnentaire.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouterCommnentaire.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Handle the IOException
            e.printStackTrace();
            showAlert("Error", "Failed to redirect to Ajouter: " + e.getMessage());
        }
    }*/

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void navigateToUpdatePostXML(Commentaire selectedCommentaire) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCommentaire.fxml"));
            Parent root = loader.load();

            updateCommentaire controller = loader.getController();
            controller.initData(selectedCommentaire);
            controller.setAfficherCommentaire(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                try {
                    populateTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open update window: " + e.getMessage());
        }
    }

    public void refreshList() {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void navigateToAddCommentaire() {
        try {
            // Load the AddVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouterCommnentaire.fxml"));
            Parent root = loader.load();

            // Show the scene containing the AddVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table view after adding a new voiture
            populateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterButtonOnAction(ActionEvent event) {
        navigateToAddCommentaire();
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
    void userButtonOnAction(ActionEvent event){

        Stage stage = (Stage) userButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToUser();    }
    private void navigateToUser() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListUser.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            ListUserController controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
    void homeButtonOnAction(ActionEvent event){

        Stage stage = (Stage) homeBt.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToHome();    }
    private void navigateToHome() {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller and pass the selected user to it
            Home controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

