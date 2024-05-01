package com.example.javadjango;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import Services.ServiceMecanicien;
import Models.mecanicien;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.SQLException;

import javafx.event.ActionEvent;

import javax.persistence.Id;

public class AjouterM {

    @FXML
    private TextField DispoM;

    @FXML
    private TextField ImageM;

    @FXML
    private TextField NomM;

    @FXML
    private TextField NumM;

    @FXML
    private TextField PrenomM;

    @FXML
    private TextField emailM;

    @FXML
    private TextField localM;
    @FXML
    private ImageView ImageV;
    String filepath = null, filename = null, fn = null;
    FileChooser fc = new FileChooser();
  private   String uploads = "C:/Users/Nour/IdeaProjects/DjangoAssurance-crud back/src/main/resources/images/";
    private final ServiceMecanicien mec = new ServiceMecanicien();
    private String image;
    @FXML
    void btnAjouterM(ActionEvent event) {

        if (NomM.getText().isEmpty() || PrenomM.getText().isEmpty() || localM.getText().isEmpty() || DispoM.getText().isEmpty() || filepath.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {

            String adresse = emailM.getText();
            if (!adresse.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer une adresse e-mail valide (sous forme '***@***.**').");
                alert.showAndWait();
                return; // Quittez la méthode si l'adresse e-mail n'est pas valide
            }


        String numString = NumM.getText();
        if (!numString.matches("[0-9]+")|| numString.length() > 8) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un numéro uniquement de chiffres depasse pas 8 chiffres.");
            alert.showAndWait();
            return;
        }

        try {
            // Ajoutez le mec seulement si aucun champ n'est vide et si l'adresse e-mail est valide
            mec.ajouter(new mecanicien(
                    NomM.getText(),
                    PrenomM.getText(),
                    localM.getText(),
                    DispoM.getText(),
                    NumM.getText(),
                    filepath,
                    emailM.getText()
            ));
            showAlertSuccess();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }}
    private void showAlertSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("L'ajout a été effectué avec succès!");
        alert.showAndWait();
    }

    @FXML
    void btnImporter(ActionEvent event) throws SQLException, FileNotFoundException, IOException  {
      File file = fc.showOpenDialog(null);
        // Shows a new file open dialog.
        if (file != null) {
            // URI that represents this abstract pathname
            ImageV.setImage(new Image(file.toURI().toString()));

            filename = file.getName();
            filepath = file.getAbsolutePath();

            fn = filepath;

        //    FileChannel source = new FileInputStream(filepath).getChannel();
         // FileChannel dest = new FileOutputStream(uploads + filepath).getChannel();
         // dest.transferFrom(source, 0, source.size());
        } else {
            System.out.println("Fichier invalide!");
        }

    }



    @FXML
    void btnaffichageajoutM(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherM.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la référence à la scène actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement du FXML
        }
    }

}
