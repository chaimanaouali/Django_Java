/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.assurance;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.ReponseDevis;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author chaima
 */
public class itemReponseDevis implements Initializable {
    @FXML
    private Label decisionCol;

    @FXML
    private Label delaiCol;

    @FXML
    private Button deleteB;
    @FXML
    private Button btqr;

    @FXML
    private Label documentCol;

    @FXML
    private Label dureeCol;

    @FXML
    private ImageView emailCol;

    @FXML
    private Label etatCol;

    @FXML
    private Label idCol;
    @FXML
    private Label idmail;

    @FXML
    private Label reglementCol;

    public Button getDeleteButton(){
        return deleteB;
    }
    public Button getbtqr(){
        return btqr;
    }
    public int getId(){
        System.out.println(Integer.valueOf(idCol.getText()));
        return Integer.valueOf(idCol.getText());
    }
    public int getEmailId(){
        System.out.println(Integer.valueOf(idmail.getText()));
        return Integer.valueOf(idmail.getText());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        }

    public void afficher (ReponseDevis d) {

        this.etatCol.setText(d.getEtat());
        this.decisionCol.setText(d.getDecision());

        this.dureeCol.setText(d.getDuree_validite());
        Image brandingImage = new Image(getClass().getResource("/screenshotqr/screenshot.png").toString());

this.emailCol.setImage(brandingImage);
        this.delaiCol.setText(d.getDelai_reparation());
        this.reglementCol.setText(d.getDate_reglement().toString());
        this.documentCol.setText(d.getDocuments());
        this.idCol.setText(Integer.toString(d.getId()));
this.idmail.setText(Integer.toString(d.getEmail_id()));
    }



}