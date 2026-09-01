import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;


//class that intializes the database, and also chnages the employee table.
public class SchemaManager {

    //making the database for the first time
    public static boolean initializeSchema() {
        //Creating Employee Table
        String createEmployeeTable = """
        CREATE TABLE IF NOT EXISTS employee (
            emp_id INT PRIMARY KEY AUTO_INCREMENT,
            first_name VARCHAR(50) NOT NULL,
            last_name VARCHAR(50) NOT NULL,
            salary DECIMAL(10, 2),
            job_title VARCHAR(100),
            division VARCHAR(50),
            fullTime BOOLEAN NOT NULL
        );
        """;

        
        //creating payStatement Table
        String createPayTable = """
        CREATE TABLE IF NOT EXISTS pay_statement (
            id INT PRIMARY KEY AUTO_INCREMENT,
            emp_id INT NOT NULL,
            amount DECIMAL(10, 2) NOT NULL,
            start_date DATE,
            end_date DATE,
            FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
        );
        """;
                
        try (Connection conn = DBConnection.getConnection();Statement stmt = conn.createStatement()) 
        {
            //execute the strings
            stmt.execute(createEmployeeTable);
            stmt.execute(createPayTable);

            System.out.println("Database tables created successfully");
            return true;
        }
        catch (SQLException e)
        {
            System.err.println("Error creating tables: " + e.getMessage());
            return false;
            
        }
    }


    //For the Employee table given, function checks if a given column exists based on the Column Name
    public static Boolean doesColExist(String colName) 
    {
        // Use the standard DatabaseMetaData approach which is more robust for H2
        try (Connection conn = DBConnection.getConnection()) 
        {
            DatabaseMetaData metaData = conn.getMetaData();
            // H2 expects table and column names in UPPERCASE in metadata queries
            try (ResultSet rs = metaData.getColumns(null, null, "EMPLOYEE", colName.toUpperCase())) 
            {
                return rs.next(); // Returns true if the column exists
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error checking schema: " + e.getMessage());
            return false;
        }
    }

    public static Map<String, String> getEmployeeSchema()  throws SQLException {
        Map<String, String> employeeDetails = new LinkedHashMap<>();
        String tableName = "employee";

        DatabaseMetaData metaData;
        try {
            metaData = DBConnection.getConnection().getMetaData();
            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            while (columns.next()) 
            {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");

                // Exclude emp_id from editable fields
                if(!columnName.equals("emp_id")) { // Exclude emp_id from editable fields
                    employeeDetails.put(columnName, columnType);
                }
            }
            return employeeDetails;
        } 
        catch (SQLException e) {
            System.err.println("Error retrieving employee details: " + e.getMessage());
            throw e; // Rethrow the exception for further handling
        }
    }

    public static boolean addCol(String colName, String colType) {
        // Check if it already exists first to avoid the "Duplicate column" error
        if (doesColExist(colName)) {
            System.out.println("Column " + colName + " already exists. Skipping.");
            return true; // Return true because the desired state (column exists) is met
        }

        String alterTableSQL = String.format("ALTER TABLE employee ADD COLUMN %s %s", colName, colType);
        try (Connection conn = DBConnection.getConnection(); 
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(alterTableSQL);
            System.out.println("Column " + colName + " added successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding column: " + e.getMessage());
            return false;
        }
    }
}
