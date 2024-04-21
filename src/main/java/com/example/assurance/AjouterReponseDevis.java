package com.example.assurance;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Devis;
import models.ReponseDevis;
import services.ServiceDevis;
import services.ServiceReponseDevis;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterReponseDevis implements Initializable {
    private  Path source ;
    @FXML
    private Button boutonAjouter;
    @FXML
    private Button choose;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField tfAdresse;

    @FXML
    private DatePicker tfDate;

    @FXML
    private ComboBox tfEmail;

    @FXML
    private TextField tfModele;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfNumtel;

    @FXML
    private TextField tfPrenom;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfPuissance;
    @FXML
    private ImageView brandingImageView;
    @FXML
    void ChooseF(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage mainStage = (Stage) cancelButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {

            source = Path.of(selectedFile.getAbsolutePath()); // Destination within your project directory
             tfAdresse.setText(source.toString());

        }
    }


    @FXML
    void insertOne(ActionEvent event) {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfAdresse.getText().isEmpty() ||
                  tfDate.getValue() == null || tfModele.getText().isEmpty() ||
                tfPuissance.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs obligatoires!");
            alert.show();
        } else {
            try {

                String selectedEtat = tfNom.getText();
                String selectedDecision = tfPrenom.getText();
                String selectedDocuments = tfAdresse.getText();
                String selectedEmail1 = (String)tfEmail.getValue();
                LocalDate selectedDate = tfDate.getValue(); // Assuming tfDate is a DatePicker
                String selectedDelai = tfModele.getText();
                String selectedDuree = tfPuissance.getText();
                ServiceDevis dss = new ServiceDevis();
                List<Devis> ddd = dss.selectAll();
                int selectedEmail =1;

                for (Devis d: ddd
                ) {

                    if (d.getEmail().equals(selectedEmail1) ){
                        selectedEmail=d.getId();

                    }

                }

                ReponseDevis d = new ReponseDevis(selectedEmail, selectedEtat, selectedDecision,selectedDelai,  selectedDuree, selectedDocuments,selectedDate);
                ServiceReponseDevis sp = new ServiceReponseDevis();
                sp.insertOne(d);


                Path target = Paths.get("src/main/resources/Documents/"+selectedEmail1+".txt"); // Destination within your project directory
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

                Stage stage = (Stage) cancelButton.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherReponseDevis.fxml"));
                Parent root = loader.load();

                // Access the controller and pass the selected user to it


                // Show the scene containing the UpdateUser.fxml file
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root));

                stage1.show();
                stage.close();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherReponseDevis.fxml"));
        Parent root = loader.load();

        // Access the controller and pass the selected user to it


        // Show the scene containing the UpdateUser.fxml file
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
        stage1.close();
    }

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image brandingImage = new Image(getClass().getResource("/images/django-removebg-preview.png").toString());
            brandingImageView.setImage(brandingImage);
            ServiceDevis ds = new ServiceDevis();
            List<Devis> dd = ds.selectAll();
            List<String> Emails = new ArrayList<>();
            for (Devis d: dd
            ) {
                Emails.add(d.getEmail());

            }
            System.out.println(Emails);
            for (String e: Emails
            ) {
                System.out.println("dddd");
                tfEmail.getItems().add(e);
            }
            tfEmail.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }}

}
