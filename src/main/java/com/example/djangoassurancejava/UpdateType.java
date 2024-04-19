package com.example.djangoassurancejava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Type;
import services.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateType implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField typedecouvertureTextField;
    @FXML
    private Type selectedtype;

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void initData(Type type) {
        selectedtype = type;
        // Populate the fields in the UI with the data from selectedUser
        typedecouvertureTextField.setText(selectedtype.getType_couverture());
        descriptionTextField.setText(selectedtype.getDescription());
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
        String selectedtypecouverture = (String) typedecouvertureTextField.getText();
        String selecteddescription = (String) descriptionTextField.getText();


// Create a new Transport object with retrieved values
        Type type1 = new Type(selectedtype.getId(), selecteddescription, selectedtypecouverture);

        ServiceType st = new ServiceType();

        try {
            st.modifier(type1);
            System.out.println("type updated successfully.");


        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to update type: " + e.getMessage());
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
