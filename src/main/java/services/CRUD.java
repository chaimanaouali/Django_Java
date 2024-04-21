package services;

import models.Devis;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {

    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(Integer t) throws SQLException;
    List<T> selectAll() throws SQLException;
    List<T> rechercheF(String vari) throws SQLException;
}
