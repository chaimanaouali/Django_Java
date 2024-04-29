package com.example.djangoassurancejava;

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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Contrat;
import models.Type;
import services.ServiceContrat;
import services.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateContrat implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField adresseTextField;
    @FXML
    private DatePicker datedebutDatePicker;

    @FXML
    private DatePicker datefinDatePicker;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField numeroTextField;

    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField idupdate;

    @FXML
    private ComboBox<String> typedecouvertureChoiceBox;
    @FXML
    private Contrat selectedcontrat;

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void initData(Contrat contrat) throws SQLException {
        ServiceType sc = new ServiceType();
        List<Type> t = sc.recuperer();
        List<String> desc = new ArrayList<>();
        for (Type type: t
        ) {
            desc.add(type.getDescription());
        }
        for (String e:desc
        ) {
            typedecouvertureChoiceBox.getItems().add(e);
        }
        typedecouvertureChoiceBox.getSelectionModel().selectFirst();


        selectedcontrat = contrat;
        // Populate the fields in the UI with the data from selectedUser
        nomTextField.setText(selectedcontrat.getNom());
        prenomTextField.setText(selectedcontrat.getPrenom());
        emailTextField.setText(selectedcontrat.getEmail());
        numeroTextField.setText(selectedcontrat.getNumero_assur());
        idupdate.setText(String.valueOf(selectedcontrat.getId()));
        adresseTextField.setText(selectedcontrat.getAdresse_assur());

        datedebutDatePicker.setValue(selectedcontrat.getDate_debut_contrat());
        datefinDatePicker.setValue(selectedcontrat.getDatefin_contrat());


    }
    @FXML
    void retourList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ListType.fxml"));
        Stage stageu = new Stage();
        stageu.initStyle(StageStyle.UNDECORATED);
        stageu.setScene(new Scene(root, 1020,800));
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
        stageu.show();
    }
    @FXML
    void modifier(ActionEvent event) throws SQLException {
        int selectedid = Integer.valueOf(idupdate.getText());
        String selectednom = (String) nomTextField.getText();
        String selectedprenom = (String) prenomTextField.getText();
        String selectedemail = (String) emailTextField.getText();
        String selectednumero = (String) numeroTextField.getText();
        String selectedadresse = (String) adresseTextField.getText();
        String selectedtypec = (String) typedecouvertureChoiceBox.getValue();
        ServiceContrat scc = new ServiceContrat();
        int selectedtypecid =  scc.rechercheId(selectedtypec);
        LocalDate selecteddated = datedebutDatePicker.getValue();
        LocalDate selecteddatef =  datefinDatePicker.getValue();


// Create a new Transport object with retrieved values
        Contrat contrat = new Contrat(selectedid,selectedtypecid, selecteddated,selecteddatef,selectedadresse,selectednumero,selectednom,selectedprenom,selectedemail);
        System.out.println(contrat);
        ServiceContrat sc = new ServiceContrat();

        try {
            sc.modifier(contrat);
            System.out.println("contrat updated successfully.");


        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to update contrat: " + e.getMessage());
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Image brandingImage = new Image(getClass().getResource("/images/thumbnail_image.png").toString());
           // brandingImageView.setImage(brandingImage);
        } catch (Exception e) {
            e.printStackTrace();
        }}

}
