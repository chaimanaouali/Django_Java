    package com.example.django2;

    import javafx.fxml.FXML;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.TextArea;
    import javafx.scene.control.TextField;
    import javafx.stage.Stage;
    import models.Post;

    import java.sql.SQLException;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    public class ChatbotPopupController {

        @FXML
        private TextField userInputField;

        @FXML
        private TextArea chatArea;

        @FXML
        private Button sendButton;

        @FXML
        private Button[] questionButtons;
        private Set<String> askedQuestions = new HashSet<>();

        private int countNumberOfPosts() {
            try {
                // Access the list of posts from the AfficherPostXML class
                AfficherPostXML afficherPostXML = new AfficherPostXML();
                List<Post> posts = afficherPostXML.getAllPosts();
                return posts.size();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the SQLException
                return -1; // or throw an exception
            }
        }

        // Define predefined questions and responses
        private final String[] predefinedQuestions = {
                "what is your name?",
                "how old are you?",
                "where are you from?",
                "how many posts are there?" // Add the new question
        };

        private final String[] predefinedResponses = {
                "My name is Chatbot.",
                "I am a chatbot, I don't have an age.",
                "I exist in the digital world!",
                "There are currently " + countNumberOfPosts() + " posts." // Update the response
        };

        @FXML
        public void initialize() {
            // Display the introductory message from the chatbot
            chatArea.appendText("Hello! I'm Chatbot. How can I assist you today?\n");

            // Display suggestions of predefined questions
            chatArea.appendText("Here are some questions you can ask:\n");
            for (String question : predefinedQuestions) {
                chatArea.appendText("- " + question + "\n");
            }

            // Create buttons for predefined questions
            questionButtons = new Button[predefinedQuestions.length];
            for (int i = 0; i < predefinedQuestions.length; i++) {
                String question = predefinedQuestions[i];
                Button button = new Button(question);
                final int index = i; // Local variable that is effectively final
                button.setOnAction(event -> {
                    // Display the question in the chat area
                    chatArea.appendText("You: " + question + "\n");
                    // Display the response in the chat area
                    chatArea.appendText("Chatbot: " + predefinedResponses[index] + "\n");
                    // Clear user input field
                    userInputField.clear();
                });
                questionButtons[i] = button;
            }
        }
        @FXML
        void sendMessage() {
            // Get user input
            String userMessage = userInputField.getText().trim();

            // Display user input in the chat area
            chatArea.appendText("You: " + userMessage + "\n");

            // Check if the user's message matches any predefined question
            for (int i = 0; i < predefinedQuestions.length; i++) {
                if (userMessage.equalsIgnoreCase(predefinedQuestions[i])) {
                    // Display predefined response
                    chatArea.appendText("Chatbot: " + predefinedResponses[i] + "\n");

                    // Update the list of asked questions
                    askedQuestions.add(predefinedQuestions[i]);

                    // Suggest other questions
                    chatArea.appendText("Other questions you can ask:\n");
                    for (String question : predefinedQuestions) {
                        if (!askedQuestions.contains(question)) {
                            chatArea.appendText("- " + question + "\n");
                        }
                    }

                    // Clear user input field and return
                    userInputField.clear();
                    return;
                }
            }

            // If the user's message doesn't match any predefined question, ask for more details
            chatArea.appendText("Chatbot: Je n'ai pas compris. Pouvez-vous donner plus de détails?\n");

            // Clear user input field
            userInputField.clear();
        }


        @FXML
        void onClose() {
            // Create and show an alert message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chatbot");
            alert.setHeaderText(null);
            alert.setContentText("Thank you for using Chatbot!");
            alert.showAndWait();

            // Close the popup window
            Stage stage = (Stage) sendButton.getScene().getWindow();
            stage.close();
        }
    }
