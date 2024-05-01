package com.example.javadjango;
import Models.mecanicien;
import Services.Crud;
import javafx.scene.control.Button;
import Services.ServiceMecanicien;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.sql.SQLXML;

public class ModifierM {
    @FXML
    private TextField idm;
    @FXML
    private TextField NumM;

    @FXML
    private TextField dispoM;

    @FXML
    private TextField EmailM;


    @FXML
    private TextField LocalM;

    @FXML
    private TextField nomM;

    @FXML
    private TextField prenomM;
    private mecanicien mecanicienToModify;

    @FXML
    private ImageView ImageVmodifM;
    String filepath = null, filename = null, fn = null;
    FileChooser fc = new FileChooser();
    String uploads = "C:/Users/Nour/IdeaProjects/DjangoAssurance/src/main/resources/";
    private final ServiceMecanicien mecM = new ServiceMecanicien();

    @FXML
    void btnModifierM(ActionEvent event) {
        try {
            // Vérifiez si les champs ne sont pas vides
            if (nomM.getText().isEmpty() || prenomM.getText().isEmpty() || LocalM.getText().isEmpty() || dispoM.getText().isEmpty() || NumM.getText().isEmpty() || EmailM.getText().isEmpty()) {
                // Affichez une alerte si un champ est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return; // Quittez la méthode si un champ est vide
            }

            // Vérifiez si l'adresse e-mail est valide en utilisant une expression régulière
            String adresse = EmailM.getText();
            if (!adresse.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                // Affichez une alerte si l'adresse e-mail n'est pas valide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer une adresse e-mail valide (sous forme '***@***.**').");
                alert.showAndWait();
                return; // Quittez la méthode si l'adresse e-mail n'est pas valide
            }

            // Vérifiez si le champ de numéro contient uniquement des chiffres
            String numString = NumM.getText();
            if (!numString.matches("[0-9]+") || numString.length() > 8) {
                // Affichez une alerte si le champ de numéro contient des caractères non numériques ou dépasse 8 chiffres
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer un numéro uniquement de chiffres ne dépassant pas 8 chiffres.");
                alert.showAndWait();
                return; // Quittez la méthode si le numéro n'est pas valide
            }

            // Conversion de l'ID du mécanicien en entier
            int mecId = Integer.parseInt(idm.getText());

            // Modifier le mécanicien seulement si aucun champ n'est vide et si l'adresse e-mail est valide
            mecM.modifier(new mecanicien(mecId,
                    nomM.getText(),
                    prenomM.getText(),
                    LocalM.getText(),
                    dispoM.getText(),
                    NumM.getText(),
                    filepath,
                    EmailM.getText()));

            // Afficher une notification de succès
            showSuccessNotification("Modif réussie");

        } catch (SQLException e) {
            // Afficher les détails de l'exception SQL
            e.printStackTrace();
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Error executing SQL query", e);
        }
    }

    private void showSuccessNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void btnImporterModifM(ActionEvent actionEvent) throws IOException {
        File file = fc.showOpenDialog(null);
        // Shows a new file open dialog.
        if (file != null) {
            // URI that represents this abstract pathname
            ImageVmodifM.setImage(new Image(file.toURI().toString()));

            filename = file.getName();
            filepath = file.getAbsolutePath();

            fn = filename;

            FileChannel source = new FileInputStream(filepath).getChannel();
            FileChannel dest = new FileOutputStream(uploads + filename).getChannel();
            dest.transferFrom(source, 0, source.size());
        } else {
            System.out.println("Fichier invalide!");
        }
    }


    public void setmecanicienToModify(mecanicien M) {
        // Supposons que cette classe possède des attributs pour idm, nomM, etc., qui sont des contrôles d'interface utilisateur.
        try {
           // this.mecanicienToModify = M;

            // Assurer que M n'est pas null avant d'initialiser les champs
            if (M != null) {
                this.mecanicienToModify = M;
                idm.setText(String.valueOf(M.getId()));
                nomM.setText(String.valueOf(M.getNom()));

               // idm.setText(String.valueOf(M.getId()));
               // nomM.setText(String.valueOf(M.getNom()));
                prenomM.setText(String.valueOf(M.getPrenom()));
                LocalM.setText(String.valueOf(M.getLocalisation()));
                dispoM.setText(String.valueOf(M.getDisponibilite()));
                NumM.setText(String.valueOf(M.getNumero()));
                EmailM.setText(String.valueOf(M.getEmail()));

                // Afficher l'image
                String imageFilePath = M.getImage();
                if (imageFilePath != null && !imageFilePath.isEmpty()) {
                    File imageFile = new File(imageFilePath);
                    if (imageFile.exists()) {
                        ImageVmodifM.setImage(new Image(imageFile.toURI().toString()));
                    } else {
                        System.out.println("Le fichier d'image n'existe pas : " + imageFilePath);
                    }
                } else {
                    System.out.println("Chemin d'image invalide !");
                }
            }
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de la modification du mécanicien : " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void btnafficherModifM(ActionEvent event) {
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

    public void btnmecanicien(ActionEvent actionEvent) {
            try {
                // Charger le fichier FXML de la nouvelle scène
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherM.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Obtenir la référence à la scène actuelle
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();  // Correction ici: 'event' devrait être 'actionEvent'

                // Définir la nouvelle scène sur la fenêtre principale
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace(); // Gérer les erreurs de chargement du FXML
            }
        }

    public void btnevaluation(ActionEvent actionEvent) {
            try {
                // Charger le fichier FXML de la nouvelle scène
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherE.fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène
                Scene scene = new Scene(root);

                // Obtenir la référence à la scène actuelle
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();  // Correction ici: 'event' devrait être 'actionEvent'

                // Définir la nouvelle scène sur la fenêtre principale
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace(); // Gérer les erreurs de chargement du FXML
            }
        }

    }








