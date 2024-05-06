package com.example.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import com.example.user.App;
import com.example.user.MyListener;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Post;
import models.User;
import models.Voiture;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import services.ServiceVoiture;
import utils.DBconnection;
import utils.SessionManager;

import static utils.SessionManager.clearSession;


public class FrontController implements Initializable {
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private Label marqueLable;

    @FXML
    private Label matriculeLabel;

    @FXML
    private Label priceLable;

    @FXML
    private Label puissanceLable;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Label loginLabel;
    @FXML
    private Button logoutButton;

    private Voiture selectedVoiture;
    private List<Voiture> voitureList = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    User currentUser;
    @FXML
    private Button homeBt;
    private List<Voiture> getData() throws SQLException {
        ServiceVoiture serviceVoiture = new ServiceVoiture();
        List<Voiture> voitureList = serviceVoiture.selectVoitureByUser(SessionManager.getSession().getUser());
        //List<Voiture> voitureList = serviceVoiture.selectAll();
        return voitureList;
    }

   /* private void setChosenFruit(Voiture voiture) {
        matriculeLabel.setText(voiture.getMatricule());
        marqueLable.setText(voiture.getMarque());
        priceLable.setText(String.valueOf(voiture.getPrix_voiture()));
        puissanceLable.setText(String.valueOf(voiture.getPuissance()));
        Image image = new Image(getClass().getResource("/images/car.png").toString());
        img.setImage(image);

    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.currentUser = SessionManager.getSession().getUser();
            loginLabel.setText(this.currentUser.getNom_user());
            voitureList = getData(); // Populate the existing voitureList
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        myListener = new MyListener() {
            @Override
            public void onClickListener(Voiture voiture) {
                //setChosenFruit(voiture);
                selectedVoiture = voiture;
                System.out.println(selectedVoiture);
            }

            @Override
            public void onClickListener(Post post) {

            }
        };

        if (voitureList.size() > 0) {
          //  setChosenFruit(voitureList.get(0));
            System.out.println(voitureList.size());
        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < voitureList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(voitureList.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToAddVoiture() {
        try {
            // Load the AddVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddVoiture.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the scene containing the AddVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            // Set a listener to execute code after the AddVoiture stage is closed
            stage.setOnHiding(event -> {
                // Call the refreshList method to update the grid with the latest data
                refreshList();
            });
            stage.showAndWait();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterButtonOnAction(ActionEvent event) {
        navigateToAddVoiture();
        refreshList();

    }

    public void refreshList() {
        try {
            // Clear the existing list of Voiture objects
            voitureList.clear();

            // Fetch the latest data from the database
            voitureList = getData();

            // Clear the existing items from the grid
            grid.getChildren().clear();

            // Reset row and column indices
            int column = 0;
            int row = 1;

            // Iterate through the updated voitureList
            for (int i = 0; i < voitureList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(voitureList.get(i), myListener);

                // Check if the column exceeds the limit
                if (column == 3) {
                    column = 0;
                    row++;
                }

                // Add the anchorPane to the grid at the specified column and row
                grid.add(anchorPane, column++, row);

                // Set margins for the anchorPane
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonClicked(ActionEvent event) {
        try {
            if (selectedVoiture == null) {
                // Alert the user to select a vehicle first
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Vehicle Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a vehicle to update.");
                alert.showAndWait();
                return; // Exit the method if no vehicle is selected
            }

            // Load the UpdateVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateVoiture.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            UpdateVoiture updateVoitureController = loader.getController();

            // Pass the selected vehicle to the controller
            updateVoitureController.initData(selectedVoiture);

            // Create a new stage for the UpdateVoiture scene
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Voiture");
            stage.setScene(new Scene(root));

            // Show the UpdateVoiture stage
            stage.showAndWait();

            // Optionally, refresh the list after updating
            refreshList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteButtonClicked(ActionEvent event) {
        // Check if a voiture is selected
        if (selectedVoiture != null) {
            // Create a confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to delete the selected vehicle?");

            // Show the confirmation dialog and wait for user response
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            // If the user confirms deletion, proceed with deletion
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Call the delete method from your ServiceVoiture class to delete the selected voiture
                    ServiceVoiture serviceVoiture = new ServiceVoiture();
                    serviceVoiture.deleteOne(selectedVoiture);

                    // Optionally, refresh the list after deletion
                    refreshList();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle any potential exceptions here
                }
            }
        } else {
            // Alert the user to select a vehicle first
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Vehicle Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a vehicle to delete.");
            alert.showAndWait();
        }
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
    void homeButtonOnAction(ActionEvent event){

        Stage stage = (Stage) homeBt.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToHome();    }
    private void navigateToHome() {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home2.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller and pass the selected user to it
            Home2 controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}