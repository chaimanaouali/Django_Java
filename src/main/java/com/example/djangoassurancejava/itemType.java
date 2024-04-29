/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.djangoassurancejava;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import models.Type;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author zahra
 */
public class itemType implements Initializable {
    @FXML
    private Label typeCol;

    @FXML
    private Label descriptionCol;
    @FXML
    private Label idCol;

    @FXML
    private Button deleteB;
    @FXML
    private Button btqr;





    public Button getDeleteButton(){
        return deleteB;
    }

    public int getId(){
        System.out.println(Integer.valueOf(idCol.getText()));
        return Integer.valueOf(idCol.getText());
    }
    public int getEmailId(){
        System.out.println(Integer.valueOf(idCol.getText()));
        return Integer.valueOf(idCol.getText());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        }

    public void afficher (Type d) {

        this.typeCol.setText(d.getType_couverture());
        this.descriptionCol.setText(d.getDescription());


        this.idCol.setText(Integer.toString(d.getId()));

    }



}