package services;

import models.Contrat;
import models.Type;
import utils.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceType implements CRUD2<Type> {
    private Connection cnx;

    public ServiceType() {
        cnx = DBconnection.getInstance().getCnx();
    }

    public Type findTypeByDescription(String description) {
        Type type = null;
        // Assuming "type" is the correct table name and "description" is the column to match against.
        String req = "SELECT * FROM type WHERE description = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, description);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                type = new Type();
                // Assuming these are the correct column names in your "type" table.
                type.setType_couverture(rs.getString("type_couverture"));
                type.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider more sophisticated error handling.
        }
        return type;
    }

    @Override
    public void ajouter(Type type) throws SQLException {
        String req = "INSERT INTO type (description, type_couverture) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, type.getDescription());
            ps.setString(2, type.getType_couverture());
            ps.executeUpdate();
         // Try-with-resources ensures that PreparedStatement is closed after use
    }

    @Override
    public void modifier(Type type) throws SQLException {
        String req = "UPDATE type SET description = ?, type_couverture = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, type.getDescription());
            ps.setString(2, type.getType_couverture());
            ps.setInt(3, type.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM type WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Type> recuperer() throws SQLException {
        List<Type> types = new ArrayList<>();
        String req = "SELECT * FROM type";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Type type = new Type();
                type.setId(rs.getInt("id"));
                type.setDescription(rs.getString("description"));
                type.setType_couverture(rs.getString("type_couverture"));
                types.add(type);
            }
        } // Try-with-resources ensures that Statement and ResultSet are closed after use
        return types;
    }
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
        return 0;
    }

    @Override
    public List<Contrat> recherche(String desc) throws SQLException {
        return null;
    }


}
