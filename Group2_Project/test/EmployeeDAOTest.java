import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class EmployeeDAOTest {
    private EmployeeDAO employeeDAO;

    @Before
    public void setup() throws SQLException {
        // Initialize the database schema before each test
        SchemaManager.initializeSchema(); 
        // Create the DAO instance to test
        employeeDAO = new EmployeeDAO(); 
    }

	@Test
    public void populateTestDatabase() throws SQLException {
        // 1. Add Multiple Employees
        Map<String, String> emp1 = new LinkedHashMap<>();
        emp1.put("first_name", "John");
        emp1.put("last_name", "Doe");
        emp1.put("salary", "75000.00");
        emp1.put("job_title", "Software Engineer");
        emp1.put("division", "IT");
        emp1.put("fullTime", "1");
        employeeDAO.insertEmployee(emp1);

        Map<String, String> emp2 = new LinkedHashMap<>();
        emp2.put("first_name", "Jane");
        emp2.put("last_name", "Smith");
        emp2.put("salary", "82000.00");
        emp2.put("job_title", "Project Manager");
        emp2.put("division", "Operations");
        emp2.put("fullTime", "1");
        employeeDAO.insertEmployee(emp2);

        Map<String, String> emp3 = new LinkedHashMap<>();
        emp3.put("first_name", "Bob");
        emp3.put("last_name", "Johnson");
        emp3.put("salary", "45000.00");
        emp3.put("job_title", "Technician");
        emp3.put("division", "IT");
        emp3.put("fullTime", "0");
        employeeDAO.insertEmployee(emp3);

        // 2. Add Multiple Pay Statements
        // Linking PayStatements to Employee ID 1 (John Doe)
        PayStatement ps1 = new PayStatement(1, 3125.00, LocalDate.of(2023, 11, 1), LocalDate.of(2023, 11, 15));
        PayStatement ps2 = new PayStatement(1, 3125.00, LocalDate.of(2023, 11, 16), LocalDate.of(2023, 11, 30));
        
        // Linking PayStatement to Employee ID 2 (Jane Smith)
        PayStatement ps3 = new PayStatement(2, 3416.67, LocalDate.of(2023, 11, 1), LocalDate.of(2023, 11, 30));

        employeeDAO.addPayStatement(1, ps1);
        employeeDAO.addPayStatement(1, ps2);
        employeeDAO.addPayStatement(2, ps3);

        // 3. Verify Data Presence
        assertTrue("Database should have employees", employeeDAO.employeeExists(1));
        System.out.println("Test database populated successfully with 3 employees and 3 pay statements.");
    }

	@Test
    public void testInsertEmployee() throws SQLException {
        Map<String, String> testData = new LinkedHashMap<>();
        testData.put("first_name", "John");
        testData.put("last_name", "Doe");
        testData.put("salary", "55000.00");
        testData.put("job_title", "Developer");
        testData.put("division", "IT");
        testData.put("fullTime", "1"); // 1 for true in many SQL dialects

        // Test the insertion logic
        employeeDAO.insertEmployee(testData); 

        // Verify the employee now exists in the database
        assertTrue("Employee should exist after insertion", employeeDAO.employeeExists(1)); 
    }

    @Test
    public void testSelectEmployee() throws SQLException {
        // First, ensure there is data to select
        Map<String, String> testData = new HashMap<>();
        testData.put("first_name", "Alice");
        testData.put("last_name", "Smith");
        employeeDAO.insertEmployee(testData);

        // Test retrieving the employee by a specific column
        ResultSet rs = employeeDAO.selectEmployee("first_name", "Alice"); 
        
        assertTrue("ResultSet should have data", rs.next());
        assertEquals("Alice", rs.getString("first_name"));
    }

    @Test
    public void testDeleteEmployee() throws SQLException {
        // Ensure an employee exists to delete
        Map<String, String> testData = new HashMap<>();
        testData.put("first_name", "Temporary");
        employeeDAO.insertEmployee(testData);

        // Verify deletion logic
        boolean deleted = employeeDAO.deleteEmployee(1); 
        
        assertTrue("Delete operation should return true", deleted);
        assertFalse("Employee should no longer exist", employeeDAO.employeeExists(1));
    }

    private void assertEquals(String alice, String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void assertTrue(String resultSet_should_have_data, boolean next) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}