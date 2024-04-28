package com.example.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import utils.Hash;
import utils.InputValidation;
import utils.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Button confirmerButton;

    @FXML
    private TextField emailUserTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nomUserTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField prenomUserTextField;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Hyperlink loginLink;


    @FXML
    void insertOne(ActionEvent event) throws SQLException {
        String selectedNomUser = (String) nomUserTextField.getText();
        String selectedPrenomUser = (String) prenomUserTextField.getText();
        String selectedEmailUser = (String) emailUserTextField.getText();
        String passwordUser = passwordTextField.getText();
        String selectedRoles = "[]";

        String hashedpwd = Hash.generateHash(passwordUser);
// Create a new Transport object with retrieved values
        User user = new User( selectedNomUser, selectedPrenomUser, hashedpwd, selectedEmailUser, selectedRoles);

        ServiceUser st = new ServiceUser();

        try {if (InputValidation.isTextFieldEmpty(selectedNomUser)) {
            InputValidation.showAlert("Input Error", null, "Please enter a valid Name");
        } else if (InputValidation.isTextFieldEmpty(selectedPrenomUser)) {
            InputValidation.showAlert("Input Error", null, "Please enter a valid Prenom");
        } else if (!InputValidation.isValidEmail(selectedEmailUser)) {
            InputValidation.showAlert("Input Error", null, "Please enter a valid email address.");
        } else if (!InputValidation.isValidPassword(passwordUser)) {
            InputValidation.showAlert("Input Error", null, "Please enter a valid password (at least 8 characters with at least one digit and one letter).");
        } else {
            st.insertOne(user);
            System.out.println("User added successfully.");

            redirectToFrontPage(user);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to add user: " + e.getMessage());
        }
    }

    @FXML

    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image brandingImage = new Image(getClass().getResource("/images/logo-django.png").toString());
            brandingImageView.setImage(brandingImage);
        } catch (Exception e) {
            e.printStackTrace();
        }}

    private void navigateToLogin() {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller and pass the selected user to it
            LoginController controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void loginLinkOnAction(ActionEvent event){

        Stage stage = (Stage) loginLink.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToLogin();    }


    private void redirectToFrontPage(User user) {
        try {

            SessionManager.startSession(user);
            Parent root = FXMLLoader.load(getClass().getResource("Front.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Admin - User List");
            stage.setScene(new Scene(root));
            stage.show();
            // Close the login stage
            ((Stage) emailUserTextField.getScene().getWindow()).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
