package services;

import models.Devis;
import utils.DBconnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceDevis implements  CRUD<Devis>  {

    private Connection cnx;

    public ServiceDevis() {
        cnx = DBconnection.getInstance().getCnx();
    }


    @Override
    public void insertOne(Devis devis) throws SQLException {


        String req = "INSERT INTO `devis`(`nom`, `prenom`, `adresse`,`email`, `date_naiss`, `num_tel`,`modele`, `puissance`, `prix`) VALUES " +
                "(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, devis.getNom());
            ps.setString(2, devis.getPrenom());
            ps.setString(3, devis.getAdresse());
            ps.setString(4, devis.getEmail());
            ps.setObject(5, devis.getDate_naiss());
            ps.setInt(6, devis.getNum_tel());
            ps.setString(7, devis.getModele());
            ps.setString(8, devis.getPuissance());
            ps.setDouble(9, devis.getPrix());

            // Execute the prepared statement without passing the SQL query
            ps.executeUpdate();
            System.out.println("Devis Added!");
        }

    }

    @Override
    public void updateOne(Devis devis) throws SQLException {
        String req = "UPDATE `devis` SET `nom`=?, `prenom`=?, `adresse`=?, `email`=?, `date_naiss`=?, `num_tel`=?, `modele`=?, `puissance`=?, `prix`=? WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, devis.getNom());
            ps.setString(2, devis.getPrenom());
            ps.setString(3, devis.getAdresse());
            ps.setString(4, devis.getEmail());
            ps.setObject(5, devis.getDate_naiss());
            ps.setInt(6, devis.getNum_tel());
            ps.setString(7, devis.getModele());
            ps.setString(8, devis.getPuissance());
            ps.setDouble(9, devis.getPrix());
            ps.setDouble(10, devis.getId());
            ps.executeUpdate();
            System.out.println("Devis updated!");
        }
    }


    @Override
    public void deleteOne(Integer id) throws SQLException {
        String req = "DELETE FROM `devis` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Use the nom attribute from the Devis object
            ps.executeUpdate();
            System.out.println("Devis deleted!");
        }
    }

    @Override
    public List<Devis> selectAll() throws SQLException {
        List<Devis> devisList = new ArrayList<>();
        String query = "SELECT * FROM devis ";
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String adresse = resultSet.getString("adresse");
            String email = resultSet.getString("email");
            LocalDate date_naiss = resultSet.getDate("date_naiss").toLocalDate();
            int num_tel = resultSet.getInt("num_tel");
            String modele = resultSet.getString("modele");
            String puissance = resultSet.getString("puissance");
            double prix = resultSet.getDouble("prix");

            Devis devis = new Devis(id, nom, prenom, adresse, email, date_naiss, num_tel, modele, puissance, prix);
            devisList.add(devis);
        }
        return devisList;
    }
    @Override
    public List<Devis> rechercheF(String vari) throws SQLException {
        List<Devis> devisList = new ArrayList<>();
        System.out.println(vari);
        String query = "SELECT * FROM devis where nom LIKE '"+vari+"%' OR prenom LIKE '"+vari+"%' OR adresse LIKE '"+vari+"%' OR email LIKE '"+vari+"%' OR modele LIKE '"+vari+"%'";
        System.out.println(query);
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String adresse = resultSet.getString("adresse");
            String email = resultSet.getString("email");
            LocalDate date_naiss = resultSet.getDate("date_naiss").toLocalDate();
            int num_tel = resultSet.getInt("num_tel");
            String modele = resultSet.getString("modele");
            String puissance = resultSet.getString("puissance");
            double prix = resultSet.getDouble("prix");

            Devis devis = new Devis(id, nom, prenom, adresse, email, date_naiss, num_tel, modele, puissance, prix);
            devisList.add(devis);
        }
        return devisList;
    }
}
