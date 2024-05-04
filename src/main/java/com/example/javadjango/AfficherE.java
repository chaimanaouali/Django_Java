package com.example.javadjango;

import Models.evaluation;

import Models.mecanicien;
import Services.ServiceEvaluation;
import Services.ServiceMecanicien;
import Utils.MyDB;
import com.google.protobuf.BoolValue;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import javafx.scene.chart.PieChart;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import java.util.Timer;
import java.util.stream.Collectors;
import javafx.scene.control.Label;

// Déclaration du label


public class AfficherE {

        @FXML
        private TableColumn<evaluation, String> AvisE;
        @FXML
        private TableColumn<evaluation, LocalDate> Date_eval;
        @FXML
        private TableColumn<evaluation, String> NomE;
        @FXML
        private TableView<evaluation> TableEvaluation;
        @FXML
        private TableColumn<evaluation, Integer> mecanicien_id;
        @FXML
        private TableColumn<evaluation, String> prenomE;
        @FXML
        private TableColumn<evaluation, Integer> IdE;
        @FXML
        private PieChart pieChart;
        @FXML
        private TextField searchEvalBack;
        @FXML
        private Label labelInfo;
        private Connection connect;
        private ServiceEvaluation eval;
       // private evaluation evaluationToModify;
        @FXML
        private DatePicker rechercheEval;


        @FXML
        public void initialize() {
                EvaluationListData();
                afficherRepartitionEvaluationParMecanicien();
        }

        private void EvaluationListData() {
                ObservableList<evaluation> listData = FXCollections.observableArrayList();
                String sql = "SELECT * FROM evaluation";
                this.connect = MyDB.getInstance().getConnection();
                try {
                        PreparedStatement preparedStatement = this.connect.prepareStatement(sql);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                                evaluation eval = new evaluation(
                                        resultSet.getInt("id"),
                                        resultSet.getInt("mecanicien_id"),
                                        resultSet.getString("nom_client"),
                                        resultSet.getString("prenom_client"),
                                        resultSet.getString("avis_client"),
                                        resultSet.getDate("date_eval").toLocalDate() // Assuming the date_eval column is of type DATE
                                );
                                listData.add(eval);
                        }

                        // Setup the TableColumn value factories
                        IdE.setCellValueFactory(new PropertyValueFactory<>("id"));
                        mecanicien_id.setCellValueFactory(new PropertyValueFactory<>("mecanicien_id"));
                        NomE.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
                        prenomE.setCellValueFactory(new PropertyValueFactory<>("prenom_client"));
                        AvisE.setCellValueFactory(new PropertyValueFactory<>("avis_client"));
                        Date_eval.setCellValueFactory(new PropertyValueFactory<>("date_eval"));

                        // Set the items in the TableView
                        TableEvaluation.setItems(listData);
                } catch (Exception e) {
                        e.printStackTrace();
                }

                this.eval = new ServiceEvaluation();
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
        void btnsupprimerE(ActionEvent event) {
                evaluation selectedRecom = TableEvaluation.getSelectionModel().getSelectedItem();
                if (selectedRecom != null) {
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Confirmation de suppression");
                        confirmationAlert.setHeaderText("Voulez-vous vraiment supprimer cette evaluation ?");
                        //confirmationAlert.setContentText("Cette action ne peut pas être annulée.");

                        ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

                        if (result == ButtonType.OK) {
                                try {
                                        // Assuming eval.supprimer() also updates the data model
                                        eval.supprimer(selectedRecom);

                                        // Assuming addevalListData() correctly updates the TableView
                                        EvaluationListData();
                                } catch (SQLException e) {
                                        e.printStackTrace();
                                        showErrorNotification("Erreur lors de la suppression de le mécanicien");
                                }
                        }
                } else {
                        showErrorNotification("Veuillez sélectionner une evaluation à supprimer");
                }
        }

