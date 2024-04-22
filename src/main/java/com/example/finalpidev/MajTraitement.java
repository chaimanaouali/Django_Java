package com.example.finalpidev;

import Services.ServiceTraitement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.constat;
import models.traitement;
import com.example.finalpidev.AffichageTraitement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MajTraitement {

    ServiceTraitement st=new ServiceTraitement();

    @FXML
    private TextField tfrem;

    @FXML
    private TextField tfresp;

    @FXML
    private TextField tfstatut;

    private traitement selectedTraitement;
    public void modifTtrait(ActionEvent actionEvent) {
        traitement t=new traitement(selectedTraitement.getIdT(),LocalDateTime.now(),tfresp.getText(),tfstatut.getText(),tfrem.getText());
        try {
            st.update(t);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La création a réussi!");
            alert.showAndWait();
            tfresp.clear();
            tfrem.clear();
            tfstatut.clear();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            String e1=e.getMessage();
            alert.setContentText(e1);
            alert.showAndWait();

        }

    }

    public void affTrait(ActionEvent actionEvent) {
        // Close the current stage
        Stage currentStage = (Stage) tfresp.getScene().getWindow();
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

    public void initData(traitement selectedTraitement) {
        this.selectedTraitement = selectedTraitement;

        // Populate the fields with data from the selected constat object
        tfresp.setText(selectedTraitement.getResponsable());
        tfstatut.setText(selectedTraitement.getStatut());
        tfrem.setText(selectedTraitement.getRemarque());
        // Populate other fields similarly
        // Make sure to handle radio buttons and SplitMenuButton based on the data in selectedConstat
    }

    public void aff5(ActionEvent actionEvent) {
        Stage currentStage = (Stage) tfresp.getScene().getWindow();
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
}
