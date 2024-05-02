package com.example.finalpidev;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.constat;
import Services.ServiceConstat;

public class AjouterConstat {

    ServiceConstat sc=new ServiceConstat();
    private int i;

    @FXML
    private Label option;


    @FXML
    private TextField desctf;
    @FXML
    private Label photopath;

    @FXML
    private TextField lieutf;

    @FXML
    private RadioButton nonradio;

    @FXML
    private RadioButton ouiradio;

    @FXML
    private SplitMenuButton splitmenutf;



    public void initialize() {
        // Add items to the SplitMenuButton
        MenuItem Rg = new MenuItem("Route glissante");
        MenuItem Mt = new MenuItem("Méteo");
        MenuItem Tr = new MenuItem("Travaux routiers");
        MenuItem  Fv= new MenuItem("Faible Visibilité");

        splitmenutf.getItems().addAll(Rg,Mt,Tr,Fv);

        Rg.setOnAction(event -> handleOption1(event));
        Mt.setOnAction(event1 -> handleOption2(event1));
        Tr.setOnAction(event2 -> handleOption3(event2));
        Fv.setOnAction(event3 -> handleOption4(event3));




    }


    private void handleOption1(ActionEvent event) {
        option.setText("Route glissante");
        splitmenutf.setText("Route glissante");
    }

    private void handleOption2(ActionEvent event1) {
        option.setText("Méteo");
        splitmenutf.setText("Méteo");
    }

    private void handleOption3(ActionEvent event2) {
        option.setText("Travaux Routiers");
        splitmenutf.setText("Travaux Routiers");
    }

    private void handleOption4(ActionEvent event3) {
        option.setText("Faible Visibilité");
        splitmenutf.setText("Faible Visibilité");
    }




/*
    @FXML
    void ajouterC(ActionEvent event) {
        try {
            String lieu = lieutf.getText();
            if(lieu.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("controle de saisie");
                alert.setHeaderText(null);
                alert.setContentText("vous devez ajouter un lieu!");
                alert.showAndWait();
            }

            boolean isOuiSelected = ouiradio.isSelected(); // Check selection
            System.out.println("Oui selected: " + isOuiSelected);
            int rapport = (isOuiSelected) ? 1 : 0;
            System.out.println("Rapport value before object creation: " + rapport);
            constat c=new constat(LocalDateTime.now(),lieutf.getText(),desctf.getText(),photopath.getText(),option.getText(),rapport);
            sc.create(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La création a réussi!");
            alert.showAndWait();
            lieutf.clear();
            desctf.clear();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            String e1=e.getMessage();
            alert.setContentText(e1);
            alert.showAndWait();
        }





    }*/

    @FXML
    void ajouterC(ActionEvent event) {
        try {
            String lieu = lieutf.getText();
            String description = desctf.getText();
            String photoPath = photopath.getText();


            if (lieu.isEmpty() || description.isEmpty() || photoPath.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Contrôle de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs obligatoires!");
                alert.showAndWait();
                return;
            }


            if (description.length() <= 10) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Contrôle de saisie");
                alert.setHeaderText(null);
                alert.setContentText("La description doit contenir plus de 10 caractères!");
                alert.showAndWait();
                return;
            }

            boolean isOuiSelected = ouiradio.isSelected();
            System.out.println("Oui selected: " + isOuiSelected);
            int rapport = (isOuiSelected) ? 1 : 0;
            System.out.println("Rapport value before object creation: " + rapport);
            constat c = new constat(LocalDateTime.now(), lieu, description, photoPath, option.getText(), rapport);
            sc.create(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La création a réussi!");
            alert.showAndWait();
            lieutf.clear();
            desctf.clear();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            String e1 = e.getMessage();
            alert.setContentText(e1);
            alert.showAndWait();
        }
    }






    @FXML
    void photobutt(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media File");
        File selectedFile = fileChooser.showOpenDialog(lieutf.getScene().getWindow());

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            photopath.setText(filePath);
        }
    }

    @FXML
    private void affconstat() {
        // Close the current stage
        Stage currentStage = (Stage) lieutf.getScene().getWindow();
        currentStage.close();

        // Open the new stage with AffichageConstat.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageConstat.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
