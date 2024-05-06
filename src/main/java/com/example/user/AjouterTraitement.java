package com.example.user;

import services.ServiceTraitement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.traitement;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AjouterTraitement {

    ServiceTraitement st=new ServiceTraitement();

    @FXML
    private TextField tfrem;

    @FXML
    private TextField tfresp;

    @FXML
    private Label statutt;

    @FXML
    private SplitMenuButton splitmenutf;


    @FXML
    void affTrait(ActionEvent event) {
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
    /*
    @FXML
    void ajouTtrait(ActionEvent event) {
        traitement t=new traitement(LocalDateTime.now(),tfresp.getText(),tfstatut.getText(),tfrem.getText());
        try {
            st.create(t);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
*/
    public void initialize() {
        // Add items to the SplitMenuButton
        MenuItem Rg = new MenuItem("en attente");
        MenuItem Mt = new MenuItem("en cours");
        MenuItem Tr = new MenuItem("terminée");
        MenuItem  Fv= new MenuItem("refuseé");

        splitmenutf.getItems().addAll(Rg,Mt,Tr,Fv);

        Rg.setOnAction(event -> handleOption1(event));
        Mt.setOnAction(event1 -> handleOption2(event1));
        Tr.setOnAction(event2 -> handleOption3(event2));
        Fv.setOnAction(event3 -> handleOption4(event3));


    }

    private void handleOption1(ActionEvent event) {
        statutt.setText("en attente");
        splitmenutf.setText("en attente");
    }

    private void handleOption2(ActionEvent event1) {
        statutt.setText("en cours");
        splitmenutf.setText("en cours");
    }

    private void handleOption3(ActionEvent event2) {
        statutt.setText("terminée");
        splitmenutf.setText("terminée");
    }

    private void handleOption4(ActionEvent event3) {
      statutt.setText("refuseé");
        splitmenutf.setText("refuseé");
    }


    @FXML
    void ajouTtrait(ActionEvent event) {
        try {
            String responsable = tfresp.getText();
            String statut = statutt.getText();
            String remarque = tfrem.getText();


            if (responsable.isEmpty() || statut.isEmpty() || remarque.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Contrôle de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs obligatoires!");
                alert.showAndWait();
                return;
            }


            if (!responsable.matches("[a-zA-Z]+")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Contrôle de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le champ responsable ne doit contenir que des lettres!");
                alert.showAndWait();
                return;
            }


            if (statut.length() <= 5) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Contrôle de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le champ statut doit contenir plus de 5 caractères!");
                alert.showAndWait();
                return;
            }


            if (remarque.length() <= 10) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Contrôle de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Le champ remarque doit contenir plus de 10 caractères!");
                alert.showAndWait();
                return; }

            traitement t = new traitement(LocalDateTime.now(), responsable, statut, remarque);
            st.create(t);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La création a réussi!");
            alert.showAndWait();
            tfresp.clear();

            tfrem.clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void aff6(ActionEvent actionEvent) {
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
