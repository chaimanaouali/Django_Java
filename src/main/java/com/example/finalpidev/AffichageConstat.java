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
import models.constat;
import Services.ServiceConstat;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AffichageConstat implements Initializable {

    @FXML
    private TableView<constat> tableViewAff;

    private ServiceConstat sc = new ServiceConstat();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize your service class
        sc = new ServiceConstat();

        // Load data into the table
        loadDataIntoTable();

        tableViewAff.setRowFactory(tv -> {
            TableRow<constat> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    constat selectedConstat = row.getItem();
                    showConfirmationDialog(selectedConstat);
                }
            });
            return row;
        });
    }

    @FXML
    private void loadDataIntoTable() {
        try {
            // Read data from the database
            List<constat> m1 = sc.read();

            // Create an observable list from the data
            ObservableList<constat> data = FXCollections.observableArrayList(m1);

            // Set the data into the table
            tableViewAff.setItems(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void showConfirmationDialog(constat selectedConstat) {
        if (selectedConstat != null) {
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
                    deleteConstat(selectedConstat);
                } else if (result.get() == buttonTypeUpdate) {
                    updateConstat(selectedConstat);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No constat selected");
            alert.setContentText("Please select a constat from the table.");
            alert.showAndWait();
        }
    }

    private void deleteConstat(constat selectedConstat) {
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setTitle("Delete Confirmation");
        deleteConfirmation.setHeaderText("Delete Confirmation");
        deleteConfirmation.setContentText("Are you sure you want to delete this constat?");

        Optional<ButtonType> result = deleteConfirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Attempt to delete the constat
                sc.delete(selectedConstat);
                // If successful, reload the data into the table
                loadDataIntoTable();
            } catch (RelatedRecordsException e) {
                // If deletion is not possible due to related records, show an error message
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Cannot Delete Constast");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            } catch (SQLException ex) {
                // Handle other SQL exceptions
                ex.printStackTrace();
            }
        }
    }

    private void updateConstat(constat selectedConstat) {
        Stage currentStage = (Stage) tableViewAff.getScene().getWindow();
        currentStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MajConstat.fxml"));
            Parent root = loader.load();
            MajConstat controller = loader.getController();

            // Pass the selected constat object to the MajConstat controller
            controller.initData(selectedConstat);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void aff1(ActionEvent event) {
        Stage currentStage = (Stage) tableViewAff.getScene().getWindow();
        currentStage.close();

        // Open the new stage with AffichageConstat.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterConstat.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void aff2(ActionEvent actionEvent) {
        Stage currentStage = (Stage) tableViewAff.getScene().getWindow();
        currentStage.close();

        // Open the new stage with AffichageConstat.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageTraitement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void aff4(ActionEvent actionEvent) {
    }

    public void aff3(ActionEvent actionEvent) {
    }
}