        @FXML
        void btnModifierEAffichage(ActionEvent event) {
                evaluation selectedRecom = TableEvaluation.getSelectionModel().getSelectedItem();
                if (selectedRecom != null) {
                        try {
                                // Charger le fichier FXML de la nouvelle scène de modification
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierE.fxml"));
                                Parent root = loader.load();

                                // Accédez au contrôleur de la scène de modification
                                // modifier_eval_controller modifyController = loader.getController();

                                ModifierE modifyController= loader.getController();

                                // Passez la eval sélectionnée au contrôleur de la scène de modification
                               modifyController.setEvaluationToModify(selectedRecom);

                                // Créer une nouvelle fenêtre pour la scène de modification
                                Stage modifyStage = new Stage();
                                modifyStage.setTitle("Modifier evaluation");
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
        void btnAjouterAffichageE(ActionEvent event) {
                try {
                        // Charger le fichier FXML de la nouvelle scène
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterE.fxml"));
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
        void btnExcelEval(ActionEvent event) throws SQLException, IOException {
                String sql = "SELECT * FROM evaluation";
                this.connect = MyDB.getInstance().getConnection();
                PreparedStatement preparedStatement = this.connect.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("Détails evaluation");

                HSSFRow header = sheet.createRow(0);
                header.createCell(1).setCellValue("ID Mecanicien");
                header.createCell(2).setCellValue("Nom Client");
                header.createCell(3).setCellValue("Prenom Client ");
                header.createCell(4).setCellValue("Avis Client");
                header.createCell(5).setCellValue("Date Eval");

                int index = 1;
                while (resultSet.next()) {

                        HSSFRow row = sheet.createRow(index);
                        row.createCell(1).setCellValue(resultSet.getString("mecanicien_id"));
                        row.createCell(2).setCellValue(resultSet.getString("nom_client"));
                        row.createCell(3).setCellValue(resultSet.getString("prenom_client"));
                        row.createCell(4).setCellValue(resultSet.getString("avis_client"));
                        row.createCell(5).setCellValue(resultSet.getString("date_eval"));

                        index++;
                }

                FileOutputStream fileOut = new FileOutputStream("C:/Users/Nour/IdeaProjects/JAVADjango/src/main/java/EXCEL/Evaluation.xls");
                wb.write(fileOut);
                fileOut.close();
                JOptionPane.showMessageDialog(null, "Exportation 'EXCEL' effectuée avec succés");
                resultSet.close();

        }

        @FXML
        void btnpdfEval(ActionEvent event) {
                ObservableList<evaluation> data = TableEvaluation.getItems();

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
                        contentStream.newLineAtOffset(50, 700); // Positionnez le texte en haut de la page
                        contentStream.showText("LES EVALUATIONS");
                        contentStream.newLine(); // Saut de ligne après le titre

                        // Positionnez le texte des détails des évaluations sous le titre
                        contentStream.newLineAtOffset(0, -20);

                        for (evaluation evaluation : data) {
                                // Appliquer le style CSS ici
                                contentStream.showText("Nom : " + evaluation.getNom_client());
                                contentStream.newLine();
                                contentStream.showText("Prénom : " + evaluation.getPrenom_client());
                                contentStream.newLine();
                                contentStream.showText("Avis : " + evaluation.getAvis_client());
                                contentStream.newLine();
                                contentStream.showText("Date Eval : " + evaluation.getDate_eval());
                                contentStream.newLine();
                                contentStream.newLine(); // Saut de ligne supplémentaire entre les entrées
                        }

                        // Fermez le contenu de la page
                        contentStream.endText();
                        contentStream.close();

                        // Enregistrez le document PDF
                        String outputPath = "C:/Users/Nour/IdeaProjects/JAVADjango/src/main/java/PDF/Evaluation.pdf";
                        File file = new File(outputPath);
                        document.save(file);

                        // Fermez le document
                        document.close();

                        JOptionPane.showMessageDialog(null, "Exportation 'PDF' effectuée avec succès");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }



        private void rechercherEvaluation(String rechercheText) {
                ServiceEvaluation serviceMec = new ServiceEvaluation();
                try {
                        List<evaluation> evaluations = serviceMec.rechercher(rechercheText, connect); // Assurez-vous de passer la connexion correcte
                        ObservableList<evaluation> observableList = FXCollections.observableList(evaluations);
                        TableEvaluation.setItems(observableList);
                } catch (SQLException e) {
                        showErrorNotification(e.getMessage());
                }
        }

        @FXML
        void rechercheEval(KeyEvent event) {

                String rechercheText = searchEvalBack.getText().trim();
                rechercherEvaluation(rechercheText);
        }


        private void afficherRepartitionEvaluationParMecanicien() {
                // Récupérer la liste des évaluations
                ObservableList<evaluation> evaluations = TableEvaluation.getItems();

                // Créer un map pour stocker le nombre total d'évaluations pour chaque mécanicien
                Map<Integer, Integer> totalEvaluationCounts = new HashMap<>();

                // Parcourir les évaluations et compter le nombre total d'évaluations pour chaque mécanicien
                for (evaluation eval : evaluations) {
                        int mecanicienId = eval.getMecanicien_id();
                        totalEvaluationCounts.put(mecanicienId, totalEvaluationCounts.getOrDefault(mecanicienId, 0) + 1);
                }

                // Créer une liste pour stocker les données du graphique circulaire
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

                // Ajouter les données au graphique circulaire avec les étiquettes
                totalEvaluationCounts.forEach((mecanicienId, total) -> {
                        String label = "Mécanicien ID: " + mecanicienId + " (" + total + ")";
                        pieChartData.add(new PieChart.Data(label, total));
                });

                // Définir les données dans le PieChart
                pieChart.setData(pieChartData);

                // Afficher les informations dans le label
                StringBuilder labelText = new StringBuilder();
                pieChartData.forEach(data -> labelText.append(data.getName()).append("\n"));
                labelInfo.setText(labelText.toString());
        }



        @FXML
        void btntrierEBack(ActionEvent event) {
                // Sauvegardez les données d'origine avant le tri
                ObservableList<evaluation> originalData = FXCollections.observableArrayList(TableEvaluation.getItems());

                // Tri des mécaniciens par nom en utilisant Java Streams
                List<evaluation> evaluations = originalData.stream()
                        .sorted(Comparator.comparing(evaluation::getNom_client))
                        .collect(Collectors.toList());

                // Rechargez les données de mécaniciens triées
                TableEvaluation.getItems().setAll(evaluations);

                // Créez une tâche pour réinitialiser les données après quelques secondes
                java.util.Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                                Platform.runLater(() -> {
                                        // Réinitialisez les données de la TableView aux données d'origine
                                        TableEvaluation.getItems().setAll(originalData);
                                });
                        }
                }, 3000);
        }

        @FXML
        void btnMecanicien(ActionEvent event) {
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
        void btnEvaluation(ActionEvent event) {
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
}







