package Services;

import com.example.finalpidev.RelatedRecordsException;
import models.constat;
import utils.MyDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import models.traitement;

public class ServiceTraitement implements IService<traitement> {
    private Connection cnn;

    public ServiceTraitement() {
        cnn = MyDB.getInstance().getConnection();
    }

    @Override
    public void create(traitement traitement) throws SQLException {
        String request = "INSERT INTO traitement (id, date_taitement, responsable ,statut, remarque) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = cnn.prepareStatement(request);

        // Set the values using the appropriate methods of the traitement object
        preparedStatement.setInt(1, traitement.getId());
        preparedStatement.setObject(2, traitement.getDate_traitement());
        preparedStatement.setString(3, traitement.getResponsable());
        preparedStatement.setString(4, traitement.getStatut());
        preparedStatement.setString(5, traitement.getRemarque());


        // Execute the query
        preparedStatement.executeUpdate();

        // Close the PreparedStatement (optional but recommended)
        preparedStatement.close();
    }

    @Override
    public void delete(traitement t) throws SQLException, RelatedRecordsException {
        try {
            // Delete the traitement record
            PreparedStatement preparedStatement = cnn.prepareStatement("DELETE FROM traitement WHERE id=?");
            preparedStatement.setInt(1, t.getId());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle the foreign key constraint violation
            throw new RelatedRecordsException("Cannot delete traitement record due to existing related records.");
        }
    }

    @Override
    public List<traitement> read() throws SQLException {
        String request = "SELECT * FROM traitement";
        Statement ste = cnn.createStatement();

        ResultSet res = ste.executeQuery(request);
        List<traitement> list = new ArrayList<>();
        while (res.next()) {
            traitement tr = new traitement();
            tr.setId(res.getInt(1));
            LocalDate date = res.getDate(2).toLocalDate(); // Get the date part
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT); // Combine with midnight time
            tr.setDate_traitement(dateTime);
            tr.setStatut(res.getString(3));
            tr.setRemarque(res.getString(4));
            tr.setResponsable(res.getString(5));
            list.add(tr);
        }
        return list;}

    @Override
    public List<constat> Tri(String recherche) {
        return null;
    }

    @Override
    public List<constat> Tri() {
        return null;
    }

    @Override
    public List<constat> Rechreche(String recherche) {
        return null;
    }

    @Override
    public void update(traitement traitement) throws SQLException {
        String request = "UPDATE traitement SET date_taitement=?, statut=?, remarque=?, responsable=? WHERE id=?";
        PreparedStatement pre = cnn.prepareStatement(request);
        pre.setObject(1, traitement.getDate_traitement());
        pre.setString(2, traitement.getStatut());
        pre.setString(3, traitement.getRemarque());
        pre.setString(4, traitement.getResponsable());
        pre.setInt(5, traitement.getId());
        pre.executeUpdate();
    }
}
