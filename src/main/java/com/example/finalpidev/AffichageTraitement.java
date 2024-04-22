package com.example.finalpidev;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.traitement;
import Services.ServiceTraitement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichageTraitement implements Initializable {

    @FXML
    private TableView<traitement> tableViewAff;

    private ServiceTraitement st = new ServiceTraitement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize your service class
        st = new ServiceTraitement();

        // Load data into the table
        loadDataIntoTable();

        tableViewAff.setRowFactory(tv -> {
            TableRow<traitement> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    traitement selectedTraitement = row.getItem();
                    showConfirmationDialog(selectedTraitement);
                }
            });
            return row;
        });
    }

    @FXML
    private void loadDataIntoTable() {
        try {
            // Read data from the database
            List<traitement> m1 = st.read();

            // Create an observable list from the data
            ObservableList<traitement> data = FXCollections.observableArrayList(m1);

            // Set the data into the table
            tableViewAff.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void showConfirmationDialog(traitement selectedTraitement) {
        if (selectedTraitement != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Action Confirmation");
            alert.setContentText("Choose your action:");

            ButtonType buttonTypeDelete = new ButtonType("Delete");
            ButtonType buttonTypeUpdate = new ButtonType("Update");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeDelete, buttonTypeUpdate, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == buttonTypeDelete) {
                    deleteTraitement(selectedTraitement);
                } else if (result.get() == buttonTypeUpdate) {
                    updateTraitement(selectedTraitement);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No traitement selected");
            alert.setContentText("Please select a traitement from the table.");
            alert.showAndWait();
        }
    }

    private void deleteTraitement(traitement selectedTraitement) {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setTitle("Delete Confirmation");
        deleteConfirmation.setHeaderText("Delete Confirmation");
        deleteConfirmation.setContentText("Are you sure you want to delete this traitement?");

        Optional<ButtonType> result = deleteConfirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Attempt to delete the traitement
                st.delete(selectedTraitement);
                // If successful, reload the data into the table
                loadDataIntoTable();
            } catch (SQLException ex) {
                // Handle SQL exceptions
                ex.printStackTrace();
            } catch (RelatedRecordsException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateTraitement(traitement selectedTraitement) {
        Stage currentStage = (Stage) tableViewAff.getScene().getWindow();
        currentStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MajTraitement.fxml"));
            Parent root = loader.load();
            MajTraitement controller = loader.getController();

            // Pass the selected constat object to the MajConstat controller
            controller.initData(selectedTraitement);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void aff1(ActionEvent actionEvent) {
        Stage currentStage = (Stage) tableViewAff.getScene().getWindow();
        currentStage.close();

        // Open the new stage with AjouterTraitement.fxml
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterTraitement.fxml"));
            // Load the root node (layout)
            Parent root = loader.load();

            // Create a new scene with the root node
            Scene scene = new Scene(root);

            // Create a new stage
            Stage stage = new Stage();
            // Set the scene for the stage
            stage.setScene(scene);
            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void aff3(ActionEvent actionEvent) {
        Stage currentStage = (Stage) tableViewAff.getScene().getWindow();
        currentStage.close();

        // Open the new stage with AjouterTraitement.fxml
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageConstat.fxml"));
            // Load the root node (layout)
            Parent root = loader.load();

            // Create a new scene with the root node
            Scene scene = new Scene(root);

            // Create a new stage
            Stage stage = new Stage();
            // Set the scene for the stage
            stage.setScene(scene);
            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void aff4(ActionEvent actionEvent) {
        Stage currentStage = (Stage) tableViewAff.getScene().getWindow();
        currentStage.close();

        // Open the new stage with AjouterTraitement.fxml
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterTraitement.fxml"));
            // Load the root node (layout)
            Parent root = loader.load();

            // Create a new scene with the root node
            Scene scene = new Scene(root);

            // Create a new stage
            Stage stage = new Stage();
            // Set the scene for the stage
            stage.setScene(scene);
            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}