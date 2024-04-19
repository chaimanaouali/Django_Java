package services;

import java.sql.SQLException;
import java.util.List;

// Assuming CRUD operations are basic and IService is an extension with more functionalities
public interface CRUD<T> {
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> recuperer() throws SQLException;
}
