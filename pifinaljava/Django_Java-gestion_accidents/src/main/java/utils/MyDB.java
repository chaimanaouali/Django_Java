package utils;
import java.sql.*;

public class MyDB {

    private final String URL = "jdbc:mysql://localhost:3306/djangoassurance";
    private final String USER = "root";
    private final String PWD = "";
    private Connection connection;

    private static MyDB instance;

    public MyDB(){
        try {
            connection = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("connected to BD");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static MyDB getInstance(){
        if (instance == null){
            instance = new MyDB();
        }return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public class DBConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/votre_base_de_donnees";
        private static final String USER = "votre_utilisateur";
        private static final String PASSWORD = "votre_mot_de_passe";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        public static void closeConnection(Connection conn) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // Méthode surchargée pour fermer la connexion avec PreparedStatement et ResultSet
        public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) {
            closeConnection(conn);

            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) {
    }
}
