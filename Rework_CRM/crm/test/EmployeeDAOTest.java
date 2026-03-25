
import org.junit.jupiter.api.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDAOTest 
{

    private static EmployeeDAO dao;

    @BeforeAll
    static void setup() throws SQLException 
    {
        // Initialize the database structure first
        SchemaManager.initializeSchema();
        dao = new EmployeeDAO();
    }

    @BeforeEach
    void resetData() throws SQLException {
        try (Statement stmt = dao.getConnection().createStatement()) {
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE"); 
            stmt.execute("TRUNCATE TABLE pay_statement");
            stmt.execute("TRUNCATE TABLE employee");
            
            // IMPORTANT: If you added a column in a previous test run, 
            // you may need to drop it here to keep the test 'idempotent'
            if (SchemaManager.doesColExist("phone_number")) {
                stmt.execute("ALTER TABLE employee DROP COLUMN phone_number");
            }
            
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
            stmt.execute("ALTER TABLE employee ALTER COLUMN emp_id RESTART WITH 1");
            stmt.execute("ALTER TABLE pay_statement ALTER COLUMN id RESTART WITH 1");
        }
    }

    @Test
    @Order(1)
    @DisplayName("Success Case: Insert and Retrieve Employee")
    void testInsertAndSelectEmployee() throws SQLException 
    {
        // ARRANGE
        Map<String, String> data = new LinkedHashMap<>();
        data.put("first_name", "Alice");
        data.put("last_name", "Smith");
        data.put("salary", "75000.00");
        data.put("job_title", "Developer");
        data.put("division", "IT");
        data.put("fullTime", "true");

        // ACT
        dao.insertEmployee(data);
        List<Employee> results = dao.selectEmployee("first_name", "Alice");

        // ASSERT
        assertFalse(results.isEmpty(), "Employee list should not be empty");
        assertEquals("Smith", results.get(0).getLastName(), "Last name should match");
    }

    // Helper method to create the standard test employee
    private void insertTestAlice() throws SQLException {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("first_name", "Alice");
        data.put("last_name", "Smith");
        data.put("salary", "75000.00");
        data.put("job_title", "Developer");
        data.put("division", "IT");
        data.put("fullTime", "true");
        dao.insertEmployee(data);
    }

    @Test
    @Order(2)
    @DisplayName("Success Case: Update Employee Salary")
    void testUpdateSalary() throws SQLException 
    {
        //ARRANGE
        insertTestAlice();
        List<Employee> currentDb = dao.getAllEmployees();
        System.out.println("Current Employees in DB: " + currentDb.size());
        currentDb.forEach(System.out::println);

       // ACT
        int rowsUpdated = dao.updateSalary(10.0, 70000.0, 80000.0);
        System.out.println("Rows affected: " + rowsUpdated); // Should be 1

        // ASSERT
        List<Employee> results = dao.selectEmployee("first_name", "Alice");
        double updatedSalary = results.get(0).getSalary();
        System.out.println("Salary after update: " + updatedSalary); 

        assertEquals(82500.00, updatedSalary, 0.01, "Expected 82500.00 but got " + updatedSalary);
    
    }

    @Test
    @Order(3)
    @DisplayName("Success Case: Delete Employee")
   void testDeleteEmployee() throws SQLException 
   {
        // ARRANGE: Get current list to see how many we have
        insertTestAlice();
        List<Employee> beforeDelete = dao.getAllEmployees();
        int initialSize = beforeDelete.size();
        
        // We will delete the employee named "Alice"
        int idToDelete = beforeDelete.get(0).getEmpId();

        // ACT
        boolean isDeleted = dao.deleteEmployee(idToDelete);

        // ASSERT
        assertTrue(isDeleted, "Delete method should return true");
        
        List<Employee> afterDelete = dao.getAllEmployees();
        assertEquals(initialSize - 1, afterDelete.size(), "List size should decrease by 1");
        
        // Verify the specific ID is gone
        List<Employee> searchResult = dao.selectEmployee("emp_id", String.valueOf(idToDelete));
        assertTrue(searchResult.isEmpty(), "Deleted employee should not be found in a search");
    }

    @Test
    @Order(4)
    @DisplayName("Fail Case: Check non-existent Employee")
    void testEmployeeExistsFail() throws SQLException {
        // ACT
        boolean exists = dao.employeeExists(999); // ID that doesn't exist

        // ASSERT
        assertFalse(exists, "employeeExists should return false for unknown IDs");
    }

    @Test
    @Order(5)
    @DisplayName("Success Case: Update Employee Info")
    void testUpdateEmployee() throws SQLException {
        // ARRANGE
        insertTestAlice();
        Map<String, String> updateData = new LinkedHashMap<>();
        updateData.put("job_title", "Senior Developer");
        updateData.put("salary", "90000.00");

        // ACT
        dao.updateEmployee(1, updateData);
        List<Employee> results = dao.selectEmployee("emp_id", "1");

        // ASSERT
        assertEquals("Senior Developer", results.get(0).getJobTitle(), "Job title should be updated");
        assertEquals(90000.00, results.get(0).getSalary(), 0.01, "Salary should be updated");
    }

    @Test
    @Order(6)
    @DisplayName("Success Case: Add and Verify Pay Statement")
    void testAddPayStatement() throws SQLException {
        // ARRANGE
        insertTestAlice();
        PayStatement ps = new PayStatement(1, 3000.0, 
            LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 15));

        // ACT & ASSERT
        assertDoesNotThrow(() -> dao.addPayStatement(1, ps), 
            "Adding a pay statement should not throw an exception");
    }

    @Test
    @Order(7)
    @DisplayName("Success Case: Alter Table by Adding Column")
    void testAlterEmployeeTable() throws SQLException {
        // ACT
        boolean success = dao.alterEmployeeTable("phone_number", "VARCHAR(20)");

        // ASSERT
        assertTrue(success, "Alter table should return true");
        assertTrue(SchemaManager.doesColExist("phone_number"), "New column should exist in DB");
    }

    @Test
    @Order(8)
    @DisplayName("Success Case: Print Employee List")
    void testPrintEmployees() throws SQLException {
        // ARRANGE
        insertTestAlice();
        List<Employee> employees = dao.getAllEmployees();

        // ACT & ASSERT
        // Verify that the refactored print method handles the list without crashing
        assertDoesNotThrow(() -> dao.printEmployees(employees), 
            "Printing the employee list should execute successfully");
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBConnection.disconnect();
    }
}

