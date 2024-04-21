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
        private Hyperlink  signupLink;

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

            boolean isLogged = Hash.verifyHash(password,user.getPassword());
            if (user != null && user.getPassword() != null && isLogged) {
                // Successful login
                SessionManager.startSession(user);


                    showAlert("Login Successful", "Welcome, " + user.getEmail() + "!");

                    if ("[\"ROLE_ADMIN\"]".equals(user.getRoles())) {
                        // Redirect to Admin page (ListUser)
                        redirectToAdminPage();
                    } else {
                        try {
                            // Load the FrontUser.fxml file
                            Parent root = FXMLLoader.load(getClass().getResource("Front.fxml"));

                            // Create a new stage and set the scene
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
                    // Set the current user in the session
                    // SessionManager.setCurrentUser(user);


            } else {
                // Display an error message or handle unsuccessful login
                showAlert("Login Failed", "Invalid email or mot de passe.");
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

        private void navigateToSignUp() {
            try {
                // Load the UpdateUser.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
                javafx.scene.Parent root = loader.load();

                // Access the controller and pass the selected user to it
                RegisterController controller = loader.getController();


                // Show the scene containing the UpdateUser.fxml file
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        @FXML
        void signupLinkOnAction(ActionEvent event){

            Stage stage = (Stage) signupLink.getScene().getWindow();
            stage.close();
            // Navigate to the login window
            navigateToSignUp();    }




    }
