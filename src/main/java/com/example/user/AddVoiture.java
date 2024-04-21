package com.example.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.User;
import models.Voiture;
import services.ServiceUser;
import services.ServiceVoiture;
import utils.Hash;
import utils.InputValidation;
import utils.SessionManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class AddVoiture implements Initializable {

    @FXML
    private TextField MatriculeTextField;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmerButton;

    @FXML
    private ComboBox<User> emailVoitureTextField;

    @FXML
    private TextField marqueTextField;

    @FXML
    private TextField prixTextField;

    @FXML
    private TextField puissanceTextField;

    private ListVoitureController ListVoitureController;

    public void setListUserController(ListUserController listUserController) {
        this.ListVoitureController = ListVoitureController;
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void insertOne(ActionEvent event) throws SQLException {
        ServiceUser serviceUser = new ServiceUser();
        List<User> userList = serviceUser.selectAll();

        String selectedMatricule = MatriculeTextField.getText();
        String selectedMarque = marqueTextField.getText();
        String selectedPuissanceText = puissanceTextField.getText();
        String selectedPrixText = prixTextField.getText();
        String selectedEmail = null;
        User selectedUser = emailVoitureTextField.getValue();
        if (selectedUser != null) {
            selectedEmail = selectedUser.getEmail();
        } else {
            InputValidation.showAlert("Input Error", null, "Please select a user from the list.");
            return;
        }


        if (selectedPuissanceText.isEmpty() || selectedMatricule.isEmpty() || selectedMarque.isEmpty() || selectedPrixText.isEmpty() || selectedEmail.isEmpty()) {
            InputValidation.showAlert("Input Error", null, "Please fill in all fields.");
            return;
        }
        emailVoitureTextField.getItems().clear();


        User currentUser = SessionManager.getSession().getUser();


        // Add the associated user to the ComboBox
        //emailVoitureTextField.getItems().add(selectedVoiture.getUser());
        if ("[\"ROLE_ADMIN\"]".equals(currentUser.getRoles())){
            emailVoitureTextField.getItems().addAll(userList);}
        else {emailVoitureTextField.getItems().add(currentUser);
        }
        try {
            int selectedPuissance = Integer.parseInt(selectedPuissanceText);
            float selectedPrix = Float.parseFloat(selectedPrixText);

            // Fetch user associated with the provided email
            ServiceUser userService = new ServiceUser();
            User user = userService.getByEmail(selectedEmail);
            if (user == null) {
                InputValidation.showAlert("Input Error", null, "No user found with the provided email.");
                return;
            }

            // Create a new Voiture object with retrieved values
            Voiture voiture = new Voiture(selectedPuissance, selectedMatricule, selectedMarque, selectedPrix, user);

            ServiceVoiture st = new ServiceVoiture();
            st.insertOne(voiture);
            System.out.println("Voiture added successfully.");

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            InputValidation.showAlert   ("Input Error", null, "Please enter valid numeric values for puissance and prix.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to add voiture: " + e.getMessage());
        }
    }

    /*@Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Populate the ComboBox with User objects
            ServiceUser userService = new ServiceUser();
            List<User> users = userService.selectAll();
            emailVoitureTextField.getItems().addAll(users);

            // Display only email in the ComboBox
            emailVoitureTextField.setCellFactory(param -> new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);

                    if (empty || user == null) {
                        setText(null);
                    } else {
                        setText(user.getEmail());
                    }
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            Image brandingImage = new Image(getClass().getResource("/images/logo-django.png").toString());
            brandingImageView.setImage(brandingImage);
        } catch (Exception e) {
            e.printStackTrace();
        }}*/


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Populate the ComboBox with User objects

            emailVoitureTextField.getItems().clear();


            User currentUser = SessionManager.getSession().getUser();


            ServiceUser userService = new ServiceUser();
            // Add the associated user to the ComboBox
            //emailVoitureTextField.getItems().add(selectedVoiture.getUser());
            if ("[\"ROLE_ADMIN\"]".equals(currentUser.getRoles())){

                List<User> users = userService.selectAll();

                emailVoitureTextField.getItems().addAll(users);
            }
            else {
                emailVoitureTextField.getItems().add(currentUser);
            }



            // Display only email in the ComboBox
            emailVoitureTextField.setCellFactory(param -> new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);

                    if (empty || user == null) {
                        setText(null);
                    } else {
                        setText(user.getEmail());
                    }
                }
            });

            // Ensure that the email is displayed in the ComboBox when a user is selected
            emailVoitureTextField.setButtonCell(new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);

                    if (empty || user == null) {
                        setText(null);
                    } else {
                        setText(user.getEmail());
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Image brandingImage = new Image(getClass().getResource("/images/logo-django.png").toString());
            brandingImageView.setImage(brandingImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
