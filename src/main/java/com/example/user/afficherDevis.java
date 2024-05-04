package com.example.user;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Devis;
import services.ServiceDevis;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
public class afficherDevis implements Initializable {

    @FXML
    private TableColumn<Devis, String> adresseCol;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Devis, LocalDate> datenaissCol;

    @FXML
    private TableColumn<Devis, String> emailCol;

    @FXML
    private TableColumn<Devis, Integer> idCol;

    @FXML
    private TableColumn<Devis, String> modeleCol1;

    @FXML
    private TableColumn<Devis, String> nomCol;

    @FXML
    private TableColumn<Devis, Integer> numtelCol1111;

    @FXML
    private TableColumn<Devis, String> prenomCol;

    @FXML
    private TableColumn<Devis, Double> prixCol111;

    @FXML
    private TableColumn<Devis, String> puissanceCol11;

    @FXML
    private Button supprimerButton;
    @FXML
    private TextField recherchee;


    @FXML
    private TableView<Devis> devisTableView;
    @FXML
    void getReponseDevis(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("afficherReponseDevis.fxml"));
        Stage pStage= new Stage();
        pStage.initStyle(StageStyle.UNDECORATED);
        pStage.setScene(new Scene(root, 667,556));
        pStage.show();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void chart(ActionEvent ev) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chart.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            chart controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                try {
                    // Refresh the TableView when the PopUp stage is closed
                    populateTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            stage.show();
            Stage gg= (Stage)cancelButton.getScene().getWindow();
            gg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void recherche(KeyEvent event) {
        System.out.println("dd");
        devisTableView.getItems().clear();
        String charr = recherchee.getText();
        System.out.println(charr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        // Retrieve data from the database
        ServiceDevis serviceDevis = new ServiceDevis(); // Creating an instance of ServiceDevis
        List<Devis> devisList = null; // Calling selectAll() on the instance
        try {
            devisList = serviceDevis.rechercheF(charr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Clear existing items in the TableView
        devisTableView.getItems().clear();

        // Set cell value factories for each column
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        adresseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        datenaissCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate_naiss()));
        modeleCol1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        puissanceCol11.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPuissance()));
        prixCol111.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));
        numtelCol1111.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNum_tel()));

        // Add the retrieved data to the TableView
        devisTableView.getItems().addAll(devisList);

        devisTableView.setOnMouseClicked(evt -> {
            if (evt.getClickCount() == 2) { // Double-click detected
                Devis selectedDevis = devisTableView.getSelectionModel().getSelectedItem();
                if (selectedDevis != null) {
                    // Navigate to UpdateUser.fxml
                    navigateToUpdateDevis(selectedDevis);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void populateTableView() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        // Retrieve data from the database
        ServiceDevis serviceDevis = new ServiceDevis(); // Creating an instance of ServiceDevis
        List<Devis> devisList = serviceDevis.selectAll(); // Calling selectAll() on the instance

        // Clear existing items in the TableView
        devisTableView.getItems().clear();

        // Set cell value factories for each column
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        adresseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        datenaissCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate_naiss()));
        modeleCol1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
        puissanceCol11.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPuissance()));
        prixCol111.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrix()));
        numtelCol1111.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNum_tel()));

        // Add the retrieved data to the TableView
        devisTableView.getItems().addAll(devisList);

        devisTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Devis selectedDevis = devisTableView.getSelectionModel().getSelectedItem();
                if (selectedDevis != null) {
                    // Navigate to UpdateUser.fxml
                    navigateToUpdateDevis(selectedDevis);
                }
            }
        });
    }


    private void navigateToUpdateDevis(Devis devis) {
            try {
                // Load the UpdateUser.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDevis.fxml"));
                Parent root = loader.load();

                // Access the controller and pass the selected user to it
                updateDevis controller = loader.getController();
                controller.initData(devis);

                // Show the scene containing the UpdateUser.fxml file
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setOnCloseRequest(event -> {
                    try {
                        // Refresh the TableView when the PopUp stage is closed
                        populateTableView();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                stage.show();
               Stage gg= (Stage)cancelButton.getScene().getWindow();
               gg.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    @FXML
    private void AjoutOne(ActionEvent event) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterDevis.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
            Stage gg= (Stage)cancelButton.getScene().getWindow();
            gg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
