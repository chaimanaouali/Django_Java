package com.example.user;

import services.ServiceEvaluation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class AjouterE {

    @FXML
    private TextField AvisE;
    @FXML
    private ComboBox<String> comboboxmec;
    @FXML
    private TextField MecanicienID;

    @FXML
    private TextField NomE;

    @FXML
    private TextField PrenomE;

    @FXML
    private DatePicker dateE;



   private Connection connection;

    private final ServiceEvaluation re = new ServiceEvaluation();

    public void initialize() throws SQLException {
        // Appelez la fonction pour peupler le ComboBox avec les données de la base de données
        populateComboBox();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integration", "root", "");
            // Appelez la fonction pour peupler le ComboBox avec les données de la base de données
            populateComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de connexion à la base de données
        }

    }

       @FXML
       void btnAjouterE(ActionEvent event) {
           try {
               if (NomE.getText().isEmpty() || PrenomE.getText().isEmpty() || AvisE.getText().isEmpty() || dateE.getValue() == null || comboboxmec.getSelectionModel().isEmpty()) {
                   // Affichez une alerte si un champ est vide
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Erreur");
                   alert.setHeaderText(null);
                   alert.setContentText("Veuillez remplir tous les champs.");
                   alert.showAndWait();
               } else {
                   LocalDate dateEval = dateE.getValue();
                   String nomClient = NomE.getText();
                   String prenomClient = PrenomE.getText();
                   String avisClient = AvisE.getText();
                   String selectedNomMecanicin = comboboxmec.getSelectionModel().getSelectedItem();

                   // Récupération de l'ID du mécanicien sélectionné
                   int mecanicienId = getIDFromNomMecanicin(connection,selectedNomMecanicin);

                   // Utilisation de requêtes préparées pour insérer une nouvelle évaluation dans la base de données
                   try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/integration", "root", "");
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO evaluation (mecanicien_id, nom_client, prenom_client, avis_client, date_eval) VALUES (?, ?, ?, ?, ?)")) {
                       preparedStatement.setInt(1, mecanicienId);
                       preparedStatement.setString(2, nomClient);
                       preparedStatement.setString(3, prenomClient);
                       preparedStatement.setString(4, avisClient);
                       preparedStatement.setDate(5, Date.valueOf(dateEval));

                       int rowsAffected = preparedStatement.executeUpdate();

                       if (rowsAffected > 0) {
                           // Afficher une alerte de succès si l'ajout est réussi
                           Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                           successAlert.setTitle("Succès");
                           successAlert.setHeaderText(null);
                           successAlert.setContentText("L'évaluation a été ajoutée avec succès.");
                           successAlert.showAndWait();

                           // Nettoyer les champs après l'ajout
                           NomE.clear();
                           PrenomE.clear();
                           AvisE.clear();
                           dateE.setValue(null);
                           comboboxmec.getSelectionModel().clearSelection();
                       } else {
                           // Afficher une alerte si aucun enregistrement n'a été ajouté (ajout a échoué)
                           Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                           errorAlert.setTitle("Erreur");
                           errorAlert.setHeaderText(null);
                           errorAlert.setContentText("Une erreur s'est produite lors de l'ajout de l'évaluation.");
                           errorAlert.showAndWait();
                       }
                   }
               }
           } catch (NumberFormatException e) {
               // Afficher une alerte si une valeur numérique est invalide
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Erreur");
               alert.setHeaderText(null);
               alert.setContentText("Veuillez entrer des valeurs numériques valides.");
               alert.showAndWait();
           } catch (SQLException e) {
               System.out.println("SQL Error: " + e.getMessage());
           }
       }

    @FXML
    void btnaffichageajoutE(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherE.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la référence à la scène actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement du FXML
        }
    }

    private void populateComboBox() {
        ObservableList<String> NomMECList = FXCollections.observableArrayList();


        try {
           String url = "jdbc:mysql://localhost:3306/integration";
           String username = "root";
           String password = "";
           Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT id, nom FROM mecanicien";
            // Sélectionner les noms des mecanicien en joignant la table de evaluation avec la table du mecanicien

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Remplir la liste des noms des mecanicien
            while (resultSet.next()) {
                String nomMec = resultSet.getString("nom");//Cchaqye ligne il recupere le nom du mecanicien
                NomMECList.add(nomMec);
            }

            // Ajouter les données au ComboBox IdEv
            comboboxmec.setItems(NomMECList);

            // Réinitialiser le ResultSet
            resultSet.close();



        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private int getIDFromNomMecanicin(Connection connection, String nomMec) throws SQLException {
        int ID = -1;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM mecanicien WHERE nom = ?")) {
            preparedStatement.setString(1, nomMec);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ID = resultSet.getInt("id");
                }
            }
        }
        return ID;
    }

}
