package com.example.javadjango;

import Models.SmsSender;
import Models.mecanicien;
import Models.evaluation;
import Services.ServiceEvaluation;
import Services.ServiceMecanicien;
import Utils.MyDB;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.EventHandler;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class AfficherMFRONT implements Initializable {
    private mecanicien selectedMecanicien;
    private final ServiceMecanicien sm = new ServiceMecanicien();
   private String uploads = "";
    FileChooser fc = new FileChooser();
    String filepath = null, filename = null, fn = null;
    List<mecanicien> list = new ArrayList<>();
    @FXML
    private TextField searchMecFront;
    public int getId() {
        return getId();
    }

    @FXML
    private ListView<GridPane> listView;
    @FXML
    private ImageView qrcodeMecanicen;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showMecanicienFront();

    }
    public void showMecanicienFront() {

        listView.getItems().clear();

        try {
            String query = "SELECT * FROM mecanicien"; // Requête SQL pour sélectionner tous les mécaniciens
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:4306/assurance", "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<mecanicien> mecaniciens = new ArrayList<>();

            // Récupérer tous les mécaniciens et les stocker dans une liste
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
                mecaniciens.add(m);
            }

            // Diviser la liste des mécaniciens en paquets de deux
            for (int i = 0; i < mecaniciens.size(); i += 2) {
                GridPane gridPane = new GridPane();
                gridPane.setHgap(200);

                // Créer un GridPane pour chaque paquet de deux mécaniciens
                VBox vbox1 = createMecanicienGridPaneBox(mecaniciens.get(i));
                gridPane.add(vbox1, 0, 0);

                // Ajouter le deuxième mécanicien s'il existe
                if (i + 1 < mecaniciens.size()) {
                    VBox vbox2 = createMecanicienGridPaneBox(mecaniciens.get(i + 1));
                    gridPane.add(vbox2, 1, 0);
                }

                // Ajouter le GridPane à la ListView
                listView.getItems().add(gridPane);
            }

            // Fermer les ressources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            // Gérer les exceptions SQL
            ex.printStackTrace();
        }
    }


    private VBox createMecanicienGridPaneBox(mecanicien mecanicien) {
        VBox vbox = new VBox();
        ImageView imageView = new ImageView();
        loadAndSetImage(imageView, mecanicien.getImage()); // Charger et définir l'image

        Label nameLabel = new Label( mecanicien.getNom());
        Label prenomLabel = new Label( mecanicien.getPrenom());
        Label localLabel = new Label( mecanicien.getLocalisation());
       // Label dispoLabel = new Label( mecanicien.getDisponibilite());
        Label numLabel = new Label( mecanicien.getNumero());
       // Label emailLabel = new Label( mecanicien.getEmail());
        Button addButton2 = new Button("Confirmer le RDV");
        Button addButton = new Button("Creer une evaluation");
        addButton.getStyleClass().add("addbuttonevaluation");
        Button qrCodeButton = new Button("QR code");
        qrCodeButton.getStyleClass().add("addbuttonPanier");


        // Ajouter les composants à VBox, en commençant par l'image
        vbox.getChildren().addAll(imageView, nameLabel, prenomLabel, localLabel,numLabel,addButton2,qrCodeButton,addButton);


        addButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    // Charger la vue AjouterE.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterEF.fxml"));
                    Parent root = loader.load();
                    AjouterEF controller= loader.getController();
                    controller.getNommecanicien().setText(mecanicien.getNom());
                    controller.setId_mecanicien(mecanicien.getId());
                    // Créer une nouvelle instance
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));

                    // Afficher la scène
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        qrCodeButton.setOnAction(event ->
                {
                    String qrData = "nom: " + mecanicien.getNom() + "\t prenom: " + mecanicien.getPrenom() + "\n localisation: " + mecanicien.getLocalisation() + "\t disponibilite: " + mecanicien.getDisponibilite() +"\n numero: " + mecanicien.getNumero() + "\t  image: " + mecanicien.getImage() +"\n email: " + mecanicien.getEmail();

                    // Générez et affichez le QR code
                    generateAndDisplayQRCode(qrData);
                }


        );
        /*addButton2.setOnAction(event->
                {

                    String toPhoneNumber = "+216" + "55686196";
                    System.out.println(toPhoneNumber);
                    SmsSender.sendVerificationCode(toPhoneNumber,mecanicien.getNom());
                }

                );*/
        // Définir l'espacement et l'alignement de la VBox
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        return vbox;



    }


    private void loadAndSetImage(ImageView imageView, String filename) {
        // Utiliser le chemin d'upload + le nom du fichier pour construire le chemin complet
        String completePath = uploads + filename;
        System.out.println("Chemin complet de l'image : " + completePath);

        // Utiliser un URI pour charger l'image
        File imageFile = new File(completePath);
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(190);
            imageView.setFitHeight(190);
        } else {
            // Gérer le cas où le fichier d'image n'existe pas
            System.out.println("Le fichier d'image n'existe pas : " + filename);
            imageView.setImage(null);
        }
    }

    private void generateAndDisplayQRCode(String qrData) {
        try {
            // Configuration pour générer le QR code
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Générer le QR code avec ZXing
            BitMatrix matrix = new MultiFormatWriter().encode(qrData, BarcodeFormat.QR_CODE, 184, 199, hints);
// Ajuster la taille de l'ImageView
            qrcodeMecanicen.setFitWidth(100);
            qrcodeMecanicen.setFitHeight(100);

            // Convertir la matrice en image JavaFX
            Image qrCodeImage = matrixToImage(matrix);

            // Afficher l'image du QR code dans l'ImageView
            qrcodeMecanicen.setImage(qrCodeImage);
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Succes");
            a.setContentText("qr code generer");
            a.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour convertir une matrice BitMatrix en image BufferedImage
    private Image matrixToImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelColor = matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                pixelWriter.setArgb(x, y, pixelColor);
            }
        }

        System.out.println("Matrice convertie en image avec succès");

        return writableImage;
    }


    @FXML
    void btnAfficherEFRONT(ActionEvent event) {
/*
        try {
            // Load the AjouterM.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherEF.fxml"));
            Parent root = loader.load();

            // Create the stage for the AjouterM scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Show the AjouterM stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    @FXML
    void searchMec(KeyEvent event) {}
    }






