package services;

import models.Contrat;
import models.Type;
import utils.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceContrat implements CRUD2<Contrat> {
    private Connection cnx;

    public ServiceContrat() {
        cnx = DBconnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Contrat contrat) throws SQLException {
        String req = "INSERT INTO contrat (type_couverture_id,date_debut_contrat,datefin_contrat,adresse_assur,numero_assur,nom,prenom,email) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, contrat.getType_couverture_id());
        ps.setObject(2, contrat.getDate_debut_contrat());
        ps.setObject(3, contrat.getDatefin_contrat());
        ps.setString(4, contrat.getAdresse_assur());
        ps.setString(5, contrat.getNumero_assur());
        ps.setString(6, contrat.getNom());
        ps.setString(7, contrat.getPrenom());
        ps.setString(8, contrat.getEmail());

        ps.executeUpdate();
        // Try-with-resources ensures that PreparedStatement is closed after use
    }

    @Override
    public void modifier(Contrat contrat) throws SQLException {
        String req = "UPDATE contrat SET type_couverture_id = ?, date_debut_contrat = ?, datefin_contrat = ? , adresse_assur = ?, numero_assur = ?, nom = ?, prenom = ?, email = ? WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, contrat.getType_couverture_id());
            ps.setObject(2, contrat.getDate_debut_contrat());
            ps.setObject(3, contrat.getDatefin_contrat());
            ps.setString(4, contrat.getAdresse_assur());
            ps.setString(5, contrat.getNumero_assur());
            ps.setString(6, contrat.getNom());
            ps.setString(7, contrat.getPrenom());
            ps.setString(8, contrat.getEmail());
            ps.setInt(9, contrat.getId());
            System.out.println(ps);
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM contrat WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Contrat> recuperer() throws SQLException {
        List<Contrat> contrats = new ArrayList<>();
        String req = "SELECT c.*, t.type_couverture FROM `contrat`as c , `type` as t WHERE (c.type_couverture_id = t.id)";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Contrat contrat = new Contrat();
                contrat.setId(rs.getInt("id"));
                contrat.setType_couverture_id(rs.getInt("type_couverture_id"));
                contrat.setDate_debut_contrat( rs.getDate("date_debut_contrat").toLocalDate());
                contrat.setDatefin_contrat(rs.getDate("datefin_contrat").toLocalDate());
                contrat.setAdresse_assur(rs.getString("adresse_assur"));
                contrat.setNumero_assur(rs.getString("numero_assur"));
                contrat.setNom(rs.getString("nom"));
                contrat.setPrenom(rs.getString("prenom"));
                contrat.setEmail(rs.getString("email"));
                contrat.setType_couverture(rs.getString("type_couverture"));
                contrats.add(contrat);
            }
        } // Try-with-resources ensures that Statement and ResultSet are closed after use
        return contrats;
    }

    @Override
    public Type rechercheId(int id) throws SQLException {
        String req = "SELECT * FROM type where id =" + id;
        Type type = null;
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                type = new Type();
                type.setId(rs.getInt("id"));
                type.setDescription(rs.getString("description"));
                type.setType_couverture(rs.getString("type_couverture"));

            }
        } // Try-with-resources ensures that Statement and ResultSet are closed after use
        return type;
    }

    @Override
    public int rechercheId(String desc) throws SQLException {
        List<Contrat> contrats = new ArrayList<>();
        String req = "SELECT id FROM `type` WHERE description = '" + desc +"'";
        System.out.println(req);
        Type contrat = null;
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                contrat = new Type();
                contrat.setId(rs.getInt("id"));

            }
        } // Try-with-resources ensures that Statement and ResultSet are closed after use
        return contrat.getId();


    }

    @Override
    public List<Contrat> recherche(String desc) throws SQLException {
        String req = "SELECT * FROM contrat where nom LIKE '" + desc + "%' OR prenom LIKE '" + desc + "%' OR adresse_assur LIKE '" + desc + "%' OR email LIKE '" + desc + "%' ";
        Contrat contrat = null;
        List<Contrat> contrats = new ArrayList<>();
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                contrat = new Contrat();
                contrat.setId(rs.getInt("id"));
                contrat.setType_couverture_id(rs.getInt("type_couverture_id"));
                contrat.setDate_debut_contrat(rs.getDate("date_debut_contrat").toLocalDate());
                contrat.setDatefin_contrat(rs.getDate("datefin_contrat").toLocalDate());
                contrat.setAdresse_assur(rs.getString("adresse_assur"));
                contrat.setNumero_assur(rs.getString("numero_assur"));
                contrat.setNom(rs.getString("nom"));
                contrat.setPrenom(rs.getString("prenom"));
                contrat.setEmail(rs.getString("email"));
                //contrat.setType_couverture(rs.getString("type_couverture"));
                contrats.add(contrat);
            }
        } // Try-with-resources ensures that Statement and ResultSet are closed after use
        return contrats;
    }

}

