    package com.example.finalpidev;

    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javax.mail.MessagingException;

    import java.io.File;
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.net.URL;
    import java.sql.*;
    import java.util.Properties;
    import java.util.ResourceBundle;

    import java.io.File;
    import java.io.IOException;
    import java.sql.SQLException;
    import java.time.LocalDateTime;
    import java.util.Properties;

    import javafx.stage.FileChooser;
    import javafx.stage.Stage;
    import models.constat;
    import Services.ServiceConstat;

    import javax.mail.*;
    import javax.mail.internet.AddressException;
    import javax.mail.internet.InternetAddress;
    import javax.mail.internet.MimeMessage;
    import java.net.URL;



    public class AjouterConstat {

        ServiceConstat sc=new ServiceConstat();
        private int i;

        @FXML
        private Label option;


        @FXML
        private TextField desctf;
        @FXML
        private TextField emailtf;
        @FXML
        private Label photopath;

        @FXML
        private TextField lieutf;

        @FXML
        private RadioButton nonradio;

        @FXML
        private RadioButton ouiradio;

        @FXML
        private SplitMenuButton splitmenutf;



        public void initialize() {
            // Add items to the SplitMenuButton
            MenuItem Rg = new MenuItem("Route glissante");
            MenuItem Mt = new MenuItem("Méteo");
            MenuItem Tr = new MenuItem("Travaux routiers");
            MenuItem  Fv= new MenuItem("Faible Visibilité");

            splitmenutf.getItems().addAll(Rg,Mt,Tr,Fv);

            Rg.setOnAction(event -> handleOption1(event));
            Mt.setOnAction(event1 -> handleOption2(event1));
            Tr.setOnAction(event2 -> handleOption3(event2));
            Fv.setOnAction(event3 -> handleOption4(event3));




        }




        private void handleOption1(ActionEvent event) {
            option.setText("Route glissante");
            splitmenutf.setText("Route glissante");
        }

        private void handleOption2(ActionEvent event1) {
            option.setText("Méteo");
            splitmenutf.setText("Méteo");
        }

        private void handleOption3(ActionEvent event2) {
            option.setText("Travaux Routiers");
            splitmenutf.setText("Travaux Routiers");
        }

        private void handleOption4(ActionEvent event3) {
            option.setText("Faible Visibilité");
            splitmenutf.setText("Faible Visibilité");
        }




    private void sendEmail(String toEmail, String msg) {
        final String username = "yassinkhelefi@gmail.com";
        final String password = "bnlynnqtdwuyosek";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Reclamation En Succes");
            message.setText(msg);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
        @FXML
        void ajouterC(ActionEvent event) {
            try {
                String lieu = lieutf.getText();
                String description = desctf.getText();
                String photoPath = photopath.getText();
                String EMAIL = emailtf.getText();


                if (lieu.isEmpty() || description.isEmpty() || photoPath.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Contrôle de saisie");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez remplir tous les champs obligatoires!");
                    alert.showAndWait();
                    return;
                }


                if (description.length() <= 10) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Contrôle de saisie");
                    alert.setHeaderText(null);
                    alert.setContentText("La description doit contenir plus de 10 caractères!");
                    alert.showAndWait();
                    return;
                }

                boolean isOuiSelected = ouiradio.isSelected();
                System.out.println("Oui selected: " + isOuiSelected);
                int rapport = (isOuiSelected) ? 1 : 0;
                System.out.println("Rapport value before object creation: " + rapport);
                constat c = new constat(LocalDateTime.now(), lieu, description, photoPath, option.getText(), rapport,EMAIL);
                sc.create(c);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La création a réussi!");
                String msg = "Votre reclamation a été enregistré.\n\n" +
                        "Détails du rendez-vous :\n" +
                        "Nous avons hâte de vous repondre bientot !";
                sendEmail("arij.laatigue@esprit.tn", msg);


                // Envoi de l'email avec le message contenant les détails du rendez-vous

                alert.showAndWait();
                lieutf.clear();
                desctf.clear();

            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                String e1 = e.getMessage();
                alert.setContentText(e1);
                alert.showAndWait();
            }

        }






        @FXML
        void photobutt(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Media File");
            File selectedFile = fileChooser.showOpenDialog(lieutf.getScene().getWindow());

            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                photopath.setText(filePath);
            }
        }

        @FXML
        private void affconstat() {
            // Close the current stage
            Stage currentStage = (Stage) lieutf.getScene().getWindow();
            currentStage.close();

            // Open the new stage with AffichageConstat.fxml
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageConstat.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
