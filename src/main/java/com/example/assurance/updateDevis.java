package com.example.assurance;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Devis;
import services.ServiceDevis;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.example.assurance.afficherDevis;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class updateDevis implements Initializable {

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
    private TextField tfEmail;

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
    private TextField tfid;

    @FXML
        void cancelButtonOnAction(ActionEvent event) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
        Parent root = loader.load();
        GestionDevis controller = loader.getController();


        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public Button getBt(){
        return boutonAjouter;
    }
    private Devis selecteddevis;
    public void initData(Devis devis) {
        selecteddevis = devis;
        // Populate the fields in the UI with the data from selectedUser
        tfNom.setText(selecteddevis.getNom());
        tfPrenom.setText(selecteddevis.getPrenom());
        tfAdresse.setText(selecteddevis.getAdresse());
        tfEmail.setText(selecteddevis.getEmail());
        tfDate.setValue(selecteddevis.getDate_naiss()); // Assuming tfDate is a DatePicker
        tfModele.setText(selecteddevis.getModele());
        tfPuissance.setText(String.valueOf(selecteddevis.getPuissance())); // Converting double to String
        tfPrix.setText(String.valueOf(selecteddevis.getPrix())); // Converting double to String
        tfNumtel.setText(String.valueOf(selecteddevis.getNum_tel())); // Converting int to String
        tfid.setText(String.valueOf(selecteddevis.getId())); // Converting int to String
    }
@FXML
    void updateOne1(ActionEvent Event) throws SQLException, IOException {
    String selectedNom = tfNom.getText();
    String selectedPrenom = tfPrenom.getText();
    String selectedAdresse = tfAdresse.getText();
    String selectedEmail = tfEmail.getText();
    LocalDate selectedDatenaiss = tfDate.getValue(); // Assuming tfDate is a DatePicker
    String selectedModele = tfModele.getText();
    String selectedPuissance = tfPuissance.getText();
    double selectedPrix = Double.parseDouble(tfPrix.getText());
    int selectedNumtel = Integer.parseInt(tfNumtel.getText());
    int id = Integer.parseInt(tfid.getText());
    Devis d = new Devis(id,selectedNom, selectedPrenom, selectedAdresse,selectedEmail,  selectedDatenaiss, selectedNumtel,selectedModele, selectedPuissance, selectedPrix);
    ServiceDevis st = new ServiceDevis();

    try {
        st.updateOne(d);
        System.out.println("devis updated successfully.");



    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Failed to update devis: " + e.getMessage());
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


