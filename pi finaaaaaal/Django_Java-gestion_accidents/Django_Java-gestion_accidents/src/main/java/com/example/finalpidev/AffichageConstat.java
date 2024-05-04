package com.example.finalpidev;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
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



import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class AffichageConstat implements Initializable {

    @FXML
    private TableView<constat> tableViewAff;
    @FXML
    private TextField usersearch;
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
    void triDate(ActionEvent event) {

        try {
            // Perform search and retrieve data
            List<constat> reclamationList = sc.Tri();

            // Create an observable list from the search result
            ObservableList<constat> data = FXCollections.observableArrayList(reclamationList);

            // Clear existing items in the table
            tableViewAff.getItems().clear();

            // Set the data into the table
            tableViewAff.setItems(data);

        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }


    @FXML
    void RechercheNom(ActionEvent event) {
        String recherche = usersearch.getText();
        try {
            // Perform search and retrieve data
            List<constat> reclamationList = sc.Rechreche(recherche);

            // Create an observable list from the search result
            ObservableList<constat> data = FXCollections.observableArrayList(reclamationList);

            // Clear existing items in the table
            tableViewAff.getItems().clear();

            // Set the data into the table
            tableViewAff.setItems(data);

        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception appropriately
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
    @FXML
    void extract(ActionEvent event) {
        try {
            generatePDF();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void generatePDF() throws FileNotFoundException, SQLException {
        // Get the path to the Downloads directory
        String downloadsDir = System.getProperty("user.home") + "/Downloads/";

        // Create a PDF file in the Downloads directory
        File file = new File(downloadsDir + "Constats.pdf");
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);

        // Create a document
        Document document = new Document(pdf);

        // Add content to the document
        for (constat reclamation : sc.read()) {
            document.add(new Paragraph("ID:       " + reclamation.getId()));
            document.add(new Paragraph("Lieu:       " + reclamation.getLieu()));
            document.add(new Paragraph("description:    " + reclamation.getDescription()));
            document.add(new Paragraph("Condition route: " + reclamation.getConditionroute()));
            document.add(new Paragraph("rapport de police: " + reclamation.getRapportpolice()));
            document.add(new Paragraph("date: " + reclamation.getDate()));
            document.add(new Paragraph("\n")); // Add a blank line between users
        }
        // Close the document
        document.close();

        System.out.println("PDF file generated successfully at: " + file.getAbsolutePath());
    }
}

