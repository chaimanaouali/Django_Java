package com.example.djangoassurancejava;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Type;
import services.ServiceType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListType {

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Type, String> descriptionCol;

    @FXML
    private TableColumn<Type, Integer> idCol;

    @FXML
    private Button supprimerButton;
    @FXML
    private Button ajouterButton;

    @FXML
    private TableColumn<Type, String> typeCol;

    @FXML
    private TableView<Type> TypeTableView; // Ensure the TableView is of type Type

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Type.fxml"));
        Stage stageu = new Stage();
        stageu.initStyle(StageStyle.UNDECORATED);
        stageu.setScene(new Scene(root, 1020,800));
        Stage stage = (Stage) supprimerButton.getScene().getWindow();
        stage.close();
        stageu.show();
    }

    public void initialize() {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider more robust error handling or showing a user-friendly message
        }
    }

    private void populateTableView() throws SQLException {
        ServiceType serviceType = new ServiceType();
        List<Type> typeList = serviceType.recuperer();

        TypeTableView.getItems().clear();

        // For the integer property
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        // For the String properties
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType_couverture()));
        descriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        TypeTableView.getItems().addAll(typeList);

        TypeTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Type selectedType = TypeTableView.getSelectionModel().getSelectedItem();
                if (selectedType != null) {
                    navigateToUpdateType(selectedType);



                }
            }
        });
    }

    private void navigateToUpdateType(Type type) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateType.fxml"));
            Parent root = loader.load();

            UpdateType controller = loader.getController();
            controller.initData(type);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                try {
                    populateTableView();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Consider more robust error handling here
                }
            });
            stage.show();
            Stage stage1 = (Stage) supprimerButton.getScene().getWindow();
            stage1.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Consider logging or showing an error dialog here
        }
    }
    @FXML
    private void supprimer(ActionEvent event) {
        // Get the selected type from the table view
        Type selectedtype = TypeTableView.getSelectionModel().getSelectedItem();
        if (selectedtype != null) {
            try {
                // Call the deleteOne method with the selected type
                ServiceType serviceType = new ServiceType();
                serviceType.supprimer(selectedtype.getId());
                // Show a confirmation message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Success");
                alert.setHeaderText(null);
                alert.setContentText("Type deleted successfully.");
                alert.showAndWait();
                // Refresh the table view after deletion
                populateTableView();
            } catch (SQLException ex) {
                // Show an error message if deletion fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setHeaderText(null);
                alert.setContentText("Error deleting Type: " + ex.getMessage());
                alert.showAndWait();
            }
        } else {
            // Show an alert if no type is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a Type to delete.");
            alert.showAndWait();
        }
    }

}
