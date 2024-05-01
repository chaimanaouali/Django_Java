package Services;
import Models.mecanicien;
import Utils.MyDB;
import Models.evaluation;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
public class ServiceEvaluation  implements Crud<evaluation> {
    private Connection cnx;

    public ServiceEvaluation() {
        cnx = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(evaluation evaluation) throws SQLException {
        String req = "INSERT INTO evaluation (id, mecanicien_id, nom_client, prenom_client, avis_client, date_eval) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1, evaluation.getId());
        pre.setInt(2, evaluation.getMecanicien_id());
        pre.setString(3, evaluation.getNom_client());
        pre.setString(4, evaluation.getPrenom_client());
        pre.setString(5, evaluation.getAvis_client());
        pre.setDate(6, Date.valueOf(evaluation.getDate_eval()));
        pre.executeUpdate();
    }

    @Override
    public void modifier(evaluation evaluation) throws SQLException {
        String req = "UPDATE evaluation SET mecanicien_id=?, nom_client=?, prenom_client=?, avis_client=?, date_eval=? WHERE id=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1, evaluation.getMecanicien_id());
        pre.setString(2, evaluation.getNom_client());
        pre.setString(3, evaluation.getPrenom_client());
        pre.setString(4, evaluation.getAvis_client());
        pre.setDate(5, Date.valueOf(evaluation.getDate_eval()));
        pre.setInt(6, evaluation.getId());
        pre.executeUpdate();

    }

    @Override
    public void supprimer(evaluation evaluation) throws SQLException {
        String req = "DELETE FROM evaluation WHERE id = ?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1, evaluation.getId());
        pre.executeUpdate();
    }

    @Override
    public List<evaluation> afficher() throws SQLException {

        String req = "SELECT * FROM evaluation";

        //String req = "SELECT * FROM evaluation";
        List<evaluation> list = new ArrayList<>();

        try (Statement ste = cnx.createStatement();
             ResultSet res = ste.executeQuery(req)) {

            while (res.next()) {
                evaluation E = new evaluation();
                E.setId(res.getInt(1));
                E.setMecanicien_id(res.getInt(2));
                E.setNom_client(res.getString(3));
                E.setPrenom_client(res.getString(4));
                E.setAvis_client(res.getString(5));
                Date dateSql = res.getDate(6);
                LocalDate dateEval = dateSql.toLocalDate();
                E.setDate_eval(dateEval);

                list.add(E);
            }
        } catch (SQLException ex) {
            // Gérer les exceptions appropriées ici, telles que les journaux ou le renvoi d'une liste vide
            ex.printStackTrace();
            // Vous pouvez également lancer l'exception vers le code appelant si nécessaire
            throw ex;
        }

        return list;
    }


    public List<evaluation> rechercher(String rechercheText, Connection cnx) throws SQLException {
        String sql = "SELECT * FROM evaluation WHERE id LIKE ? OR mecanicien_id LIKE ? OR nom_client LIKE ? OR prenom_client LIKE ? OR avis_client LIKE ? OR date_eval LIKE ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setString(1, "%" + rechercheText + "%");
        preparedStatement.setString(2, "%" + rechercheText + "%");
        preparedStatement.setString(3, "%" + rechercheText + "%");
        preparedStatement.setString(4, "%" + rechercheText + "%");
        preparedStatement.setString(5, "%" + rechercheText + "%");
        preparedStatement.setString(6, "%" + rechercheText + "%");
        ResultSet rs = preparedStatement.executeQuery();

        List<evaluation> evaluations = new ArrayList<>();
        while (rs.next()) {
            evaluation evaluation = new evaluation();
            evaluation.setId(rs.getInt("id"));
            evaluation.setMecanicien_id(rs.getInt("mecanicien_id"));
            evaluation.setNom_client(rs.getString("nom_client"));
            evaluation.setPrenom_client(rs.getString("prenom_client"));
            evaluation.setAvis_client(rs.getString("avis_client"));
            evaluation.setDate_eval(rs.getDate("date_eval").toLocalDate());

            evaluations.add(evaluation);
        }
        return evaluations;
    }
}

