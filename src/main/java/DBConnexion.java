import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnexion {
    private  static final String URL ="jdbc:mysql://localhost:3306/SGB";
    private static  final String USER ="root" ;
    private  static  final  String PASSWORD = "" ;

    private static Connection connection = null;


    private DBConnexion() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
               // System.out.println("Connexion à la base de données réussie !");
            } catch (SQLException e) {
                System.err.println("Erreur de connexion : " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}



