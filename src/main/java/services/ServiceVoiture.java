package services;

import models.Devis;
import models.User;
import models.Voiture;
import utils.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceVoiture implements CRUD<Voiture> {

    private Connection cnx ;
    public ServiceVoiture() {
        cnx = DBconnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Voiture voiture) throws SQLException {
        String req = "INSERT INTO `voiture`(`puissance`, `matricule`, `marque` , `prix_voiture` , `email_id`) VALUES " +
                "(?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, voiture.getPuissance());
        ps.setString(2, voiture.getMatricule());
        ps.setString(3, voiture.getMarque());
        ps.setFloat(4, voiture.getPrix_voiture());
        ps.setInt(5, voiture.getUser().getId());

        // Execute the prepared statement without passing the SQL query string again
        ps.executeUpdate();
    }

    @Override
    public void updateOne(Voiture voiture) throws SQLException {
        String sql = "UPDATE voiture SET puissance=?, matricule=?, marque=?, prix_voiture=?, email_id=? WHERE id=?";



        try (PreparedStatement statement = cnx.prepareStatement(sql)) {

            statement.setInt(1, voiture.getPuissance());
            statement.setString(2, voiture.getMatricule());
            statement.setString(3, voiture.getMarque());
            statement.setFloat(4, voiture.getPrix_voiture());
            statement.setInt(5, voiture.getUser().getId());
            statement.setInt(6, voiture.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("Voiture not found, update failed.");
            } else {
                System.out.println("Voiture updated successfully.");
            }
        }
    }

    public void deleteOne(Voiture voiture) throws SQLException {
        String sql = "DELETE FROM voiture WHERE id=?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, voiture.getId());

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                System.out.println("voiture not found, delete failed.");
            } else {
                System.out.println("voiture deleted successfully.");
            }
        }
    }






    @Override
    public List<Voiture> selectAll() throws SQLException {
        List<Voiture> voitureList = new ArrayList<>();

        String req = "SELECT v.id as id_voiture, v.matricule, v.marque, v.prix_voiture, v.puissance, u.id as id_user, u.email, u.roles, u.nom_user, u.prenom_user FROM `voiture` as v JOIN `user` as u ON u.id = v.email_id";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Voiture v = new Voiture();

            v.setId(rs.getInt("id_voiture"));
            v.setPuissance(rs.getInt("puissance"));
            v.setMartricule(rs.getString("matricule"));
            v.setMarque(rs.getString("marque"));
            v.setPrix_voiture(rs.getFloat("prix_voiture"));

            User user = new User();
            user.setId(rs.getInt("id_user"));
            user.setEmail(rs.getString("email"));
            user.setRoles(rs.getString("roles"));
            user.setNom_user(rs.getString("nom_user"));
            user.setPrenom_user(rs.getString("prenom_user"));

            v.setUser(user);

            voitureList.add(v);
        }

        return voitureList;
    }

    @Override
    public List<Voiture> rechercheF(String vari) throws SQLException {
        return null;
    }

    @Override
    public Devis rechercheId(int idd) throws SQLException {
        return null;
    }

    @Override
    public void deleteOne(Integer t) throws SQLException {

    }

    public List<Voiture> selectVoitureByUser(User u) throws SQLException {
        List<Voiture> voitureList = new ArrayList<>();

        String req = "SELECT v.id as id_voiture, v.matricule, v.marque, v.prix_voiture, v.puissance, u.id as id_user, u.email, u.roles, u.nom_user, u.prenom_user FROM `voiture` as v JOIN `user` as u ON u.id = v.email_id WHERE v.email_id = "+ u.getId();
        System.out.println(req);
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Voiture v = new Voiture();

            v.setId(rs.getInt("id_voiture"));
            v.setPuissance(rs.getInt("puissance"));
            v.setMartricule(rs.getString("matricule"));
            v.setMarque(rs.getString("marque"));
            v.setPrix_voiture(rs.getFloat("prix_voiture"));

            User user = new User();
            user.setId(rs.getInt("id_user"));
            user.setEmail(rs.getString("email"));
            user.setRoles(rs.getString("roles"));
            user.setNom_user(rs.getString("nom_user"));
            user.setPrenom_user(rs.getString("prenom_user"));

            v.setUser(user);

            voitureList.add(v);
        }

        return voitureList;
    }


}
