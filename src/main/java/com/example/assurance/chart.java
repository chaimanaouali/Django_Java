/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.assurance;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Devis;
import services.ServiceDevis;


/**
 * FXML Controller class
 *
 * @author chaima
 */
public class chart implements Initializable {

    private PieChart piechart;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private Button back;
    @FXML
    private NumberAxis numberAxis;
    @FXML
    private CategoryAxis xAxis;
    private ObservableList<String> modeles = FXCollections.observableArrayList();
    private int numb = 0;
    /**
     * Initializes the controller class.
     */
    public static List<Integer> getOccurrences(List<String> liste, List<String> listeUnique) {
        List<Integer> occurrences = new ArrayList<>();
        for (String unique : listeUnique) {
            int count = 0;
            for (String str : liste) {
                if (str.equals(unique)) {
                    count++;
                }
            }
            occurrences.add(count);
        }
        return occurrences;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Get an array with the FRENCH month names.
        ServiceDevis sd = new ServiceDevis();
        try {
            List<Devis> dd = sd.selectAll();
            List<String> liste = new ArrayList<>();
            for (Devis d: dd
                 ) {
                liste.add(d.getModele());
            }
            List<String> listeUnique = new ArrayList<>(new HashSet<>(liste)); // Obtient une liste de chaînes uniques
            numb = listeUnique.size();

            // Convert it to a list and add it to our ObservableList of months.
            modeles.addAll(listeUnique);

            // Assign the month names as categories for the horizontal axis.
            xAxis.setCategories(modeles);

            xAxis.setLabel("Modele");
            numberAxis.setLabel("Nombre");




                XYChart.Series<String, Integer> series = new XYChart.Series<>();
                List<Integer> occurrences = getOccurrences(liste, listeUnique);
                // Create a XYChart.Data object for each month. Add it to the series.
                System.out.println(occurrences);
                for (int i = 0; i < numb; i++) {

                    series.getData().add(new XYChart.Data<>(modeles.get(i), occurrences.get(i)));
                }

                barChart.getData().add(series);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    @FXML
    void goBack(ActionEvent ev) {
        try {
            // Load the UpdateUser.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherDevis.fxml"));
            Parent root = loader.load();

            // Access the controller and pass the selected user to it
            afficherDevis controller = loader.getController();


            // Show the scene containing the UpdateUser.fxml file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {

            });
            stage.show();
            Stage gg= (Stage)barChart.getScene().getWindow();
            gg.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}