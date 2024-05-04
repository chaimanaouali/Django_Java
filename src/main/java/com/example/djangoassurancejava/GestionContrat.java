/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.djangoassurancejava;
import javafx.embed.swing.SwingFXUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Contrat;
import javafx.scene.control.TableView;
import models.Type;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.ServiceContrat;

import services.ServiceExcel;
import services.ServiceType;

import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


/**
 * FXML Controller class
 *
 * @author chaima
 */
public class GestionContrat implements Initializable {

    @FXML
    private TextField adresseTextField;

    @FXML
    private Button boutonAjouter;

    @FXML
    private Button boutonAjouter1;

    @FXML
    private DatePicker datedebutDatePicker;

    @FXML
    private DatePicker datefinDatePicker;

    @FXML
    private Label decisionCol;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label documentCol1;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label etatCol;

    @FXML
    private Label idCol;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField numeroTextField;

    @FXML
    private Label numtelCol11111;

    @FXML
    private TextField prenomTextField;

    @FXML
    private TextField recherchee;

    @FXML
    private ComboBox<String> typedecouvertureChoiceBox;

    @FXML
    private TextField typedecouvertureTextField;

    @FXML
    private VBox vboxDevis;

    @FXML
    private VBox vboxDevis1;
    @FXML
    private Label nomCol;

    @FXML
    private Label numtelCol1111;



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
    private TextField tfPrenom1;

    @FXML
    private TextField tfPrix;
    private List<Popup> currentPopups = new ArrayList<>();

    @FXML
    private TextField tfPuissance;

    @FXML
    private TextField tfPuissance1;
    @FXML
    private TableView<Contrat> TypeTableView;
    @FXML
    private Button pdf;
    @FXML
    private Button reclamationBt;
    @FXML
    private Button excelbt;
    private Mail mail;

    public static final String ACCOUNT_SID = "AC39da85bd72354e8b0a1846684abd93c9";
    public static final String AUTH_TOKEN = "a1ba9db8d81d810255ac2cbecca9421f";
    static { Twilio.init(ACCOUNT_SID, AUTH_TOKEN); }
    public ServiceExcel excelService = new ServiceExcel();
    public ServiceContrat serviceContrat = new ServiceContrat();


    @FXML
    public void writeToExcel(ActionEvent event) throws SQLException{
        List<Contrat> contrats=serviceContrat.recuperer();
        excelService.writeToExcel(contrats,"C:/Users/Lenovo/Desktop/zahra/Contrats.xlsx");

    }

