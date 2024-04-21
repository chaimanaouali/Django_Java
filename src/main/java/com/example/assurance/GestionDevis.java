/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.assurance;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Devis;
import models.ReponseDevis;
import services.ServiceDevis;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import services.ServiceReponseDevis;

/**
 * FXML Controller class
 *
 * @author chaima
 */
public class GestionDevis implements Initializable {
    @FXML
    private Label nomCol;

    @FXML
    private Label numtelCol1111;

    @FXML
    private Label numtelCol11111;

    @FXML
    private Label prenomCol;

    @FXML
    private Label prixCol111;

    @FXML
    private Label puissanceCol11;

    @FXML
    private TextField tfAdresse;

    @FXML
    private TextField tfAdresse1;

    @FXML
    private DatePicker tfDate;

    @FXML
    private DatePicker tfDate1;

    @FXML
    private TextField tfEmail;

    @FXML
    private ComboBox<String> tfEmail1;

    @FXML
    private TextField tfModele;

    @FXML
    private TextField tfModele1;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfNom1;

    @FXML
    private TextField tfNumtel;

    @FXML
    private TextField tfPrenom;

    @FXML
    private TextField tfPrenom1;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfPuissance;

    @FXML
    private TextField tfPuissance1;
    @FXML
    void insertOne(ActionEvent event) {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfAdresse.getText().isEmpty() ||
                tfEmail.getText().isEmpty() || tfDate.getValue() == null || tfModele.getText().isEmpty() ||
                tfPuissance.getText().isEmpty() || tfPrix.getText().isEmpty() || tfNumtel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs obligatoires!");
            alert.show();
        } else {
            try {
                // Vérification de l'e-mail
                if (!isValidEmail(tfEmail.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Veuillez saisir une adresse e-mail valide!");
                    alert.show();
                    return; // Arrête le traitement si l'e-mail n'est pas valide
                }

                // Vérification du numéro de téléphone
                if (!isValidPhoneNumber(tfNumtel.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Veuillez saisir un numéro de téléphone valide (8 chiffres)!");
                    alert.show();
                    return; // Arrête le traitement si le numéro de téléphone n'est pas valide
                }

                // Vérification de la date de naissance
                if (!isValidAge(tfDate.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Vous devez avoir au moins 18 ans!");
                    alert.show();
                    return; // Arrête le traitement si l'âge n'est pas valide
                }

                // Si toutes les validations sont réussies, procédez à l'insertion
                String selectedNom = tfNom.getText();
                String selectedPrenom = tfPrenom.getText();
                String selectedAdresse = tfAdresse.getText();
                String selectedEmail = tfEmail.getText();
                LocalDate selectedDatenaiss = tfDate.getValue(); // Assuming tfDate is a DatePicker
                String selectedModele = tfModele.getText();
                String selectedPuissance = tfPuissance.getText();
                double selectedPrix = Double.parseDouble(tfPrix.getText());
                int selectedNumtel = Integer.parseInt(tfNumtel.getText());

                Devis d = new Devis(selectedNom, selectedPrenom, selectedAdresse, selectedEmail, selectedDatenaiss, selectedNumtel, selectedModele, selectedPuissance, selectedPrix);
                ServiceDevis sp = new ServiceDevis();
                sp.insertOne(d);
                afficherDevis();
                Alert alert = new Alert(AlertType.WARNING);

                // Set the title
                alert.setTitle("Ajout");

                // Set the header text
                alert.setHeaderText("Devis Ajouté!");

                // Set the content text
                alert.setContentText("Devis ajouté avec succes");

                // Display the alert
                alert.showAndWait();
                tfNom.setText("");
               tfPrenom.setText("");
                 tfAdresse.setText("");
                tfEmail.setText("");
               tfDate.setValue(null);
               tfModele.setText("");
               tfPuissance.setText("");
               tfPrix.setText("");
               tfNumtel.setText("");


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
            }
        }
    }


    private boolean isValidEmail(String email) {
        // Expression régulière pour valider l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Vérifie si la longueur du numéro de téléphone est de 8 chiffres
        return phoneNumber.matches("\\d{8}");
    }

    private boolean isValidAge(LocalDate birthDate) {
        // Calcul de l'âge à partir de la date de naissance
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.minusYears(18).compareTo(birthDate);
        return age >= 0; // Renvoie vrai si l'âge est de 18 ans ou plus
    }

    @FXML
    void insertOne1(ActionEvent event) {
        if (tfNom1.getText().isEmpty() || tfPrenom1.getText().isEmpty() || tfAdresse1.getText().isEmpty() ||
                tfDate1.getValue() == null || tfModele1.getText().isEmpty() ||
                tfPuissance1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs obligatoires!");
            alert.show();
        } else {
            try {

                String selectedEtat = tfNom1.getText();
                String selectedDecision = tfPrenom1.getText();
                String selectedDocuments = tfAdresse1.getText();
                String selectedEmail1 = (String)tfEmail1.getValue();
                LocalDate selectedDate = tfDate1.getValue(); // Assuming tfDate is a DatePicker
                String selectedDelai = tfModele1.getText();
                String selectedDuree = tfPuissance1.getText();
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
                afficherReponseDevis();
                Alert alert = new Alert(AlertType.WARNING);

                // Set the title
                alert.setTitle("Ajout");

                // Set the header text
                alert.setHeaderText("Reponse Devis Ajouté!");

                // Set the content text
                alert.setContentText(" Reponse Devis ajouté avec succes");

                // Display the alert
                alert.showAndWait();
                tfNom1.setText("");
                tfPrenom1.setText("");
                tfAdresse1.setText("");
                tfEmail1.getSelectionModel().selectFirst();
                tfDate1.setValue(null);
                tfModele1.setText("");
                tfPuissance1.setText("");


              //  Path target = Paths.get("src/main/resources/Documents/"+selectedEmail1+".txt"); // Destination within your project directory
               // Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);


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
            }
        }
    }

    @FXML
    private VBox vboxDevis;
    @FXML
    private VBox vboxDevis1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        afficherDevis();
        afficherReponseDevis();
        ServiceDevis ds = new ServiceDevis();
        List<Devis> dd = null;
        try {
            dd = ds.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> Emails = new ArrayList<>();
        for (Devis d: dd
        ) {
            Emails.add(d.getEmail());

        }
        System.out.println(Emails);
        for (String e: Emails
        ) {
            System.out.println("dddd");
            tfEmail1.getItems().add(e);
        }
        tfEmail1.getSelectionModel().selectFirst();
    }


    public void deleteDevis(int id){

        ServiceDevis st = new ServiceDevis();
        try {
            st.deleteOne(id);
            System.out.println("devis deleted successfully.");
            afficherDevis();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to delete devis: " + e.getMessage());
        }
    }
    public void deleteReponseDevis(int id){

        ServiceReponseDevis st = new ServiceReponseDevis();
        try {
            st.deleteOne(id);
            System.out.println("devis deleted successfully.");
            afficherReponseDevis();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to delete devis: " + e.getMessage());
        }
    }
public void afficherDevis(){
        vboxDevis.getChildren().clear();
    ServiceDevis sd = new ServiceDevis();
    try {
        List<Devis>deviss = sd.selectAll();
        HBox hb[]= new HBox[deviss.size()];
        for (Devis d: deviss
        ) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("itemDevis.fxml"));
                Parent root = loader.load();
                itemDevis otherController = loader.getController();
                HBox hbox = (HBox) loader.getRoot();
                vboxDevis.getChildren().add(hbox);


                otherController.afficher(d);
                hbox.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) { // Double-click detected
                        Devis selectedDevis = d;
                        if (selectedDevis != null) {
                            // Navigate to UpdateUser.fxml
                            navigateToUpdateDevis(selectedDevis);
                            afficherDevis();
                        }
                    }
                });

