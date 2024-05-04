package services;

import models.mecanicien;
import utils.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceMecanicien implements Crud3<mecanicien> {
    private Connection cnx;

    public ServiceMecanicien() {
        cnx = DBconnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(mecanicien mecanicien) throws SQLException {
        String req = "INSERT INTO mecanicien (id, nom, prenom, localisation, disponibilite,numero,image,email) VALUES (?,?, ?, ?, ?, ?,?,?)";
        PreparedStatement pre = cnx.prepareStatement(req);
       pre.setInt(1, mecanicien.getId());
        pre.setString(2, mecanicien.getNom());
        pre.setString(3, mecanicien.getPrenom());
        pre.setString(4, mecanicien.getLocalisation());
        pre.setString(5, mecanicien.getDisponibilite());
        pre.setString(6, mecanicien.getNumero());
        pre.setString(7, mecanicien.getImage()); // Si vous stockez le chemin de l'image
        // Si vous stockez l'image comme des données binaires, utilisez setBlob ou setBinaryStream
        pre.setString(8, mecanicien.getEmail());
        pre.executeUpdate();

    }

    @Override
    public void modifier(mecanicien mecanicien) throws SQLException {
            // La requête de base sans l'image
        String req = "UPDATE mecanicien SET nom=?, prenom=?, localisation=?, disponibilite=?, numero=?, email=? WHERE id=?";

            // Vérifiez si l'image doit être incluse
            if (mecanicien.getImage() != null) {
                // Ajouter la colonne image dans la requête
                req = "UPDATE mecanicien SET nom=?, prenom=?, localisation=?, disponibilite=?, numero=?, image=?, email=? WHERE id=?";
            }

            PreparedStatement pre = cnx.prepareStatement(req);

            // Set des paramètres de base

            pre.setString(1, mecanicien.getNom());
            pre.setString(2, mecanicien.getPrenom());
            pre.setString(3, mecanicien.getLocalisation());
            pre.setString(4, mecanicien.getDisponibilite());
            pre.setString(5, mecanicien.getNumero());


            int index = 6; // Définir l'index pour le prochain paramètre

            if (mecanicien.getImage() != null) {
                pre.setString(index, mecanicien.getImage());
                index++;
            }

            // Définir l'email et l'identifiant
            pre.setString(index, mecanicien.getEmail());
        pre.setInt(index + 1, mecanicien.getId());

            pre.executeUpdate();
        }



    @Override
    public void supprimer(mecanicien mecanicien) throws SQLException {
        String req = "DELETE FROM mecanicien WHERE id = ?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1, mecanicien.getId());
        pre.executeUpdate();
    }

    @Override
    public List<mecanicien> afficher()  {
        String req = "SELECT * FROM mecanicien";
        List<mecanicien> list = new ArrayList<>();

        try (Statement ste = cnx.createStatement();
             ResultSet res = ste.executeQuery(req)) {

            while (res.next()) {
                mecanicien M = new mecanicien();
                M.setNom(res.getString(1));
                M.setPrenom(res.getString(2));
                M.setLocalisation(res.getString(3));
                M.setDisponibilite(res.getString(4));
                M.setNumero(res.getString(5));
                M.setImage(res.getString(6));
                M.setNumero(res.getString(7));
                M.setEmail(res.getString(8));
                list.add(M);
            }
        } catch (SQLException ex) {
            // Gérer les exceptions appropriées ici, telles que les journaux ou le renvoi d'une liste vide
            ex.printStackTrace();
            // Vous pouvez également lancer l'exception vers le code appelant si nécessaire

        }

        return list;
    }
    public List<mecanicien> rechercher(String rechercheText, Connection cnx) throws SQLException {
        String sql = "SELECT * FROM mecanicien WHERE id LIKE ? OR nom LIKE ? OR prenom LIKE ? OR localisation LIKE ? OR disponibilite LIKE ? OR numero LIKE ? OR image LIKE ? OR email LIKE ?";
        PreparedStatement preparedStatement = cnx.prepareStatement(sql);
        preparedStatement.setString(1, "%" + rechercheText + "%");
        preparedStatement.setString(2, "%" + rechercheText + "%");
        preparedStatement.setString(3, "%" + rechercheText + "%");
        preparedStatement.setString(4, "%" + rechercheText + "%");
        preparedStatement.setString(5, "%" + rechercheText + "%");
        preparedStatement.setString(6, "%" + rechercheText + "%");
        preparedStatement.setString(7, "%" + rechercheText + "%");
        preparedStatement.setString(8, "%" + rechercheText + "%");
        ResultSet rs = preparedStatement.executeQuery();

        List<mecanicien> mecaniciens = new ArrayList<>();
        while (rs.next()) {
            mecanicien mecanicien = new mecanicien();
            mecanicien.setId(rs.getInt("id"));
            mecanicien.setNom(rs.getString("nom"));
            mecanicien.setPrenom(rs.getString("prenom"));
            mecanicien.setLocalisation(rs.getString("localisation"));
            mecanicien.setDisponibilite(rs.getString("disponibilite"));
            mecanicien.setNumero(rs.getString("numero"));
            mecanicien.setImage(rs.getString("image"));
            mecanicien.setEmail(rs.getString("email"));
            mecaniciens.add(mecanicien);
        }
        return mecaniciens;
    }

}


