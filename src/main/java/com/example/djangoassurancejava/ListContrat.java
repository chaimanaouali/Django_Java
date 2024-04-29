package com.example.djangoassurancejava;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Contrat;
import models.Type;
import org.apache.pdfbox.pdmodel.font.PDFont;
import services.ServiceContrat;
import services.ServiceType;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javafx.scene.input.MouseEvent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;


public class ListContrat {

    @FXML
    private TableView<Contrat> TypeTableView;

    @FXML
    private TableColumn<Contrat, String> adresseCol;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button pdf;

    @FXML
    private Button cancelButton;
    ObservableList<Contrat> list = FXCollections.observableArrayList();
    private final  ServiceContrat cs =new ServiceContrat();
    private ServiceContrat css =new ServiceContrat();
    private ListView<Contrat> lv;

    @FXML
    private TableColumn<Contrat, Date> datedCol;

    @FXML
    private TableColumn<Contrat, Date> datefCol;

    @FXML
    private TableColumn<Contrat, String> emailCol;

    @FXML
    private TableColumn<Contrat, Integer> idCol;

    @FXML
    private TableColumn<Contrat, String> nomCol;

    @FXML
    private TableColumn<Contrat, String> numeroCol;

    @FXML
    private TableColumn<Contrat, String> prenomCol;

    @FXML
    private Button supprimerButton;

    @FXML
    private TableColumn<Contrat, String> typeCol;

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Contrat.fxml"));
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
        ServiceContrat serviceType = new ServiceContrat();
        List<Contrat> typeList = serviceType.recuperer();

        TypeTableView.getItems().clear();

        // For the integer property
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        // For the String properties
        typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType_couverture()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        adresseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse_assur()));
        numeroCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero_assur()));
        datedCol.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDate_debut_contrat()));
        datefCol.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDatefin_contrat()));

        TypeTableView.getItems().addAll(typeList);

        TypeTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Contrat selectedType = TypeTableView.getSelectionModel().getSelectedItem();
                if (selectedType != null) {
                    try {
                        navigateToUpdateType(selectedType);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        });
    }

    private void navigateToUpdateType(Contrat contrat) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateContrat.fxml"));
            Parent root = loader.load();

            UpdateContrat controller = loader.getController();
            controller.initData(contrat);

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
        Contrat selectedcontrat = TypeTableView.getSelectionModel().getSelectedItem();
        if (selectedcontrat != null) {
            try {
                // Call the deleteOne method with the selected type
                ServiceContrat serviceContrat = new ServiceContrat();
                serviceContrat.supprimer(selectedcontrat.getId());
                // Show a confirmation message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Success");
                alert.setHeaderText(null);
                alert.setContentText("Contrat deleted successfully.");
                alert.showAndWait();
                // Refresh the table view after deletion
                populateTableView();
            } catch (SQLException ex) {
                // Show an error message if deletion fails
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setHeaderText(null);
                alert.setContentText("Error deleting Contrat: " + ex.getMessage());
                alert.showAndWait();
            }
        } else {
            // Show an alert if no type is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a Contract to delete.");
            alert.showAndWait();
        }
    }
    public void pdf(ActionEvent actionEvent) throws IOException {
        ObservableList<Contrat> data = TypeTableView.getItems();
        PDDocument document = new PDDocument();
        try {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set a custom font
            PDFont fontBold = PDType1Font.HELVETICA_BOLD;
            PDFont fontRegular = PDType1Font.HELVETICA;

            contentStream.beginText();
            contentStream.setFont(fontBold, 14);
            contentStream.setLeading(20f);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Contrat Details");
            contentStream.newLine();

            // Reset to regular font for contract details
            contentStream.setFont(fontRegular, 12);

            for (Contrat ca : data) {
                String line = "ID: " + ca.getId() + " - Nom: " + ca.getNom() +
                        " - Prenom: " + ca.getPrenom() + " - Email: " + ca.getEmail() +
                        " - Adresse: " + ca.getAdresse_assur() + " - Numero: " + ca.getNumero_assur() +
                        " - Type de couverture: " + ca.getType_couverture_id() +
                        " - Date debut du contrat: " + ca.getDate_debut_contrat() +
                        " - Date fin du contrat: " + ca.getDatefin_contrat();
                contentStream.showText(line);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            // Save and open the document
            String outputPath = "C:/Users/Lenovo/Desktop/zahra/contrat.pdf";
            File file = new File(outputPath);
            document.save(file);
            document.close();

            // Show a success popup
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF a été généré avec succès.");
            alert.showAndWait();

            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to create PDF");
            errorAlert.setContentText("An error occurred while creating the PDF: " + e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

}