                Button deleteButton = otherController.getDeleteButton();
                int id = otherController.getId();
                deleteButton.setOnAction(event -> {
                    // Remove the HBox from the VBox when the button is clicked
                    deleteDevis(id);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}

    public void afficherReponseDevis(){
        vboxDevis1.getChildren().clear();
        ServiceReponseDevis sd = new ServiceReponseDevis();
        try {
            List<ReponseDevis>deviss = sd.selectAll();
            HBox hb[]= new HBox[deviss.size()];
            for (ReponseDevis d: deviss
            ) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("itemReponseDevis.fxml"));
                    Parent root = loader.load();
                    itemReponseDevis otherController = loader.getController();
                    HBox hbox = (HBox) loader.getRoot();
                    vboxDevis1.getChildren().add(hbox);


                    otherController.afficher(d);
                    hbox.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) { // Double-click detected
                            ReponseDevis selectedDevis = d;
                            if (selectedDevis != null) {
                                // Navigate to UpdateUser.fxml
                                try {
                                    navigateToUpdateReponseDevis(selectedDevis);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                afficherDevis();
                            }
                        }
                    });

                    Button deleteButton = otherController.getDeleteButton();
                    int id = otherController.getId();
                    deleteButton.setOnAction(event -> {
                        // Remove the HBox from the VBox when the button is clicked
                        deleteReponseDevis(id);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void navigateToUpdateDevis(Devis devis) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDevis.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            updateDevis controller = loader.getController();
            controller.initData(devis);
            Button up = controller.getBt();
            up.setOnMouseClicked(event -> {
                afficherDevis();
            });

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                // Refresh the TableView when the PopUp stage is closed

                Stage gg= (Stage)tfNom.getScene().getWindow();
                gg.close();
            });
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToUpdateReponseDevis(ReponseDevis devis) throws SQLException {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateReponseDevis.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            updateReponseDevis controller = loader.getController();
            controller.initData(devis);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                // Refresh the TableView when the PopUp stage is closed

                Stage gg= (Stage)tfNom.getScene().getWindow();
                gg.close();
            });
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}