    @FXML
    public void insertOne(ActionEvent event) throws SQLException {
        LocalDate dated = datedebutDatePicker.getValue();
        LocalDate datef = datefinDatePicker.getValue();
        String adress_assur = adresseTextField.getText();
        String numero = numeroTextField.getText();
        String nom = nomTextField.getText();
        String prenom = prenomTextField.getText();
        String email = emailTextField.getText();
        String selecteddescription = (String) typedecouvertureChoiceBox.getValue();

        // Retrieve and find the corresponding type ID
        ServiceType sty = new ServiceType();
        List<Type> tt = sty.recuperer();
        int selectedDesc = 1;

        for (Type t : tt) {
            if (t.getDescription().equals(selecteddescription)) {
                selectedDesc = t.getId();
                break;
            }
        }
        clearPopups();  // Clear any existing popups

        // Validation regex patterns
        String nameRegex = "^[a-z]+$";
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String phoneRegex = "^[529][0-9]{7}$";

        try {
            // Check for required fields
            boolean valid = true;
            if (dated == null || datef == null || adress_assur.isEmpty() || numero.isEmpty() ||
                    nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || selecteddescription == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
                return;
            }

            // Validate name and surname
            if (!nom.matches(nameRegex)) {
                showErrorPopup(nomTextField, "Le nom doit contenir uniquement des lettres.");
                valid = false;
            }
            if (!prenom.matches(nameRegex)) {
                showErrorPopup(prenomTextField, "Le prénom doit contenir uniquement des lettres.");
                valid = false;
            }

            // Validate email
            if (!email.matches(emailRegex)) {
                showErrorPopup(emailTextField, "L'adresse email n'est pas valide.");
                valid = false;
            }

            // Validate phone number
            if (!numero.matches(phoneRegex)) {
                showErrorPopup(numeroTextField, "Le numéro de téléphone doit commencer par 5, 2 ou 9 et contenir exactement 8 chiffres.");
                valid = false;
            }

            // Check date order
            if (datef.isBefore(dated)) {
                showErrorPopup(datefinDatePicker, "La date de fin doit être après la date de début.");
                valid = false;
            }

            if (!valid) {
                return;
            }

            // Proceed with creating a contract object and adding to the database
            Contrat contrat = new Contrat(selectedDesc, dated, datef, adress_assur, numero, nom, prenom, email);
            ServiceContrat st = new ServiceContrat();
            st.ajouter(contrat);

            //email
            //sendRegistrationEmail(emailTextField.getText());

            //sms

            String recipientPhoneNumber = "+21658128366";
            String messageBody = "Anew contract has been added succesfully !";
            sendSMS(recipientPhoneNumber, messageBody);

            afficherDevis();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Contrat ajouté avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du contrat : " + e.getMessage());
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
        ServiceContrat serviceDevis = new ServiceContrat(); // Creating an instance of ServiceDevis
        List<Contrat> deviss = null; // Calling selectAll() on the instance
        try {
            deviss = serviceDevis.recherche(charr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        vboxDevis.getChildren().clear();
        ServiceContrat sd = new ServiceContrat();

        HBox hb[]= new HBox[deviss.size()];
        for (Contrat d: deviss
        ) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("itemContrat.fxml"));
                Parent root = loader.load();
                itemContrat otherController = loader.getController();
                HBox hbox = (HBox) loader.getRoot();
                vboxDevis.getChildren().add(hbox);


                otherController.afficher(d);
                hbox.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) { // Double-click detected
                        Contrat selectedDevis = d;
                        if (selectedDevis != null) {
                            // Navigate to UpdateUser.fxml
                            try {
                                navigateToUpdateReponseDevis(selectedDevis);
                            } catch (SQLException e) {

                            }
                            afficherDevis();
                        }
                    }
                });

                Button deleteButton = otherController.getDeleteButton();
                int id = otherController.getId();
                Button btqr = otherController.getbtqr();
                int ide = otherController.getEmailId();
                btqr.setOnAction(evt -> {

                    try {
                        System.out.println(ide);
                        Type dee = sd.rechercheId(ide);
                        System.out.println(ide);
                        System.out.println(dee);
                        String AllInfo = "Type Couverture: " + dee.getType_couverture() + "\nDescription:"+ dee.getDescription() ;
                        qr(AllInfo);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    // Remove the HBox from the VBox when the button is clicked

                });
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
        String selectedtypedecouverture = typedecouvertureTextField.getText();
        String selecteddescription = descriptionTextField.getText();

        // Regex to match only letters (and spaces to allow multi-word entries)

        // Check if any field is empty
        if (selectedtypedecouverture.isEmpty() || selecteddescription.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        // Check if the fields contain only letters (and spaces

        // Create a new Type object with retrieved values
        Type type = new Type(selecteddescription, selectedtypedecouverture);
        ServiceType st = new ServiceType();

        try {
            st.ajouter(type);
            System.out.println("Type added successfully.");
            afficherReponseDevis();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Type ajouté avec succès.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to add type: " + e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du type : " + e.getMessage());
        }
    }

