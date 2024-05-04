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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.ReponseDevis;
import services.ServiceReponseDevis;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class afficherReponseDevis implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<ReponseDevis, LocalDate> date_reglementCol;

    @FXML
    private TableColumn<ReponseDevis, String> decisionCol;

    @FXML
    private TableColumn<ReponseDevis, String> delaiCol;

    @FXML
    private TableView<ReponseDevis> devisTableView;

    @FXML
    private TableColumn<ReponseDevis, String> documentsCol;

    @FXML
    private TableColumn<ReponseDevis, String> dureeCol;

    @FXML
    private TableColumn<ReponseDevis, String> emailCol;

    @FXML
    private TableColumn<ReponseDevis, String> etatCol;

    @FXML
    private TableColumn<ReponseDevis, Integer> idCol;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button supprimerButton1;
    @FXML
    void getDevis(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("afficherDevis.fxml"));
        Stage pStage= new Stage();
        pStage.initStyle(StageStyle.UNDECORATED);
        pStage.setScene(new Scene(root, 667,556));
        pStage.show();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
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
        ServiceReponseDevis serviceDevis = new ServiceReponseDevis(); // Creating an instance of ServiceDevis
        List<ReponseDevis> devisList = serviceDevis.selectAll(); // Calling selectAll() on the instance

        // Clear existing items in the TableView
        devisTableView.getItems().clear();

        // Set cell value factories for each column
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        etatCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtat()));
        decisionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDecision()));
        emailCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmail_id()).asString());
        delaiCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDelai_reparation()));
        date_reglementCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate_reglement()));
        dureeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuree_validite()));
        documentsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocuments()));

        // Add the retrieved data to the TableView
        devisTableView.getItems().addAll(devisList);

        devisTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                ReponseDevis selectedDevis = devisTableView.getSelectionModel().getSelectedItem();
                if (selectedDevis != null) {
                    // Navigate to UpdateUser.fxml
                    navigateToUpdateDevis(selectedDevis);
                }
            }
        });
    }


    private void navigateToUpdateDevis(ReponseDevis devis) {
            try {
                // Load the UpdateUser.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateReponseDevis.fxml"));
                Parent root = loader.load();

                // Access the controller and pass the selected user to it
                updateReponseDevis controller = loader.getController();
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }


    @FXML
    private void AjoutOne(ActionEvent event) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReponseDevis.fxml"));
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
