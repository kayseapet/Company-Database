import java.sql.*;


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
            job_title_id INT NOT NULL,
            fullTime BIT NOT NULL,
            FOREIGN KEY (job_title_id) REFERENCES job_title(id)
        );
        """;

        // Creating Job Title Table
        String createJobTable= """
        CREATE TABLE IF NOT EXISTS job_title (
            id INT PRIMARY KEY AUTO_INCREMENT,
            title VARCHAR(50) NOT NULL,
            division_id INT NOT NULL,
            FOREIGN KEY (division_id) REFERENCES division(id),
            UNIQUE (title, division_id)  
        );
        """;

        // Creating Division TAble
        String createDivisionTable= """
        CREATE TABLE IF NOT EXISTS division (
            id INT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(50) NOT NULL UNIQUE
        );
        """;
        
        //creating payStatement Table
        String createPayTable = """
        CREATE TABLE IF NOT EXISTS pay_statement (
            id INT PRIMARY KEY AUTO_INCREMENT,
            employee_id INT NOT NULL,
            amount DECIMAL(10, 2) NOT NULL,
            start_date DATE,
            end_date DATE,
            FOREIGN KEY (emp_id) REFERENCES employees(emp_id) ON DELETE CASCADE
        );
        """;
                
        try (Connection conn = DBConnection.getConnection();Statement stmt = conn.createStatement()) 
        {
            //execute the strings
            stmt.execute(createEmployeeTable);
            stmt.execute(createJobTable);
            stmt.execute(createDivisionTable);
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


    //Specifically for the Employee table, functions check if a given column exist based on the Column exists
    public static void doesColExist(String name) 
    {
        String checkQuery = String.format("SHOW COLUMNS FROM employee LIKE '%s'",name);
    }
}
