/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.user;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Contrat;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author zahra
 */
public class itemContrat implements Initializable {
    @FXML
    private Label debutCol;
    @FXML
    private Button btqr;
    @FXML
    private Label finCol;

    @FXML
    private Label adresseCol;

    @FXML
    private Label numeroCol;

    @FXML
    private Label nomCol;

    @FXML
    private Label emailCol;

    @FXML
    private Label prenomCol;

    @FXML
    private Label idCol;
    @FXML
    private Label idtypeCol;
    @FXML
    private ImageView typeCol;
    @FXML
    private Button deleteB;
    public Button getDeleteButton(){
        return deleteB;
    }
    public int getId(){
        System.out.println(Integer.valueOf(idCol.getText()));
        return Integer.valueOf(idCol.getText());
    }

    public Button getbtqr(){
        return btqr;
    }
    public int getEmailId(){
        System.out.println(Integer.valueOf(idtypeCol.getText()));
        return Integer.valueOf(idtypeCol.getText());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        }

    public void afficher (Contrat d) {

        this.debutCol.setText(d.getDate_debut_contrat().toString());
        this.finCol.setText(d.getDatefin_contrat().toString());
        this.adresseCol.setText(d.getAdresse_assur());
        this.numeroCol.setText(d.getNumero_assur());
        this.nomCol.setText(d.getNom());
        this.prenomCol.setText(d.getPrenom());
        this.emailCol.setText(d.getEmail());
        Image brandingImage = new Image(getClass().getResource("/screenshotqr/screenshot.png").toString());

        this.typeCol.setImage(brandingImage);
        this.idtypeCol.setText(Integer.toString(d.getType_couverture_id()));
        this.idCol.setText(Integer.toString(d.getId()));

    }



}