package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    private final String URL = "jdbc:mysql://localhost:4306/assurance";
    private final String USER = "root";
    private final String PWD = "";

    private Connection connection;

    //3 varibale pour stocker l'instance
    private static MyDB instance;

    //1 rendre le constructeur privee
    private   MyDB(){
        try {
            connection = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("connected to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage()); //methode tebaa connection
        }
    }

    //2 de creer une methode static pour utiliser le constructeur
    public static MyDB getInstance(){
        if(instance==null){
            instance = new MyDB();
        } return instance;

    }

    public Connection getConnection() {
        return connection;
    }
}
