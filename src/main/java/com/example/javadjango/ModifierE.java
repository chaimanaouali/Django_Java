package com.example.javadjango;
import Models.evaluation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ModifierE {

    @FXML
    private TextField AvisE;

    @FXML
    private ComboBox<String> comboboxmec;

    @FXML
    private TextField NomE;

    @FXML
    private TextField PrenomE;

    @FXML
    private DatePicker dateE;

    @FXML
    private TextField ide;

    private Connection connection;

    private final evaluation e = new evaluation();

    private evaluation evaluationToModify;

    public void initialize() throws SQLException {
        populateComboBox();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:4306/assurance", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
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

    private String getNomFromIDMecanicin(Connection connection, int mecId) throws SQLException {
        String nom = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT nom FROM mecanicien WHERE id = ?")) {
            preparedStatement.setInt(1, mecId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    nom = resultSet.getString("nom");
                }
            }
        }
        return nom;
    }

    public void setEvaluationToModify(evaluation E) {
        this.evaluationToModify = E;
        if (E != null) {
            ide.setText(String.valueOf(E.getId()));
            try {
                int mecId = E.getMecanicien_id();
                String nomMec = getNomFromIDMecanicin(connection, mecId);
                comboboxmec.setValue(nomMec);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la récupération du nom du mécanicien: " + e.getMessage());
            }
            NomE.setText(E.getNom_client());
            PrenomE.setText(E.getPrenom_client());
            AvisE.setText(E.getAvis_client());
            dateE.setValue(E.getDate_eval());
        }
    }

    @FXML
    void btnmodifierE(ActionEvent event) {
        // Vérifiez si tous les champs sont remplis
        if (ide.getText().isEmpty() || comboboxmec.getValue() == null || NomE.getText().isEmpty() || PrenomE.getText().isEmpty() || AvisE.getText().isEmpty() || dateE.getValue() == null) {
            // Afficher une alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Quittez la méthode si un champ est vide
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:4306/assurance", "root", "")) {
            int idEvaluation = Integer.parseInt(ide.getText());
            String nomMec = comboboxmec.getValue();
            int mecId = getIDFromNomMecanicin(connection, nomMec);
            String nomClient = NomE.getText();
            String prenomClient = PrenomE.getText();
            String avisClient = AvisE.getText();
            LocalDate dateEval = dateE.getValue();

            String sql = "UPDATE evaluation SET mecanicien_id=?,nom_client = ?, prenom_client = ?, avis_client = ?, date_eval = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, mecId);
                preparedStatement.setString(2, nomClient);
                preparedStatement.setString(3, prenomClient);
                preparedStatement.setString(4, avisClient);
                preparedStatement.setDate(5, Date.valueOf(dateEval));
                preparedStatement.setInt(6, idEvaluation);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("L'évaluation a été modifiée avec succès.");
                    successAlert.showAndWait();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Aucune évaluation n'a été modifiée.");
                    errorAlert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorNotification("Erreur lors de la modification de l'évaluation");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void showErrorNotification(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    void btnafficherModifE(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherE.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateComboBox() {
        ObservableList<String> NomMECList = FXCollections.observableArrayList();

        try {
            String url = "jdbc:mysql://localhost:4306/assurance";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT id, nom FROM mecanicien";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nomMec = resultSet.getString("nom");
                NomMECList.add(nomMec);
            }

            comboboxmec.setItems(NomMECList);
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
