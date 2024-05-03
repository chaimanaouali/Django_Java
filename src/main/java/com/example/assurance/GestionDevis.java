/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.assurance;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Devis;
import models.ReponseDevis;
import services.ServiceDevis;
import services.ServiceReponseDevis;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author chaima
 */
public class GestionDevis implements Initializable {
    private  Path source ;
    @FXML
    private Label nomCol;

    @FXML
    private Label numtelCol1111;

    @FXML
    private Label numtelCol11111;

    @FXML
    private Label prenomCol;

    @FXML
    private Label prixCol111;

    @FXML
    private Label puissanceCol11;

    @FXML
    private TextField tfAdresse;

    @FXML
    private TextField tfAdresse1;

    @FXML
    private DatePicker tfDate;

    @FXML
    private DatePicker tfDate1;

    @FXML
    private TextField tfEmail;

    @FXML
    private ComboBox<String> tfEmail1;

    @FXML
    private TextField tfModele;

    @FXML
    private TextField tfModele1;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfNom1;

    @FXML
    private TextField tfNumtel;

    @FXML
    private TextField tfPrenom;

    @FXML
    private ComboBox<String> tfPrenom1;

    @FXML
    private TextField tfPrix;
    @FXML
    private TextField recherchee;

    @FXML
    private TextField tfPuissance;

    @FXML
    private TextField tfPuissance1;
    @FXML
    private VBox vboxDevis;
    @FXML
    private VBox vboxDevis1;

