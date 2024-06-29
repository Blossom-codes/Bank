import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db{
    public Connection connect() throws SQLException, ClassNotFoundException
    {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String username = "root";
        String password = "Blozzy200120";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

}
