package com.example.user;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.User;
import models.Voiture;
import services.ServiceVoiture;
import utils.SessionManager;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static utils.SessionManager.clearSession;

public class ListVoitureController implements Initializable {

    @FXML
    private Button ajouterButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Voiture, String> emailCol;

    @FXML
    private TableColumn<Voiture, String> marqueCol;

    @FXML
    private TableColumn<Voiture, String> matriculeCol;

    @FXML
    private TableColumn<User, String> nomCol;

    @FXML
    private TableColumn<User, String> prenomCol;

    @FXML
    private TableColumn<Voiture, Float> prixCol;

    @FXML
    private TableColumn<Voiture, Integer> puissanceCol;

    @FXML
    private Button supprimerButton;

    @FXML
    private AnchorPane tuteur_AP;

    @FXML
    private TableView<Voiture> voitureTableView;

    @FXML
    private Label loginLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button userButton;

    @FXML
    private Pagination pagination;
    private static final int ROWS_PER_PAGE = 10; // Number of rows per page
    private ObservableList<Voiture> currentPageUsers;
    private List<Voiture> allVoitures;
    private ServiceVoiture serviceVoiture = new ServiceVoiture();
    @FXML
    private TextField searchField; // Added TextField for searching
    @FXML
    private Button evaluationButton;
    @FXML
    private Button mecanicienButton;
    @FXML
    private Button contratButton;
    @FXML
    private Button devisButton;
    @FXML
    private Button commentaireButton;
    @FXML
    private Button PostButton;


    User currentUser;

    @FXML
    void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            this.currentUser = SessionManager.getSession().getUser();
            loginLabel.setText(this.currentUser.getNom_user());

            pagination.setPageFactory(this::createPage);

            allVoitures = serviceVoiture.selectAll();
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Listener for the search field to trigger filtering
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterVoitures(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void populateTableView() throws SQLException {
        // Retrieve data from the database
        ServiceVoiture serviceVoiture = new ServiceVoiture();
        List<Voiture> voitureList = serviceVoiture.selectAll();

        // Clear existing items in the TableView
        voitureTableView.getItems().clear();

        int pageCount = (int) Math.ceil((double) allVoitures.size() / ROWS_PER_PAGE);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0);
        createPage(0);

        // Set cell value factories for each column
        matriculeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricule()));
        marqueCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
        puissanceCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPuissance()).asObject());
        prixCol.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrix_voiture()).asObject());
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getEmail()));

        // Add the retrieved data to the TableView
        voitureTableView.getItems().addAll(voitureList);

        voitureTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                Voiture selectedVoiture = voitureTableView.getSelectionModel().getSelectedItem();
                if (selectedVoiture != null) {
                    // Navigate to UpdateUser.fxml
                    navigateToUpdateVoiture(selectedVoiture );
                }
            }
        });
    }

    @FXML
    private void deleteOne(ActionEvent event) {
        // Get the selected voiture from the table view
        Voiture selectedVoiture = voitureTableView.getSelectionModel().getSelectedItem();
        if (selectedVoiture != null) {
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
                        ServiceVoiture serviceVoiture = new ServiceVoiture();
                        serviceVoiture.deleteOne(selectedVoiture);
                        // Show a success message
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Delete Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Voiture deleted successfully.");
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
            alert.setTitle("Non Selection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une voiture à supprimer.");
            alert.showAndWait();
        }
    }
    private void navigateToUpdateVoiture(Voiture voiture) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateVoiture.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller and pass the selected user to it
            UpdateVoiture controller = loader.getController();
            controller.initData(voiture);
            controller.setListVoitureController(this);

            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
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
            stage.showAndWait();

            // Refresh the table view after adding a new voiture
            populateTableView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterButtonOnAction(ActionEvent event) {
        navigateToAddVoiture();
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
    void userButtonOnAction(ActionEvent event){

        Stage stage = (Stage) userButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToUser();    }
    private void navigateToUser() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListUser.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            ListUserController controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
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
    private void showCategoryStatistics(ActionEvent event) {
        // Get the list of all posts from the table
        List<Voiture> allPosts = voitureTableView.getItems();

        // Count occurrences of each category
        Map<String, Long> categoryCounts = allPosts.stream()
                .collect(Collectors.groupingBy(Voiture::getMarque, Collectors.counting()));

        // Create a PieChart Data list
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Long> entry : categoryCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Create a PieChart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Category Statistics");

        // Create a new alert dialog with PieChart as the content
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Category Statistics");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(pieChart);
        alert.showAndWait();
    }


    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, allVoitures.size());
        currentPageUsers = FXCollections.observableArrayList(allVoitures.subList(fromIndex, toIndex));

        // Populate your table with currentPageUsers here
        // For example, if you have a TableView called table:
        voitureTableView.setItems(currentPageUsers);

        return new AnchorPane(); // Return a placeholder node for now
    }
    private void filterVoitures(String query) throws SQLException {
        List<Voiture> filteredList = allVoitures.stream()
                .filter(voiture -> {
                    String searchString = query.toLowerCase();
                    return voiture.getMatricule().toLowerCase().contains(searchString) ||
                            voiture.getMarque().toLowerCase().contains(searchString) ||
                            String.valueOf(voiture.getPuissance()).contains(query) ||
                            String.valueOf(voiture.getPrix_voiture()).contains(query) ||
                            voiture.getUser().getEmail().toLowerCase().contains(searchString);
                })
                .collect(Collectors.toList());

        voitureTableView.getItems().clear();
        voitureTableView.getItems().addAll(filteredList);
    }

    @FXML
    void evaluationButtonOnAction(ActionEvent event){

        Stage stage = (Stage) evaluationButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToEvaluation();    }
    private void navigateToEvaluation() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherE.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AfficherE controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void mecanicienButtonOnAction(ActionEvent event){

        Stage stage = (Stage) mecanicienButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToMecanicien();    }
    private void navigateToMecanicien() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherM.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AfficherM controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void devisButtonOnAction(ActionEvent event){

        Stage stage = (Stage) devisButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToDevis();    }
    private void navigateToDevis() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("test.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            GestionDevis controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void contratButtonOnAction(ActionEvent event){

        Stage stage = (Stage) contratButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToContrat();    }
    private void navigateToContrat() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("test2.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            GestionContrat controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void CommentaireButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) commentaireButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToCommentaire();
    }

    private void navigateToCommentaire() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCommentaire.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AfficherCommentaire controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void PostButtonOnAction(ActionEvent event){

        Stage stage = (Stage) PostButton.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToPost();    }
    private void navigateToPost() {
        try {
            // Load the ListVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherPost.fxml"));
            Parent root = loader.load();

            // Access the controller of the ListVoiture.fxml file
            AfficherPostXML controller = loader.getController();

            // Show the scene containing the ListVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
