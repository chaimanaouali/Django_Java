package services;

import com.example.user.RelatedRecordsException;
import models.constat;
import utils.DBconnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceConstat implements IService<constat> {
    private Connection cnx;

    public ServiceConstat() {
        cnx = DBconnection.getInstance().getCnx();
    }

    @Override
    public void create(constat constat) throws SQLException {
        String request = "INSERT INTO constat (id, date, lieu, description, conditionroute, photo, rapportepolice,email) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        PreparedStatement preparedStatement = cnx.prepareStatement(request);

        // Set the values using the appropriate methods of the constat object
        preparedStatement.setInt(1, constat.getId());
        preparedStatement.setObject(2, constat.getDate());
        preparedStatement.setString(3, constat.getLieu());
        preparedStatement.setString(4, constat.getDescription());
        preparedStatement.setString(5, constat.getConditionroute());
        preparedStatement.setString(6, constat.getPhoto());
        preparedStatement.setInt(7, constat.getRapportpolice());
        preparedStatement.setString(8, constat.getEmail());
        // Execute the query
        preparedStatement.executeUpdate();

        // Close the PreparedStatement (optional but recommended)
        preparedStatement.close();
    }

    @Override
    public void delete(constat c) throws SQLException, RelatedRecordsException {
        try {
            // Delete the constat record
            PreparedStatement preparedStatement = cnx.prepareStatement("DELETE FROM constat WHERE id=?");
            preparedStatement.setInt(1, c.getId());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle the foreign key constraint violation
            throw new RelatedRecordsException("Cannot delete constat record due to existing related records.");
        }
    }



    @Override
    public List<constat> read() throws SQLException {
        String request = "SELECT * FROM constat";
        Statement ste = cnx.createStatement();

        ResultSet res = ste.executeQuery(request);
        List<constat> list = new ArrayList<>();
        while (res.next()) {
            constat sd = new constat();
            sd.setId(res.getInt(1));
            LocalDate date = res.getDate(2).toLocalDate(); // Get the date part
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT); // Combine with midnight time
            sd.setDate(dateTime);
            sd.setLieu(res.getString(3));
            sd.setDescription(res.getString(4));
            sd.setConditionroute(res.getString(5));
            sd.setPhoto(res.getString(7));
            sd.setRapportpolice(res.getInt(6)); // Corrected to getByte for tinyint
            list.add(sd);
        }
        return list;
    }

    @Override
    public List<constat> Tri(String recherche) {
        return null;
    }

    @Override
    public List<constat> Tri() {
        List<constat> constats = new ArrayList<>();
        String sql = "SELECT * FROM constat ORDER BY `lieu`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                constat sd = new constat();
                sd.setId(res.getInt(1));
                LocalDate date = res.getDate(2).toLocalDate(); // Get the date part
                LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT); // Combine with midnight time
                sd.setDate(dateTime);
                sd.setLieu(res.getString(3));
                sd.setDescription(res.getString(4));
                sd.setConditionroute(res.getString(5));
                sd.setPhoto(res.getString(7));
                sd.setRapportpolice(res.getInt(6)); // Corrected to getByte for tinyint
                constats.add(sd);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return constats;
    }
    @Override
    public List<constat> Rechreche(String recherche) {
        List<constat> constats = new ArrayList<>();
        String sql = "SELECT * FROM constat WHERE `date` LIKE '%" + recherche + "%' OR `lieu` LIKE '%" + recherche + "%' OR conditionroute LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(sql);
            while (res.next()) {
                constat sd = new constat();
                sd.setId(res.getInt(1));
                LocalDate date = res.getDate(2).toLocalDate(); // Get the date part
                LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT); // Combine with midnight time
                sd.setDate(dateTime);
                sd.setLieu(res.getString(3));
                sd.setDescription(res.getString(4));
                sd.setConditionroute(res.getString(5));
                sd.setPhoto(res.getString(7));
                sd.setRapportpolice(res.getInt(6)); // Corrected to getByte for tinyint
                constats.add(sd);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return constats;
    }
    @Override
    public void update(constat constat) throws SQLException {
        String request = "UPDATE constat SET lieu=?, description=?, conditionroute=?, photo=?, rapportepolice=? WHERE id=?";
        PreparedStatement pre = cnx.prepareStatement(request);
        pre.setString(1, constat.getLieu());
        pre.setString(2, constat.getDescription());
        pre.setString(3, constat.getConditionroute());
        pre.setString(4, constat.getPhoto());
        pre.setInt(5, constat.getRapportpolice());
        pre.setInt(6, constat.getId());
        pre.executeUpdate();
    }

}