    private void showErrorPopup(Control field, String message) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label(message);
        label.setStyle("-fx-background-color: white; -fx-text-fill: red; -fx-padding: 5px; -fx-border-color: red; -fx-border-width: 2px;");
        popup.getContent().add(label);
        Bounds bounds = field.localToScreen(field.getBoundsInLocal());
        popup.show(field, bounds.getMinX() - 5, bounds.getMaxY());
        currentPopups.add(popup);
    }

    private void clearPopups() {
        for (Popup popup : currentPopups) {
            if (popup.isShowing()) {
                popup.hide();
            }
        }
        currentPopups.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceType sc = new ServiceType();
        List<Type> t = null;
        try {
            t = sc.recuperer();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        List<String> desc = new ArrayList<>();
        for (Type type: t
        ) {
            desc.add(type.getDescription());
        }
        for (String e:desc
        ) {
            typedecouvertureChoiceBox.getItems().add(e);
        }
        typedecouvertureChoiceBox.getSelectionModel().selectFirst();

        afficherDevis();
        afficherReponseDevis();
        ServiceContrat ds = new ServiceContrat();
        List<Contrat> dd = null;
        try {
            dd = ds.recuperer();

            //TypeTableView.setItems();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> Emails = new ArrayList<>();
        for (Contrat d: dd
        ) {
          //  Emails.add(d);

        }
        System.out.println(Emails);
        for (String e: Emails
        ) {
            System.out.println("dddd");
        //    tfEmail1.getItems().add(e);
        }
      //  tfEmail1.getSelectionModel().selectFirst();
    }


    public void deleteDevis(int id) {
        try {
            // Set look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Customizing the UIManager to set the default background and foreground color
            UIManager.put("OptionPane.background", Color.WHITE); // Background color
            UIManager.put("Panel.background", Color.WHITE); // Panel background color
            UIManager.put("OptionPane.messageForeground", Color.BLACK); // Foreground (text) color
            UIManager.put("Button.background", Color.WHITE); // Button background color
            UIManager.put("Button.foreground", new Color(55, 97, 155)); // Button text color
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Affichage de la boîte de dialogue de confirmation
        int choice = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce contrat ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        // Vérification de la réponse de l'utilisateur
        if (choice == JOptionPane.YES_OPTION) {
            ServiceContrat st = new ServiceContrat();
            try {
                st.supprimer(id);
                System.out.println("Contrat deleted successfully.");
                afficherDevis();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Failed to delete Contrat: " + e.getMessage());
            }
        } else {
            System.out.println("Suppression annulée par l'utilisateur.");
        }
    }

    public void deleteReponseDevis(int id) {
        try {
            // Set look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Customizing the UIManager to set the default background and foreground color
            UIManager.put("OptionPane.background", Color.WHITE); // Background color
            UIManager.put("Panel.background", Color.WHITE); // Panel background color
            UIManager.put("OptionPane.messageForeground", Color.BLACK); // Foreground (text) color
            UIManager.put("Button.background", Color.WHITE); // Button background color
            UIManager.put("Button.foreground", new Color(55, 97, 155)); // Button text color
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Affichage de la boîte de dialogue de confirmation
        int choice = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce type de contrat ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        // Vérification de la réponse de l'utilisateur
        if (choice == JOptionPane.YES_OPTION) {
            ServiceType st = new ServiceType();
            try {
                st.supprimer(id);
                System.out.println("Type deleted successfully.");
                afficherReponseDevis();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Failed to delete type: " + e.getMessage());
            }
        } else {
            System.out.println("Suppression annulée par l'utilisateur.");
        }
    }
public void afficherReponseDevis(){
        vboxDevis1.getChildren().clear();
    ServiceType sd = new ServiceType();
    try {
        List<Type>deviss = sd.recuperer();
        HBox hb[]= new HBox[deviss.size()];
        for (Type d: deviss
        ) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("itemType.fxml"));
                Parent root = loader.load();
                itemType otherController = loader.getController();
                HBox hbox = (HBox) loader.getRoot();
                vboxDevis1.getChildren().add(hbox);


                otherController.afficher(d);
                hbox.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) { // Double-click detected
                        Type selectedDevis = d;
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
                    deleteReponseDevis(id);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}

    public void afficherDevis(){
        vboxDevis.getChildren().clear();
        ServiceContrat sd = new ServiceContrat();
        try {
            List<Contrat>deviss = sd.recuperer();
            HBox hb[]= new HBox[deviss.size()];
            for (Contrat d: deviss
            ) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("itemContrat.fxml"));
                    Parent root = loader.load();
                    itemContrat otherController = loader.getController();
                    HBox hbox = (HBox) loader.getRoot();
                    vboxDevis.getChildren().add(hbox);


                    otherController.afficher(d);

                    hbox.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) { // Double-click detected
                            Contrat selectedDevis = d;
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

                    ServiceType dd =new ServiceType();
                    Button btqr = otherController.getbtqr();
                    int ide = otherController.getEmailId();
                    btqr.setOnAction(event -> {
                        Type dee = null;
                        try {
                            System.out.println(ide);

                            dee = dd.rechercheId(ide );
                            System.out.println(ide);
                            System.out.println(dee);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        // Remove the HBox from the VBox when the button is clicked
                           String AllInfo = "Type Couverture: " + dee.getType_couverture() + "\nDescription:"+ dee.getDescription() ;
                           qr(AllInfo);
                    });
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


    private void navigateToUpdateDevis(Type devis) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateType.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            UpdateType controller = loader.getController();
            controller.initData(devis);





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

    private void navigateToUpdateReponseDevis(Contrat devis) throws SQLException {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateContrat.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            UpdateContrat controller = loader.getController();
            controller.initData(devis);


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

    @FXML
    void pdf(ActionEvent event) {
        try {
            ServiceContrat sc = new ServiceContrat();
            List<Contrat> data = sc.recuperer();
            PDDocument document = new PDDocument();

            try {
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Set a custom font
                PDFont fontBold = PDType1Font.HELVETICA_BOLD;
                PDFont fontRegular = PDType1Font.HELVETICA;

                contentStream.beginText();
                contentStream.setFont(fontBold, 14);
                contentStream.setLeading(20f);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Contrat Details");
                contentStream.newLine();

                // Reset to regular font for contract details
                contentStream.setFont(fontRegular, 12);

                for (Contrat ca : data) {
                    contentStream.showText("Nom: " + ca.getNom());
                    contentStream.newLine();

                    contentStream.showText("Prenom: " + ca.getPrenom());
                    contentStream.newLine();

                    contentStream.showText("Email: " + ca.getEmail());
                    contentStream.newLine();

                    contentStream.showText("Adresse: " + ca.getAdresse_assur());
                    contentStream.newLine();

                    contentStream.showText("Numero: " + ca.getNumero_assur());
                    contentStream.newLine();

                    contentStream.showText("Type de couverture: " + ca.getType_couverture_id());
                    contentStream.newLine();

                    contentStream.showText("Date debut du contrat: " + ca.getDate_debut_contrat());
                    contentStream.newLine();

                    contentStream.showText("Date fin du contrat: " + ca.getDatefin_contrat());
                    contentStream.newLine();

                    contentStream.newLine();
                    contentStream.newLine();
                }

                contentStream.endText();
                contentStream.close();

                // Save and open the document
                String outputPath = "C:/Users/Lenovo/Desktop/zahra/contrat.pdf";
                File file = new File(outputPath);
                document.save(file);
                document.close();

                // Show a success popup
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Le PDF a été généré avec succès.");
                alert.showAndWait();

                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Failed to create PDF");
                errorAlert.setContentText("An error occurred while creating the PDF: " + e.getMessage());
                errorAlert.showAndWait();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Failed to retrieve data");
            errorAlert.setContentText("An error occurred while retrieving data from the database: " + e.getMessage());
            errorAlert.showAndWait();
            e.printStackTrace();
        }
    }

    public static void sendSMS(String recipientPhoneNumber, String messageBody) {
        String twilioPhoneNumber = "+12679532826";
        Message message = Message.creator(
                new PhoneNumber(recipientPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                messageBody)
                .create();
        System.out.println("SMS sent successfully. SID: " + message.getSid());
    }
    /*private void sendRegistrationEmail(String recipientEmail) {
        String subject = "Welcome to Our Application!";
        String body = "Thank you for registering with our Django desktop application. Your account has been successfully created.";

        // Send email
        MailingService.sendMail(recipientEmail, subject, body);
    }*/
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
