import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SchemaManagerTest {

    @BeforeAll
    static void setup() {
        // Ensure we start with a fresh connection
        try {
            DBConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void cleanSlate() throws SQLException {
        try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement()) {
            // H2 specific command to remove all tables, constraints, and sequences
            stmt.execute("DROP ALL OBJECTS"); 
            System.out.println("Database wiped for fresh schema test.");
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test Table Initialization")
    void testInitializeSchema() {
        // ACT
        boolean result = SchemaManager.initializeSchema();

        // ASSERT
        assertTrue(result, "Schema initialization should return true");
        assertTrue(SchemaManager.doesColExist("emp_id"), "Primary key emp_id should exist");
        assertTrue(SchemaManager.doesColExist("first_name"), "Column first_name should exist");
    }

    @Test
    @Order(2)
    @DisplayName("Test Adding a New Column")
    void testAddColumn() {
        // ARRANGE
        String newCol = "phone_number";
        String type = "VARCHAR(20)";

        // ACT
        SchemaManager.initializeSchema();
        boolean added = SchemaManager.addCol(newCol, type);

        // ASSERT
        assertTrue(added, "Adding column should return true");
        assertTrue(SchemaManager.doesColExist(newCol), "The new column should be detectable in the schema");
    }

    @Test
    @Order(3)
    @DisplayName("Test Column Existence Failure Case")
    void testColumnDoesNotExist() {
        // ACT & ASSERT
        assertFalse(SchemaManager.doesColExist("non_existent_column"), 
            "Should return false for columns that were never added");
    }

    @AfterAll
    static void tearDown() throws SQLException {
        DBConnection.disconnect();
    }
}