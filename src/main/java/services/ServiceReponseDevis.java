package services;

import models.ReponseDevis;
import utils.DBconnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import models.ReponseDevis;
public class ServiceReponseDevis implements  CRUDReponse<ReponseDevis>  {

    private Connection cnx;

    public ServiceReponseDevis() {
        cnx = DBconnection.getInstance().getCnx();
    }


    @Override
    public void insertOne(ReponseDevis rdevis) throws SQLException {


        String req = "INSERT INTO `reponse_devis`(`etat`, `decision`, `date_reglement`,`delai_reparation`, `duree_validite`, `documents`,`email_id`) VALUES " +
                "(?,?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, rdevis.getEtat());
            ps.setString(2, rdevis.getDecision());
            ps.setString(4, rdevis.getDelai_reparation());
            ps.setString(5, rdevis.getDuree_validite());
            ps.setObject(3, rdevis.getDate_reglement());
            ps.setString(6, rdevis.getDocuments());
            ps.setInt(7, rdevis.getEmail_id());

            // Execute the prepared statement without passing the SQL query
            ps.executeUpdate();
            System.out.println("ReponseDevis Added!");
        }

    }

    @Override
    public void updateOne(ReponseDevis devis) throws SQLException {
        String req = "UPDATE `reponse_devis` SET `etat`=?, `decision`=?, `date_reglement`=?, `delai_reparation`=?, `duree_validite`=?, `documents`=?, `email_id`=? WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, devis.getEtat());
            ps.setString(2, devis.getDecision());
            ps.setObject(3, devis.getDate_reglement());
            ps.setString(4, devis.getDelai_reparation());
            ps.setString(5, devis.getDuree_validite());
            ps.setString(6, devis.getDocuments());
            ps.setInt(7, devis.getEmail_id());

            ps.setInt(8, devis.getId());
            ps.executeUpdate();
            System.out.println("ReponseDevis updated!");
        }
    }


    @Override
    public void deleteOne(Integer id) throws SQLException {
        String req = "DELETE FROM `reponse_devis` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id); // Use the nom attribute from the Devis object
            ps.executeUpdate();
            System.out.println("ReponseDevis deleted!");
        }
    }

    @Override
    public List<ReponseDevis> selectAll() throws SQLException {
        List<ReponseDevis> devisList = new ArrayList<>();
        String query = "SELECT r.* ,d.email FROM reponse_devis as r , devis as d where r.email_id= d.id ";
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String etat = resultSet.getString("etat");
            String decision = resultSet.getString("decision");
            String delai = resultSet.getString("delai_reparation");
            String dureeRep = resultSet.getString("duree_validite");
            LocalDate date_reglement = resultSet.getDate("date_reglement").toLocalDate();
            int email_id = resultSet.getInt("email_id");
            String documents = resultSet.getString("documents");
            String email = resultSet.getString("email");


            ReponseDevis devis = new ReponseDevis(id,email_id ,etat, decision, delai, dureeRep, documents, date_reglement);
           devis.setEmail(email);
            devisList.add(devis);
        }
        return devisList;
    }
}
