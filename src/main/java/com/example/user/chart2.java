/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.user;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Contrat;
import services.ServiceContrat;

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
public class chart2 implements Initializable {

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
        ServiceContrat sd = new ServiceContrat();
        try {
            List<Contrat> dd = sd.recuperer();
            List<String> liste = new ArrayList<>();
            for (Contrat d: dd
                 ) {
                liste.add(d.getType_couverture());
            }
            List<String> listeUnique = new ArrayList<>(new HashSet<>(liste)); // Obtient une liste de chaînes uniques
            numb = listeUnique.size();

            // Convert it to a list and add it to our ObservableList of months.
            modeles.addAll(listeUnique);

            // Assign the month names as categories for the horizontal axis.
            xAxis.setCategories(modeles);

            xAxis.setLabel("Type");
            numberAxis.setLabel("Nombre de contrat");




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
        // Load the UpdateUser.fxml file

        Stage gg= (Stage)barChart.getScene().getWindow();
        gg.close();
    }

}