package com.example.user;

import models.User;
import models.mecanicien;
import services.ServiceMecanicien;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import utils.DBconnection;
import utils.SessionManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import static utils.SessionManager.clearSession;

public class AfficherM {
    // Assurez-vous que les types des colonnes sont corrects
    @FXML
    private TableColumn<mecanicien, Integer> idM;

    @FXML
    private TableColumn<mecanicien, String> DispoM;

    @FXML
    private TableColumn<mecanicien, String> EmailM;

    @FXML
    private TableColumn<mecanicien, String> ImageM;

    @FXML
    private TableColumn<mecanicien, String> LocalM;

    @FXML
    private TableColumn<mecanicien, String> NomM;

    @FXML
    private TableColumn<mecanicien, String> NumM;

    @FXML
    private TableColumn<mecanicien, String> PrenomM;

    @FXML
    private TableView<mecanicien> TableMecanicien;

    @FXML
    private TextField searchMecBack;

    private ServiceMecanicien m;

    private Connection connect;
    private mecanicien mecanicienToModify;
    ObservableList<mecanicien> list = FXCollections.observableArrayList();

    @FXML
    private Button voitureButton;

    @FXML
    private Button logoutButton;
    User currentUser;

    @FXML
    private Button userButton;

    @FXML
    private Button PostButton;

    @FXML
    private Button commentaireButton;
    @FXML
    private Button devisButton;
    @FXML
    private Button contratButton;
    @FXML
    private Button homeBt;
    @FXML
    public void initialize() {
        this.currentUser = SessionManager.getSession().getUser();

        MecanicienListData();
        rechercherMecanicien("");

    }


    public void MecanicienListData() {
        ObservableList<mecanicien> listData = FXCollections.observableArrayList(); //Crée une liste observable  pour stocker les données récupérées dDB.

        String sql = "SELECT * FROM mecanicien";

        try {
            this.connect = DBconnection.getInstance().getCnx();
            PreparedStatement preparedStatement = this.connect.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                mecanicien m = new mecanicien(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("localisation"),
                        resultSet.getString("disponibilite"),
                        resultSet.getString("numero"),
                        resultSet.getString("image"),
                        resultSet.getString("email")
                );
                listData.add(m);
            }

            idM.setCellValueFactory(new PropertyValueFactory<>("id"));//source de valeur aficher
            NomM.setCellValueFactory(new PropertyValueFactory<>("nom"));
            PrenomM.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            LocalM.setCellValueFactory(new PropertyValueFactory<>("localisation"));
            DispoM.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
            NumM.setCellValueFactory(new PropertyValueFactory<>("numero"));
            ImageM.setCellValueFactory(new PropertyValueFactory<>("image"));
            EmailM.setCellValueFactory(new PropertyValueFactory<>("email"));

            // Assurez-vous de définir les données de la TableView après avoir configuré les colonnes
            TableMecanicien.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.m = new ServiceMecanicien();
    }

    void showErrorNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Appliquer le style CSS pour rendre l'alerte rouge
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("error-alert");

