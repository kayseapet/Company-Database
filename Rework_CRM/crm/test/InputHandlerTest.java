import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {
    private InputHandler handler;
    private EmployeeManager manager;

    @BeforeEach
    void setup() throws SQLException {
        manager = new EmployeeManager();
        handler = new InputHandler(manager);
        SchemaManager.initializeSchema();
    }

    @Test
    @DisplayName("Test: requestAddPayStatement captures input correctly")
    void testRequestAddPayStatement() {
        // ARRANGE: Simulate user typing ID, Amount, StartDate, EndDate
        String input = "1\n3000.0\n2023-01-01\n2023-01-15\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.scanner = new Scanner(System.in); 

        // ACT
        PayStatement ps = handler.requestAddPayStatement();

        // ASSERT
        assertEquals(1, ps.getEmpId());
        assertEquals(3000.0, ps.getAmount());
        assertEquals("2023-01-01 to 2023-01-15", ps.getPayPeriod());
    }

    @Test
    @DisplayName("Test: requestAddEmployee maps types correctly")
    void testRequestAddEmployee() {
        // ARRANGE: Map schema to simulate input
        Map<String, String> schema = new LinkedHashMap<>();
        schema.put("first_name", "VARCHAR");
        schema.put("salary", "DOUBLE");
        schema.put("fullTime", "BOOLEAN");

        String input = "Alice\n75000.0\ntrue\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.scanner = new Scanner(System.in);

        // ACT
        Map<String, String> result = handler.requestAddEmployee(schema);

        // ASSERT
        assertEquals("Alice", result.get("first_name"));
        assertEquals("75000.0", result.get("salary"));
        assertEquals("true", result.get("fullTime"));
    }
}