    public static final String ACCOUNT_SID = "ACdb45117869a081108be58ab82f838f35";
    public static final String AUTH_TOKEN = "73d8c4d94e62cfd601451fbfd7008f8d";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    @FXML
    void insertOne(ActionEvent event) {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfAdresse.getText().isEmpty() ||
                tfEmail.getText().isEmpty() || tfDate.getValue() == null || tfModele.getText().isEmpty() ||
                tfPuissance.getText().isEmpty() || tfPrix.getText().isEmpty() || tfNumtel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs obligatoires!");
            alert.show();
        } else {
            try {
                // Vérification de l'e-mail
                if (!isValidEmail(tfEmail.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Veuillez saisir une adresse e-mail valide!");
                    alert.show();
                    return; // Arrête le traitement si l'e-mail n'est pas valide
                }

                // Vérification du numéro de téléphone
                if (!isValidPhoneNumber(tfNumtel.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Veuillez saisir un numéro de téléphone valide (8 chiffres)!");
                    alert.show();
                    return; // Arrête le traitement si le numéro de téléphone n'est pas valide
                }

                // Vérification de la date de naissance
                if (!isValidAge(tfDate.getValue())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Vous devez avoir au moins 18 ans!");
                    alert.show();
                    return; // Arrête le traitement si l'âge n'est pas valide
                }

                // Si toutes les validations sont réussies, procédez à l'insertion
                String selectedNom = tfNom.getText();
                String selectedPrenom = tfPrenom.getText();
                String selectedAdresse = tfAdresse.getText();
                String selectedEmail = tfEmail.getText();
                LocalDate selectedDatenaiss = tfDate.getValue(); // Assuming tfDate is a DatePicker
                String selectedModele = tfModele.getText();
                String selectedPuissance = tfPuissance.getText();
                double selectedPrix = Double.parseDouble(tfPrix.getText());
                int selectedNumtel = Integer.parseInt(tfNumtel.getText());

                Devis d = new Devis(selectedNom, selectedPrenom, selectedAdresse, selectedEmail, selectedDatenaiss, selectedNumtel, selectedModele, selectedPuissance, selectedPrix);
                ServiceDevis sp = new ServiceDevis();
                sp.insertOne(d);
                afficherDevis();
                Alert alert = new Alert(AlertType.WARNING);

                // Set the title
                alert.setTitle("Ajout");

                // Set the header text
                alert.setHeaderText("Devis Ajouté!");

                // Set the content text
                alert.setContentText("Devis ajouté avec succes");

                // Display the alert
                alert.showAndWait();
                tfNom.setText("");
               tfPrenom.setText("");
                 tfAdresse.setText("");
                tfEmail.setText("");
               tfDate.setValue(null);
               tfModele.setText("");
               tfPuissance.setText("");
               tfPrix.setText("");
               tfNumtel.setText("");


            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            }
        }
    }

    @FXML
    void out(ActionEvent event){
        Stage stage = (Stage) tfNom.getScene().getWindow();
        stage.close();
    }
    private boolean isValidEmail(String email) {
        // Expression régulière pour valider l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Vérifie si la longueur du numéro de téléphone est de 8 chiffres
        return phoneNumber.matches("\\d{8}");
    }

    private boolean isValidAge(LocalDate birthDate) {
        // Calcul de l'âge à partir de la date de naissance
        LocalDate currentDate = LocalDate.now();
        int age = currentDate.minusYears(18).compareTo(birthDate);
        return age >= 0; // Renvoie vrai si l'âge est de 18 ans ou plus
    }
    @FXML
    void recherche(KeyEvent event) {
        System.out.println("dd");
        vboxDevis.getChildren().clear();
        String charr = recherchee.getText();
        System.out.println(charr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        // Retrieve data from the database
        ServiceDevis serviceDevis = new ServiceDevis(); // Creating an instance of ServiceDevis
        List<Devis> deviss = null; // Calling selectAll() on the instance
        try {
            deviss = serviceDevis.rechercheF(charr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        vboxDevis.getChildren().clear();
        ServiceDevis sd = new ServiceDevis();

        HBox hb[]= new HBox[deviss.size()];
        for (Devis d: deviss
        ) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("itemDevis.fxml"));
                Parent root = loader.load();
                itemDevis otherController = loader.getController();
                HBox hbox = (HBox) loader.getRoot();
                vboxDevis.getChildren().add(hbox);


                otherController.afficher(d);
                hbox.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) { // Double-click detected
                        Devis selectedDevis = d;
                        if (selectedDevis != null) {
                            // Navigate to UpdateUser.fxml
                            navigateToUpdateDevis(selectedDevis);
                            afficherDevis();
                        }
                    }
                });

                Button deleteButton = otherController.getDeleteButton();
                int id = otherController.getId();
                deleteButton.setOnAction(eve -> {
                    // Remove the HBox from the VBox when the button is clicked
                    deleteDevis(id);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    void chart(ActionEvent ev) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chart.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            chart controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                // Refresh the TableView when the PopUp stage is closed
                afficherDevis();
            });
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void insertOne1(ActionEvent event) {
        if (tfNom1.getText().isEmpty()  || tfAdresse1.getText().isEmpty() ||
                tfDate1.getValue() == null || tfModele1.getText().isEmpty() ||
                tfPuissance1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs obligatoires!");
            alert.show();
        } else {
            try {

                String selectedEtat = tfNom1.getText();
                String selectedDecision = tfPrenom1.getValue();
                String selectedDocuments = tfAdresse1.getText();
                String selectedEmail1 = (String)tfEmail1.getValue();
                LocalDate selectedDate = tfDate1.getValue(); // Assuming tfDate is a DatePicker
                String selectedDelai = tfModele1.getText();
                String selectedDuree = tfPuissance1.getText();
                ServiceDevis dss = new ServiceDevis();
                List<Devis> ddd = dss.selectAll();
                int selectedEmail =1;

                for (Devis d: ddd
                ) {

                    if (d.getEmail().equals(selectedEmail1) ){
                        selectedEmail=d.getId();

                    }

                }

                ReponseDevis d = new ReponseDevis(selectedEmail, selectedEtat, selectedDecision,selectedDelai,  selectedDuree, selectedDocuments,selectedDate);
                ServiceReponseDevis sp = new ServiceReponseDevis();
                sp.insertOne(d);
                afficherReponseDevis();
                Alert alert = new Alert(AlertType.WARNING);

                // Set the title
                alert.setTitle("Ajout");
                String recipientPhoneNumber = "+21624534106";
                String messageBody = "votre reponse devis est ajouté avec succés";
                sendSMS(recipientPhoneNumber, messageBody);

                // Set the header text
                alert.setHeaderText("Reponse Devis Ajouté!");

                // Set the content text
                alert.setContentText(" Reponse Devis ajouté avec succes");

                // Display the alert
                alert.showAndWait();
                tfNom1.setText("");
                tfPrenom1.getSelectionModel().selectFirst();
                tfAdresse1.setText("");
                tfEmail1.getSelectionModel().selectFirst();
                tfDate1.setValue(null);
                tfModele1.setText("");
                tfPuissance1.setText("");


               Path target = Paths.get("src/main/resources/Documents/"+selectedEmail1+".txt"); // Destination within your project directory
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);


            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        afficherDevis();
        afficherReponseDevis();
        ServiceDevis ds = new ServiceDevis();
        List<Devis> dd = null;
        try {
            dd = ds.selectAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> Emails = new ArrayList<>();
        for (Devis d: dd
        ) {
            Emails.add(d.getEmail());

        }
        System.out.println(Emails);
        for (String e: Emails
        ) {
            System.out.println("dddd");
            tfEmail1.getItems().add(e);
        }
        tfEmail1.getSelectionModel().selectFirst();
        tfPrenom1.getItems().add("En Cours");
        tfPrenom1.getItems().add("Acceptée");
        tfPrenom1.getItems().add("Réfusée");
        tfPrenom1.getSelectionModel().selectFirst();
    }


    public void deleteDevis(int id){
        // Afficher une boîte de dialogue de confirmation
        int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce devis ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

        // Vérifier la réponse de l'utilisateur
        if (confirmation == JOptionPane.YES_OPTION) {
            ServiceDevis st = new ServiceDevis();
            try {
                st.deleteOne(id);
                System.out.println("devis deleted successfully.");
                afficherDevis();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Failed to delete devis: " + e.getMessage());
            }
        }
    }
    public void deleteReponseDevis(int id){
        // Afficher une boîte de dialogue de confirmation
        int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer cette Reponse Devis ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);

        // Vérifier la réponse de l'utilisateur
        if (confirmation == JOptionPane.YES_OPTION) {
            ServiceReponseDevis st = new ServiceReponseDevis();
            try {
                st.deleteOne(id);
                System.out.println("devis deleted successfully.");
                afficherReponseDevis();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Failed to delete devis: " + e.getMessage());
            }
        }
    }
public void afficherDevis(){
        vboxDevis.getChildren().clear();
    ServiceDevis sd = new ServiceDevis();
    try {
        List<Devis>deviss = sd.selectAll();
        HBox hb[]= new HBox[deviss.size()];
        for (Devis d: deviss
        ) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("itemDevis.fxml"));
                Parent root = loader.load();
                itemDevis otherController = loader.getController();
                HBox hbox = (HBox) loader.getRoot();
                vboxDevis.getChildren().add(hbox);


                otherController.afficher(d);
                hbox.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) { // Double-click detected
                        Devis selectedDevis = d;
                        if (selectedDevis != null) {
                            // Navigate to UpdateUser.fxml
                            navigateToUpdateDevis(selectedDevis);
                            afficherDevis();
                        }
                    }
                });

