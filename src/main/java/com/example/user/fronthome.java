package com.example.user;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import services.ServiceConstat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.constat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class fronthome {
    @FXML
    private AnchorPane mainHome;

    @FXML
    private GridPane pgrid;

    private List<constat> constats;
    @FXML
    private Button homeBt;

    ServiceConstat sc;


    public void initialize() {
        sc = new ServiceConstat();
        try {

            constats =sc.read();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int column=0;
        int row= 1;
        try {
            for (constat c: constats){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("itemconstat.fxml"));
                AnchorPane pane =fxmlLoader.load();
                itemconstat itemconstat=fxmlLoader.getController();
                itemconstat.setData(c);
                if(column==3){
                    column=0;
                    ++row;
                }
                pgrid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(20));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public void ajouterfront(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterConstat.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    @FXML
    void homeButtonOnAction(ActionEvent event){

        Stage stage = (Stage) homeBt.getScene().getWindow();
        stage.close();
        // Navigate to the login window
        navigateToHome();    }
    private void navigateToHome() {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home2.fxml"));
            javafx.scene.Parent root = loader.load();

            // Access the controller and pass the selected user to it
            Home2 controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
