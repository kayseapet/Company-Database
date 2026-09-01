import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection 
{
    private static final String DB_HOST = "localhost:3306";
    private static final String DB_NAME = "employee_db";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST +"/"+ DB_NAME;
    private static final String DB_URL_WITHOUT_DB = "jdbc:mysql://" + DB_HOST;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Alias2017"; //enter personal password here

    private static Connection connection;

    //Loading the  Driver
    static 
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Ensure MySQL driver is loaded
        } 
        catch (ClassNotFoundException e) 
        {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    public boolean createDatabaseIfNotExists() 
    {
        try (Connection conn = DriverManager.getConnection(DB_URL_WITHOUT_DB, USERNAME, PASSWORD); Statement stmt = conn.createStatement()) {

            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DB_NAME +
                    " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
            stmt.executeUpdate(createDatabaseSQL);

            System.out.println("Database '" + DB_NAME + "' is ready.");
            return true;

        } 
        catch (SQLException e) 
        {
            System.err.println("Error creating database: " + e.getMessage());
            System.err.println("Please ensure MySQL is running and credentials are correct.");
            return false;
        }
    }

    public static Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public void disconnect() 
    {
        try 
        {
            if (connection != null && !connection.isClosed()) 
            {
                connection.close();
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
