package com.example.assurance;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Devis;
import models.ReponseDevis;
import services.ServiceDevis;
import services.ServiceReponseDevis;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class updateReponseDevis implements Initializable {

    @FXML
    private Button boutonAjouter;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField tfAdresse;

    @FXML
    private DatePicker tfDate;

    @FXML
    private TextField tfModele;
    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrenom;

    @FXML
    private TextField tfPuissance;

    @FXML
    private TextField tfid;

    @FXML
        void cancelButtonOnAction(ActionEvent event) {
        int id = Integer.parseInt(tfid.getText());
        ServiceReponseDevis st = new ServiceReponseDevis();
        try {
            st.deleteOne(id);
            System.out.println("devis deleted successfully.");
            Parent root = FXMLLoader.load(getClass().getResource("afficherReponseDevis.fxml"));
            Stage pStage= new Stage();
            pStage.initStyle(StageStyle.UNDECORATED);
            pStage.setScene(new Scene(root, 667,556));
            pStage.show();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to delete devis: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }
    private ReponseDevis selecteddevis;
    public void initData(ReponseDevis devis) throws SQLException {
        selecteddevis = devis;
        // Populate the fields in the UI with the data from selectedUser
        tfNom.setText(selecteddevis.getEtat());
        tfPrenom.setText(selecteddevis.getDecision());
        tfAdresse.setText(selecteddevis.getDocuments());

        tfEmail.setText(String.valueOf(selecteddevis.getEmail_id()));

        tfDate.setValue(selecteddevis.getDate_reglement()); // Assuming tfDate is a DatePicker
        tfModele.setText(selecteddevis.getDelai_reparation());
        tfPuissance.setText(String.valueOf(selecteddevis.getDuree_validite())); // Converting double to String
        tfid.setText(String.valueOf(selecteddevis.getId())); // Converting int to String
    }
@FXML
    void updateOne1(ActionEvent Event)throws SQLException{
    String selectedEtat = tfNom.getText();
    String selectedDecision = tfPrenom.getText();
    String selectedDocuments = tfAdresse.getText();

    int selectedEmail =Integer.parseInt( tfEmail.getText());


    LocalDate selectedDate = tfDate.getValue(); // Assuming tfDate is a DatePicker
    String selectedDelaiR = tfModele.getText();
    String selectedDuree = tfPuissance.getText();

    int id = Integer.parseInt(tfid.getText());
    ReponseDevis d = new ReponseDevis(id,selectedEmail, selectedEtat, selectedDecision, selectedDelaiR,selectedDuree, selectedDocuments, selectedDate);
    ServiceReponseDevis st = new ServiceReponseDevis();

    try {
        st.updateOne(d);
        System.out.println("Reponse devis updated successfully.");
        Parent root = FXMLLoader.load(getClass().getResource("afficherReponseDevis.fxml"));
        Stage pStage= new Stage();
        pStage.initStyle(StageStyle.UNDECORATED);
        pStage.setScene(new Scene(root, 667,556));
        pStage.show();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Failed to update Reponse devis: " + e.getMessage());
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image brandingImage = new Image(getClass().getResource("/images/django-removebg-preview.png").toString());
            brandingImageView.setImage(brandingImage);
        } catch (Exception e) {
            e.printStackTrace();
        }}


    }


