package data;

import java.sql.*;

public class DbProvider {
    private static final String DB_DRIVER ="com.mysql.cj.jdbc.Driver";
    private static final String DB_HOST ="jdbc:mysql://localhost:3306/ecommerce-webapp";
    private static final String DB_USER ="root"; // root is the default user
    private static final String DB_PASS ="root"; // root is the default password

    // This method will return a connection object to the database
    public static Connection getMySqlConnection()
            throws ClassNotFoundException, SQLException{
        Class.forName(DB_DRIVER);

        return DriverManager.getConnection(DB_HOST, DB_USER, DB_PASS);

    }
}
