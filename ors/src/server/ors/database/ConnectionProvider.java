package server.ors.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
    public static Connection getCon() {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ors", "root", "q1w2e3r4");
            return con;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
