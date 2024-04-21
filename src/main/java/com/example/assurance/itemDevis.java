/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.assurance;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.Devis;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author chaima
 */
public class itemDevis implements Initializable {
    @FXML
    private Label adresseCol;

    @FXML
    private Label datenaissCol;

    @FXML
    private Label emailCol;

    @FXML
    private Label modeleCol1;

    @FXML
    private Label nomCol;

    @FXML
    private Label numtelCol1111;

    @FXML
    private Label prenomCol;

    @FXML
    private Label prixCol111;

    @FXML
    private Label puissanceCol11;
    @FXML
    private Label idCol;
    @FXML
    private Button deleteB;
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

    public void afficher (Devis d) {

        this.nomCol.setText(d.getNom());
        this.prenomCol.setText(d.getPrenom());
        this.modeleCol1.setText(d.getModele());
        this.puissanceCol11.setText(d.getPuissance());
        this.prixCol111.setText(Double.toString(d.getPrix()));
        this.numtelCol1111.setText(Integer.toString(d.getNum_tel()));
        this.emailCol.setText(d.getEmail());
        this.datenaissCol.setText(d.getDate_naiss().toString());
        this.adresseCol.setText(d.getAdresse());
        this.idCol.setText(Integer.toString(d.getId()));

    }



}