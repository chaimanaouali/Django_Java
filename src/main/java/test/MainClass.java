package test;

import models.Devis;
import services.ServiceDevis;
import utils.DBconnection;

import java.time.LocalDate;
import java.util.Date;

import java.sql.SQLException;

public class MainClass {
    public static void main(String[] args) {
        DBconnection cn1 = DBconnection.getInstance();

        LocalDate date_naiss = LocalDate.of(2021, 2, 15);
        Devis d = new Devis("aaaa", "bhbh", "ooooo", "iiiiii", date_naiss, 58038168, "mercedes", "15cv", 220000);

        d.setId(18); // Assuming the ID is 1 for demonstration

        ServiceDevis sp = new ServiceDevis();

        try {
            sp.insertOne(d);
            System.out.println(sp.selectAll());
            d.setNom("melek");
            sp.updateOne(d);

        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }

    }
}
