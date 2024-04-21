/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.assurance;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Devis;

import java.io.IOException;
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


    }



}