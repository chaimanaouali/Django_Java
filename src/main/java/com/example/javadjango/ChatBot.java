package com.example.javadjango;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ChatBot {
    @FXML
    private TextArea chatArea;

    @FXML
    private TextField inputField;
    @FXML
    void reponsechat(ActionEvent event) {

            // Récupérer le texte saisi dans le champ de texte
            String userInput = inputField.getText();

            // Générer une réponse en fonction de l'entrée de l'utilisateur
            String response = getResponse(userInput);

            // Afficher la saisie de l'utilisateur et la réponse du chatbot dans la zone de chat
            chatArea.appendText("You: " + userInput + "\n");
            chatArea.appendText("ChatBot: " + response + "\n");

            // Effacer le champ de texte après l'envoi du message
            inputField.clear();
        }
    private String getResponse(String input) {
        input = input.toLowerCase();
        switch (input) {
            case "comment se déroule la consultation en ligne ?":
                return "The online consultation is conducted via secure video conference.";

            case "quels sont les médecins disponibles pour une consultation en ligne ?":
                return "Currently, our available doctors for online consultations are Dr. Smith and Dr. Dupont.";

            case "comment puis-je payer pour la consultation en ligne ?":
                return "Once your appointment is confirmed, you will receive a payment link.";

            case "comment puis-je annuler ou reporter mon rendez-vous en ligne ?":
                return "You can cancel or reschedule your online appointment by contacting us directly by phone or email.";

            case "mes informations médicales seront-elles sécurisées ?":
                return "Absolutely. We use advanced technologies to protect your confidentiality.";

            default:
                return "I'm sorry, I didn't understand that.";
        }
    }

    }

