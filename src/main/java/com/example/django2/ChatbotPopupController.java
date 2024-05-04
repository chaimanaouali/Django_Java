package com.example.django2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Post;
import service.ServicePost;

import java.sql.SQLException;
import java.util.Arrays;
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

    private List<Post> allPosts;


    private final String[] predefinedQuestions = {
            "what is your name?",
            "how old are you?",
            "where are you from?"
            // Add the new question
    };

    private final String[] predefinedResponses = {
            "My name is Chatbot.",
            "I am a chatbot, I don't have an age.",
            "I exist in the digital world!",
            "There are currently " + countNumberOfPosts() + " posts.",
            "My hobby is chatting with you!"
    };
    private final String[] newResponses = {
            "Yes, you can customize your policy to fit your needs and budget.",
            "We offer a range of coverage options, including liability, collision, comprehensive, and more.",
            "There are currently " + countNumberOfPosts() + " posts.",
            "My hobby is chatting with you!",
            "Liability insurance covers damages you cause to others' property or injuries in an accident.",
            "The cost of car insurance is affected by factors such as age, driving record, and location.",
            "Comprehensive coverage pays for damages not caused by a collision, such as theft, vandalism, or natural disasters. Collision coverage pays for damages caused by a collision with another vehicle or object.",
            "You can lower your car insurance premium by maintaining a clean driving record, bundling policies, and taking advantage of discounts offered by insurance companies."
    };

    private final String[] newQuestions = {
            "Can I customize my car insurance policy?",
            "How do I file a claim?",
            "how many posts are there?",
            "what is your hobby?",
            "What does liability insurance cover?",
            "What factors affect the cost of car insurance?",
            "What is the difference between comprehensive and collision coverage?",
            "How can I lower my car insurance premium?"
    };

    private int countNumberOfPosts() {
        try {
            // Access the list of posts from the ServicePost class
            ServicePost servicePost = new ServicePost();
            allPosts = servicePost.selectAll();
            return allPosts.size();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException
            return -1; // or throw an exception
        }
    }

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

    private void changeQuestions(String askedQuestion) {
        // Check if the asked question is one of the predefined questions
        for (int i = 0; i < predefinedQuestions.length; i++) {
            if (askedQuestion.equalsIgnoreCase(predefinedQuestions[i])) {
                // Choose a random new question
                int randomIndex = (int) (Math.random() * newQuestions.length);
                String newQuestion = newQuestions[randomIndex];

                // Update the predefined questions and responses
                predefinedQuestions[i] = newQuestion;
                predefinedResponses[i] = newResponses[randomIndex];

                // Update the displayed questions
                chatArea.appendText("some question you want to ask :\n");
                for (String question : predefinedQuestions) {
                    chatArea.appendText("- " + question + "\n");
                }
                break; // Exit the loop once the question is updated
            }
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

                // Update the questions if a predefined question is asked
                changeQuestions(predefinedQuestions[i]);

                // Clear user input field and return
                userInputField.clear();
                return;
            }
        }

        // Check if the user's message starts with "give me the description of"
        if (userMessage.toLowerCase().startsWith("give me the description of ")) {
            // Extract the post title from the user's message
            String postTitle = userMessage.substring("give me the description of ".length());

            // Search for the post with the extracted title
            for (Post post : allPosts) {
                if (post.getTitre().equalsIgnoreCase(postTitle.trim())) {
                    // Display the post description
                    chatArea.appendText("Chatbot: " + post.getDescription() + "\n");

                    // Clear user input field
                    userInputField.clear();
                    return;
                }
            }

            // If the post title is not found, inform the user
            chatArea.appendText("Chatbot: Post with title '" + postTitle + "' not found.\n");

            // Clear user input field
            userInputField.clear();
            return;
        }

        // Check if the user's message asks for the most liked post
        if (userMessage.equalsIgnoreCase("give me the most liked post")) {
            // Find the post with the most likes
            Post mostLikedPost = findMostLikedPost();

            if (mostLikedPost != null) {
                // Display the title of the most liked post
                chatArea.appendText("Chatbot: The most liked post is '" + mostLikedPost.getTitre() + "'.\n");
            } else {
                // Inform the user if no post is found
                chatArea.appendText("Chatbot: No post found.\n");
            }

            // Clear user input field
            userInputField.clear();
            return;
        }


        // If the user's message doesn't match any predefined question or post title, ask for more details
        chatArea.appendText("Chatbot: Je n'ai pas compris. Pouvez-vous donner plus de détails?\n");

        // Clear user input field
        userInputField.clear();
    }

    private Post findMostLikedPost() {
        // Find the post with the most likes
        Post mostLikedPost = null;
        int maxLikes = -1;

        for (Post post : allPosts) {
            if (post.getLike_count() > maxLikes) {
                mostLikedPost = post;
                maxLikes = post.getLike_count();
            }
        }

        return mostLikedPost;
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
