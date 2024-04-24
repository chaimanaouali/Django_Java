package com.example.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import utils.Hash;
import utils.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField emailUserTextField;

    @FXML
    private PasswordField entrerPasswordField;

    @FXML
    private Label loginMessagelabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private ImageView lockImageView;

    @FXML
    private Hyperlink signupLink;

    ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image brandingImage = new Image(getClass().getResource("/images/logo-django.png").toString());
            brandingImageView.setImage(brandingImage);

            Image lockImage = new Image(getClass().getResource("/images/lock-removebg-preview.png").toString());
            lockImageView.setImage(lockImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void LOGINUPTF(ActionEvent actionEvent) {
        String email = emailUserTextField.getText();
        String password = entrerPasswordField.getText();

        User user = serviceUser.getByEmail(email);

        if (user != null) {
            boolean isLogged = Hash.verifyHash(password, user.getPassword());
            if (isLogged) {
                // Successful login
                SessionManager.startSession(user);
                showAlert("Login Successful", "Welcome, " + user.getEmail() + "!");
                if ("[\"ROLE_ADMIN\"]".equals(user.getRoles())) {
                    // Redirect to Admin page (ListUser)
                    redirectToAdminPage();
                } else {
                    redirectToFrontPage();
                }
            } else {
                // Password verification failed
                showAlert("Login Failed", "Invalid email or password.");
            }
        } else {
            // User not found
            showAlert("Login Failed", "No user found with the provided email.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void redirectToAdminPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ListUser.fxml"));
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

    private void redirectToFrontPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Front.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Front User");
            stage.setScene(new Scene(root));
            stage.show();
            // Close the login stage
            ((Stage) emailUserTextField.getScene().getWindow()).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void navigateToSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();
            RegisterController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signupLinkOnAction(ActionEvent event) {
        Stage stage = (Stage) signupLink.getScene().getWindow();
        stage.close();
        navigateToSignUp();
    }

    public void ForgetPassword_btn(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgetPasswordForm.fxml"));
            Parent root = loader.load();

            emailUserTextField.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