                Button deleteButton = otherController.getDeleteButton();
                int id = otherController.getId();
                deleteButton.setOnAction(event -> {
                    // Remove the HBox from the VBox when the button is clicked
                    deleteDevis(id);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}

    public void afficherReponseDevis(){
        vboxDevis1.getChildren().clear();
        ServiceReponseDevis sd = new ServiceReponseDevis();
        try {
            List<ReponseDevis>deviss = sd.selectAll();
            HBox hb[]= new HBox[deviss.size()];
            for (ReponseDevis d: deviss
            ) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("itemReponseDevis.fxml"));
                    Parent root = loader.load();
                    itemReponseDevis otherController = loader.getController();
                    HBox hbox = (HBox) loader.getRoot();
                    vboxDevis1.getChildren().add(hbox);


                    otherController.afficher(d);

                    hbox.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) { // Double-click detected
                            ReponseDevis selectedDevis = d;
                            if (selectedDevis != null) {
                                // Navigate to UpdateUser.fxml
                                try {
                                    navigateToUpdateReponseDevis(selectedDevis);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                afficherDevis();
                            }
                        }
                    });

                    Button deleteButton = otherController.getDeleteButton();
                    int id = otherController.getId();
                    int ide = otherController.getEmailId();
                    ServiceDevis dd =new ServiceDevis();

