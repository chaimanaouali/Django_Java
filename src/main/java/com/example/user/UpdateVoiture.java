package com.example.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.User;
import models.Voiture;
import services.ServiceUser;
import services.ServiceVoiture;
import utils.InputValidation;
import utils.SessionManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateVoiture implements Initializable {

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

    private Voiture selectedVoiture;
    private ListVoitureController listVoitureController;



    public void setListVoitureController(ListVoitureController listVoitureController) {
        this.listVoitureController = listVoitureController;
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
        }
    }

    public void initData(Voiture voiture) throws SQLException {
        ServiceUser serviceUser = new ServiceUser();
        List<User> userList = serviceUser.selectAll();

        selectedVoiture = voiture;
        // Populate the fields in the UI with the data from selectedVoiture
        MatriculeTextField.setText(selectedVoiture.getMatricule());
        marqueTextField.setText(selectedVoiture.getMarque());
        puissanceTextField.setText(String.valueOf(selectedVoiture.getPuissance()));
        prixTextField.setText(String.valueOf(selectedVoiture.getPrix_voiture()));
        // Clear the existing items in the ComboBox
        emailVoitureTextField.getItems().clear();


        User currentUser = SessionManager.getSession().getUser();



        // Add the associated user to the ComboBox
        //emailVoitureTextField.getItems().add(selectedVoiture.getUser());
        if ("[\"ROLE_ADMIN\"]".equals(currentUser.getRoles())){
            emailVoitureTextField.getItems().addAll(userList);}
        else {
            emailVoitureTextField.getItems().add(currentUser);
        }


        // Custmize how the User object is displayed in the ComboBox
        emailVoitureTextField.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        if (user == null || empty) {
                            setText(null);
                        } else {
                            setText(user.getEmail()); // Displaying only the email
                        }
                    }
                };
            }
        });

        // Set the string converter to display only the email
        emailVoitureTextField.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user != null ? user.getEmail() : null;
            }

            @Override
            public User fromString(String string) {
                // Implement if needed, not used in this case
                return null;
            }
        });


        // Select the user associated with the selected voiture
        emailVoitureTextField.getSelectionModel().select(selectedVoiture.getUser());
    }

    @FXML
    void updateOne(ActionEvent event) throws SQLException {
        String selectedMatricule = MatriculeTextField.getText();
        String selectedMarque = marqueTextField.getText();
        String selectedPuissanceText = puissanceTextField.getText();
        String selectedPrixText = prixTextField.getText();
        String selectedEmail = emailVoitureTextField.getValue().getEmail();

        if (selectedMatricule.isEmpty() || selectedMarque.isEmpty() || selectedPuissanceText.isEmpty() || selectedPrixText.isEmpty() || selectedEmail.isEmpty()) {
            InputValidation.showAlert("Input Error", null, "Please fill in all fields.");
            return;
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
            Voiture updatedVoiture = new Voiture(selectedVoiture.getId(), selectedPuissance, selectedMatricule, selectedMarque, selectedPrix, user);

            // Update the voiture
            ServiceVoiture serviceVoiture = new ServiceVoiture();
            serviceVoiture.updateOne(updatedVoiture);
            System.out.println("Voiture updated successfully.");
            if (listVoitureController != null) {
                listVoitureController.refreshList();
            }

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            InputValidation.showAlert("Input Error", null, "Please enter valid numeric values for puissance and prix.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to update voiture: " + e.getMessage());
        }
    }
}
