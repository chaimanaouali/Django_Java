package services;

import models.Devis;
import models.Voiture;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void insertOne(T t) throws SQLException;

    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException;
    List<T> rechercheF(String vari) throws SQLException;
    Devis rechercheId(int idd) throws SQLException;
    void deleteOne(Integer t) throws SQLException;


}
