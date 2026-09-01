import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeManagerTest {

    private static EmployeeManager manager;

    @BeforeAll
    static void setup() throws SQLException {
        // Initialize schema and manager before tests
        SchemaManager.initializeSchema();
        manager = new EmployeeManager();
    }

    @BeforeEach
    void resetManagerData() throws SQLException {
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");
            stmt.execute("TRUNCATE TABLE employee");
            stmt.execute("TRUNCATE TABLE pay_statement");
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
            stmt.execute("ALTER TABLE employee ALTER COLUMN emp_id RESTART WITH 1");
        }
    }

    @Test
    @Order(1)
    @DisplayName("Success Case: Add Employee through Manager")
    void testAddEmployee() throws SQLException {
        // ARRANGE
        Map<String, String> inputData = new LinkedHashMap<>();
        inputData.put("first_name", "Bob");
        inputData.put("last_name", "Builder");
        inputData.put("salary", "60000.0");
        inputData.put("job_title", "Contractor");
        inputData.put("division", "Construction");
        inputData.put("fullTime", "true");

        // ACT
        manager.addEmployee(inputData);

        // ASSERT: Verify existence using the DAO inside the manager
        assertTrue(manager.employeeDAO.employeeExists(1), "Employee should exist after being added via manager");
    }

    @Test
    @Order(2)
    void testIncreaseSalary() throws SQLException {
        // ARRANGE: Re-add Bob because @BeforeEach wiped him
        testAddEmployee(); 

        // ACT
        manager.increaseSalaryByPercentage(5.0, 50000.0, 70000.0);

        // ASSERT
        List<Employee> employees = manager.employeeDAO.getAllEmployees();
        boolean foundUpdated = employees.stream()
            .anyMatch(e -> e.getFirstName().equals("Bob") && e.getSalary() == 63000.0);
        
        assertTrue(foundUpdated, "Employee salary should be updated to 63000.0");
    }

    @Test
    @Order(3)
    void testAddPayStatement() throws SQLException {
        // ARRANGE: Re-add Bob so ID 1 exists
        testAddEmployee(); 
        
        java.time.LocalDate start = java.time.LocalDate.now().minusDays(14);
        java.time.LocalDate end = java.time.LocalDate.now();
        PayStatement ps = new PayStatement(1, 2500.0, start, end);

        // ACT & ASSERT
        assertDoesNotThrow(() -> manager.addPayStatement(1, ps));
    }
    
    @Test
    @Order(4)
    @DisplayName("Fail Case: Delete non-existent Employee")
    void testDeleteNonExistentEmployee() throws SQLException {
        // ACT & ASSERT
        // The manager handles the message printing, but we verify the DAO handles the logic
        assertDoesNotThrow(() -> manager.deleteEmployee(999), 
            "Deleting a non-existent ID should be handled gracefully without crashing");
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBConnection.disconnect();
    }
}