        alert.showAndWait();
    }

    @FXML
    void btnSupprimerM(ActionEvent event) {
        mecanicien selectedRecom = TableMecanicien.getSelectionModel().getSelectedItem();
        if (selectedRecom != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer ce mécanicien ?");
            //confirmationAlert.setContentText("Cette action ne peut pas être annulée.");

            ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                try {
                    // Assuming s.supprimer() also updates the data model
                    m.supprimer(selectedRecom);

                    // Assuming addRecetteListData() correctly updates the TableView
                    MecanicienListData();

                } catch (SQLException e) {
                    e.printStackTrace();
                    showErrorNotification("Erreur lors de la suppression de le mécanicien");
                }
            }
        } else {
            showErrorNotification("Veuillez sélectionner un mécanicien à supprimer");
        }
    }

    @FXML
    void btnModifierMAffichage(ActionEvent event) {
        mecanicien selectedRecom = TableMecanicien.getSelectionModel().getSelectedItem();
        if (selectedRecom != null) {
            try {
                // Charger le fichier FXML de la nouvelle scène de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierM.fxml"));
                Parent root = loader.load();


                ModifierM modifyController = loader.getController();

                // Passez la mecanicien sélectionnée au contrôleur de la scène de modification
                modifyController.setmecanicienToModify(selectedRecom);

                // Créer une nouvelle fenêtre pour la scène de modification
                Stage modifyStage = new Stage();
                modifyStage.setTitle("Modifier mecanicien");
                modifyStage.initModality(Modality.WINDOW_MODAL);
                modifyStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                modifyStage.setScene(new Scene(root));

                // Affichez la nouvelle fenêtre
                modifyStage.show();

            } catch (IOException e) {
                e.printStackTrace(); // Gérer les erreurs de chargement du FXML
            }
        }
    }

    @FXML
    void btnAjouterAffichage(ActionEvent event) {

        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterM.fxml"));
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


    @FXML
    void btnExcelMec(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        String sql = "SELECT * FROM mecanicien";
        this.connect = DBconnection.getInstance().getCnx();
        PreparedStatement preparedStatement = this.connect.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Détails mecanicien");
        HSSFRow header = sheet.createRow(0);
        header.createCell(1).setCellValue("Nom Mecanicien");
        header.createCell(2).setCellValue("Prenom Mecanicien");
        header.createCell(3).setCellValue("Localisation Mecanicien");
        header.createCell(4).setCellValue("Disponibilite Mecanicien");
        header.createCell(5).setCellValue("Numero Mecanicien");
        header.createCell(6).setCellValue("Image Mecanicien");
        header.createCell(7).setCellValue("Email Mecanicien");
        int index = 1;
        while (resultSet.next()) {

            HSSFRow row = sheet.createRow(index);
            row.createCell(1).setCellValue(resultSet.getString("nom"));
            row.createCell(2).setCellValue(resultSet.getString("prenom"));
            row.createCell(3).setCellValue(resultSet.getString("localisation"));
            row.createCell(4).setCellValue(resultSet.getString("disponibilite"));
            row.createCell(5).setCellValue(resultSet.getString("numero"));
            row.createCell(6).setCellValue(resultSet.getString("image"));
            row.createCell(7).setCellValue(resultSet.getString("email"));

            index++;
        }

        FileOutputStream fileOut = new FileOutputStream("C:/Users/amena/Downloads/Django_Java-userrrrrr/Django_Java-user/src/main/java/EXCEL/Mecanicien.xls");
        wb.write(fileOut);
        fileOut.close();

        JOptionPane.showMessageDialog(null, "Exportation 'EXCEL' effectuée avec succés");
        resultSet.close();

    }

    @FXML
    void btnpdfMec(ActionEvent event) {
        ObservableList<mecanicien> data = TableMecanicien.getItems();

        try {
            // Créez un nouveau document PDF
            PDDocument document = new PDDocument();

            // Créez une page dans le document
            PDPage page = new PDPage();
            document.addPage(page);

            // Obtenez le contenu de la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Définir le style de texte
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setLeading(14.5f); // Espace entre les lignes

            // Écrivez du texte dans le document
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750); // Positionnez le texte en haut de la page
            contentStream.showText("LES MECANICIENS");
            contentStream.newLine(); // Saut de ligne après le titre

            // Positionnez le texte des détails des mécaniciens sous le titre
            contentStream.newLineAtOffset(0, -20);

            for (mecanicien mecanicien : data) {
                // Appliquer le style CSS ici
                contentStream.showText("Nom : " + mecanicien.getNom());
                contentStream.newLine();
                contentStream.showText("Prénom : " + mecanicien.getPrenom());
                contentStream.newLine();
                contentStream.showText("Localisation : " + mecanicien.getLocalisation());
                contentStream.newLine();
                contentStream.showText("Disponibilité : " + mecanicien.getDisponibilite());
                contentStream.newLine();
                contentStream.showText("Numéro : " + mecanicien.getNumero());
                contentStream.newLine();
                contentStream.showText("Image : " + mecanicien.getImage());
                contentStream.newLine();
                contentStream.showText("Email : " + mecanicien.getEmail());
                contentStream.newLine();
                contentStream.newLine(); // Saut de ligne supplémentaire entre les entrées
            }

            // Fermez le contenu de la page
            contentStream.endText();
            contentStream.close();

            // Enregistrez le document PDF
            String outputPath = "C:/Users/amena/Downloads/Django_Java-userrrrrr/Django_Java-user/src/main/java/PDF/Mecanicien.pdf";
            File file = new File(outputPath);
            document.save(file);

            // Fermez le document
            document.close();

            JOptionPane.showMessageDialog(null, "Exportation 'PDF' effectuée avec succès");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void rechercherMecanicien(String rechercheText) {
        ServiceMecanicien serviceMec = new ServiceMecanicien();
        try {
            List<mecanicien> mecaniciens = serviceMec.rechercher(rechercheText, connect); // Assurez-vous de passer la connexion correcte
            ObservableList<mecanicien> observableList = FXCollections.observableList(mecaniciens);
            TableMecanicien.setItems(observableList);
        } catch (SQLException e) {
            showErrorNotification(e.getMessage());
        }
    }

    @FXML
    void rechercheMec(KeyEvent event) {
        String rechercheText = searchMecBack.getText().trim();
        rechercherMecanicien(rechercheText);
    }



    @FXML
    void btntrierMBack(ActionEvent event) {
        // Sauvegardez les données d'origine avant le tri
        ObservableList<mecanicien> originalData = FXCollections.observableArrayList(TableMecanicien.getItems());

        // Tri des mécaniciens par nom en utilisant Java Streams
        List<mecanicien> mecaniciens = originalData.stream()
                .sorted(Comparator.comparing(mecanicien::getNom))
                .collect(Collectors.toList());

        // Rechargez les données de mécaniciens triées
        TableMecanicien.getItems().setAll(mecaniciens);

        // Créez une tâche pour réinitialiser les données après quelques secondes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    // Réinitialisez les données de la TableView aux données d'origine
                    TableMecanicien.getItems().setAll(originalData);
                });
            }
        }, 3000);  // 3000 millisecondes = 3 secondes (vous pouvez ajuster ce nombre selon vos besoins)
    }
    @FXML
    void btnMec(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherM.fxml"));
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
    @FXML
    void btnEval(ActionEvent event) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller and pass the selected user to it
            Home controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}