                    deleteButton.setOnAction(event -> {
                        // Remove the HBox from the VBox when the button is clicked
                        deleteReponseDevis(id);
                    });
                    Button btqr = otherController.getbtqr();

                    btqr.setOnAction(event -> {
                        Devis dee = null;
                        try {
                            System.out.println(ide);
                            dee = dd.rechercheId(ide);
                            System.out.println(dee);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        // Remove the HBox from the VBox when the button is clicked
                        String AllInfo = "Nom: " + dee.getNom() + "\nprénom:"+dee.getPrenom()+"\nadresse: " + dee.getAdresse() + "\nemail: " + dee.getEmail() + "\ndate de naissance: " + dee.getDate_naiss()
                                + "\nmodele: " + dee.getModele() + "\npuissance: " + dee.getPuissance() + "\nprix: " + dee.getPuissance() + "\nnuméro téléphone: " + dee.getNum_tel() ;
                        qr(AllInfo);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void qr(String AllInfo) {
        // Generate the information for the QR code




        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int width = 300;
        int height = 300;

        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(AllInfo, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, byteMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            System.out.println("QR created successfully....");

        } catch (WriterException ex) {
            ex.printStackTrace();
            return; // Exit the method if an exception occurs
        }

        ImageView qr = new ImageView();
        qr.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

        StackPane obj = new StackPane();
        obj.getChildren().add(qr);
        Scene scene = new Scene(obj, 300, 250);
        Stage p1 = new Stage();
        p1.setTitle("QRCode");
        p1.setScene(scene);
        p1.show();

        // Save the QR code as an image file
        File imageFile = new File("/resources/screenshotqr/screenshot.png");
        try {
            ImageIO.write(bufferedImage, "png", imageFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void navigateToUpdateDevis(Devis devis) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDevis.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            updateDevis controller = loader.getController();
            controller.initData(devis);
            Button up = controller.getBt();
            up.setOnMouseClicked(event -> {
                afficherDevis();
            });

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                // Refresh the TableView when the PopUp stage is closed

                Stage gg= (Stage)tfNom.getScene().getWindow();
                gg.close();

            });
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToUpdateReponseDevis(ReponseDevis devis) throws SQLException {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateReponseDevis.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            updateReponseDevis controller = loader.getController();
            controller.initData(devis);

            Button up = controller.getBt();
            up.setOnMouseClicked(event -> {
                afficherReponseDevis();
                System.out.println("yes");
            });
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            stage.setOnCloseRequest(event -> {
                // Refresh the TableView when the PopUp stage is closed

                Stage gg= (Stage)tfNom.getScene().getWindow();
                gg.close();
                afficherReponseDevis();
            });
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ChooseF(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {

            source = Path.of(selectedFile.getAbsolutePath());
            tfAdresse.setText(source.toString());

        }
    }

    public static void sendSMS(String recipientPhoneNumber, String messageBody) {
        String twilioPhoneNumber = "+13342039105";

        Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        messageBody)
                .create();

        System.out.println("SMS sent successfully. SID: " + message.getSid());
    }
    private void navigateToMail() {
        try {
            // Load the AddVoiture.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mail.fxml"));
            javafx.scene.Parent root = loader.load();

            // Show the scene containing the AddVoiture.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table view after adding a new voiture

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToMailling(ActionEvent event) {
        navigateToMail();

    }


}