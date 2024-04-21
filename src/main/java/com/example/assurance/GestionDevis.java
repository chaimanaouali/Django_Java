/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.assurance;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Devis;
import services.ServiceDevis;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author chaima
 */
public class GestionDevis implements Initializable {

    @FXML
    void insertOne(ActionEvent event) {

    }

    @FXML
    void insertOne1(ActionEvent event) {

    }
@FXML
private VBox vboxDevis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        
        }



}