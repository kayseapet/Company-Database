import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {
    private static ReportGenerator reportGen;
    private static EmployeeDAO dao;

    @BeforeAll
    static void setup() throws SQLException {
        SchemaManager.initializeSchema();
        reportGen = new ReportGenerator();
        dao = new EmployeeDAO();

        // Seed data for reports
        Map<String, String> emp = new LinkedHashMap<>();
        emp.put("first_name", "Report");
        emp.put("last_name", "User");
        emp.put("salary", "5000.0");
        emp.put("job_title", "Tester");
        emp.put("division", "QA");
        emp.put("fullTime", "true");
        dao.insertEmployee(emp);
    }

    @Test
    @DisplayName("Success Case: Generate Employee Pay History")
    void testPayHistoryReport() {
        // Since the method prints to console, we verify it runs without SQLException
        assertDoesNotThrow(() -> reportGen.generateEmployeePayHistoryReport(1));
    }

    @Test
    @DisplayName("Success Case: Job Title Report with Data")
    void testJobTitleReport() {
        // Tests the monthly aggregate logic
        assertDoesNotThrow(() -> reportGen.generateMonthlyPayByJobTitle(2023, 10));
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBConnection.disconnect();
    }
}