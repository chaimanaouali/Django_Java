package com.example.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static javafx.application.Application.launch;


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

    // Method to generate response based on user input
    private String getResponse(String input) {
        input = input.toLowerCase();

        switch (input) {
            case "hello":
            case "hi":
                return "Hello there!";
            case "how are you?":
                return "I'm just a chatbot, but thanks for asking!";
            case "i need help":
                return "Of course.";
            case "est ce que il ya des mecaniciens zone tunis":
                return "Oui bien sur Arbi Houssem";
            case "est ce que il ya des mecaniciens zone beja":
                return "Non desole";
            case "est ce que il ya des mecaniciens zone sousse":
                return "Oui bien sur Chemakh Skander";
            case "est ce que il ya des mecaniciens zone Gabes":
                return "Oui bien sur Dorki Salim";
            case "bye":
                return "Goodbye!";
            case "bonjour":
                return "bonjour!";
            case "j ai besoin d aide":
                return "Oui bien sur je suis la !";

            default:
                return "Desole je compremds pas.";
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}

