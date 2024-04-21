package com.example.user;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import utils.SessionManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static utils.InputValidation.showAlert;
import static utils.SessionManager.clearSession;

public class ListUserController implements Initializable {

    @FXML
    private Button ajouterButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, Integer> idCol;

    @FXML
    private TableColumn<User, String> nomCol;

    @FXML
    private TableColumn<User, String> passwordCol;

    @FXML
    private TableColumn<User, String> prenomCol;

    @FXML
    private TableColumn<User, String> rolesCol;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label loginLabel;


    @FXML
    private Button voitureButton;

    @FXML
    private TextField searchField;


    User currentUser;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.currentUser = SessionManager.getSession().getUser();
            loginLabel.setText(this.currentUser.getNom_user());

            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView() throws SQLException {
        // Retrieve data from the database
        ServiceUser serviceUser = new ServiceUser();
        List<User> userList = serviceUser.selectAll();

        // Clear existing items in the TableView
        userTableView.getItems().clear();

        // Set cell value factories for each column
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom_user()));
        prenomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom_user()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        rolesCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoles()));
        passwordCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));

        // Add the retrieved data to the TableView
        userTableView.getItems().addAll(userList);

        userTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                User selectedUser = userTableView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    // Navigate to UpdateUser.fxml
                    navigateToUpdateUser(selectedUser);
                }
            }
        });
    }

    private void navigateToUpdateUser(User user) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateUser.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller and pass the selected user to it
            UpdateUser controller = loader.getController();
            controller.initData(user);
            controller.setListUserController(this);

            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteOne(ActionEvent event) {
        // Get the selected user from the table view
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Show a confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation Dialog");
            confirmAlert.setHeaderText("Supprimer User");
            confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");

            // Use lambda expression for handling user's choice
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // User confirmed, proceed with deletion
                    try {
                        // Call the deleteOne method with the selected user
                        ServiceUser serviceUser = new ServiceUser();
                        serviceUser.deleteOne(selectedUser);
                        // Show a success message
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Delete Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("User deleted successfully.");
                        successAlert.showAndWait();
                        // Refresh the table view after deletion
                        populateTableView();
                    } catch (SQLException ex) {
                        // Show an error message if deletion fails
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Delete Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Error deleting user: " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            // Show an alert if no user is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
        }
    }


    public void refreshList() {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    private void navigateToAddUser() {
        try {
            // Load the AddUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
            javafx.scene.Parent root = loader.load();


            AddUser controller = loader.getController();
            controller.setListUserController(this);

            // Show the scene containing the AddUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
            populateTableView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterButtonOnAction(ActionEvent event) {

        navigateToAddUser();

    }

    @FXML
    void logoutButtonOnAction(ActionEvent event) {
        // Create a confirmation dialog
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Êtes-vous sûr de vouloir vous déconnecter?");

        // Show the confirmation dialog
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // User confirmed, clear the session and navigate to the login window
                clearSession();
                // Close the current window
                Stage stage = (Stage) logoutButton.getScene().getWindow();
                stage.close();
                // Navigate to the login window
                navigateToLogin();
            }
        });
    }


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
    void voitureButtonOnAction(ActionEvent event){

        Stage stage = (Stage) voitureButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToVoiture();    }
    private void navigateToVoiture() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListVoiture.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            ListVoitureController controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchUser(ActionEvent event) {
        // Get the search criteria entered by the user from the TextField
        String searchTerm = searchField.getText().trim().toLowerCase();

        if (!searchTerm.isEmpty()) {
            // Filter the list of users based on the search criteria
            List<User> filteredUsers = userTableView.getItems().stream()
                    .filter(user -> user.getNom_user().toLowerCase().contains(searchTerm) ||
                            user.getPrenom_user().toLowerCase().contains(searchTerm) ||
                            user.getEmail().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList());

            // Clear the TableView and add the filtered users
            userTableView.getItems().clear();
            userTableView.getItems().addAll(filteredUsers);
        } else {
            // If the search term is empty, repopulate the TableView with all users
            try {
                populateTableView(); // Call method to repopulate TableView
            } catch (SQLException e) {
                e.printStackTrace();
                InputValidation.showAlert("Error", "Failed to populate table view: " + e.getMessage());
            }
        }
    }

    public class InputValidation {
        public static void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null); // Remove the header text
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() {
        // Add a listener to the searchField to detect changes in text
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Get the search term entered by the user
            String searchTerm = newValue.trim().toLowerCase();

            if (searchTerm.isEmpty()) {
                // If the search term is empty, repopulate the TableView with all users
                try {
                    populateTableView(); // Call method to repopulate TableView
                } catch (SQLException e) {
                    e.printStackTrace();
                    InputValidation.showAlert("Error", "Failed to populate table view: " + e.getMessage());
                }
            } else {
                // Filter the list of users based on the search criteria
                List<User> filteredUsers = userTableView.getItems().stream()
                        .filter(user -> user.getNom_user().toLowerCase().contains(searchTerm) ||
                                user.getPrenom_user().toLowerCase().contains(searchTerm) ||
                                user.getEmail().toLowerCase().contains(searchTerm))
                        .collect(Collectors.toList());

                // Clear the TableView and add the filtered users
                userTableView.getItems().clear();
                userTableView.getItems().addAll(filteredUsers);
            }
        });
    }


    }




