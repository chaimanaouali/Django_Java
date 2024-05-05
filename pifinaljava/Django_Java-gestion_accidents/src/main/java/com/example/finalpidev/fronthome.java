package com.example.finalpidev;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;

import javafx.scene.Parent;
        import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
        import models.constat;
        import Services.ServiceConstat;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.geometry.Insets;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.GridPane;
        import javafx.scene.layout.Pane;

        import java.io.ByteArrayInputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.net.URL;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.ResourceBundle;

public class fronthome {
    @FXML
    private AnchorPane mainHome;

    @FXML
    private GridPane pgrid;

    private List<constat> constats;

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
                fxmlLoader.setLocation(getClass().getResource("/com/example/finalpidev/itemconstat.fxml"));
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



}
