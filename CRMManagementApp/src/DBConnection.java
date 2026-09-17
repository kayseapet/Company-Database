//Class that gets a connection to an SQL Database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    //URL that tells H@ to create a file in the project root
    private static final String DB_URL ="jdbc:h2:./employee_db;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Connection connection;
    static
    {
        try
        {
            //load H2 Driver
            Class.forName("org.h2.Driver");
        }
        catch(ClassNotFoundException e)
        {
            System.err.println("H2 Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

   
    // Method to close the connection
    public static void disconnect() throws SQLException {
        try {
            // check if the connection exists and is currently open
            if (connection != null && !connection.isClosed()) {
                connection.close();
                // set to null so the next getConnection() call 
                // knows it needs to create a brand new connection.
                connection = null; 
                System.out.println("Disconnected from H2 database.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing H2 database connection: " + e.getMessage());
            throw e; // rethrow to let the caller (like a JUnit test) know it failed
        }
    }
}
