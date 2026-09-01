import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    @DisplayName("Success Case: Get Full Name")
    void testGetFullName() {
        // ARRANGE
        Employee emp = new Employee("John", "Doe", "Developer", "IT", 50000.0, true);
        
        // ACT & ASSERT
        assertEquals("John Doe", emp.getFullName(), "Full name should be first name + space + last name");
    }

    @Test
    @DisplayName("Success Case: Test Full-Time Status String")
    void testToStringStatus() {
        // ARRANGE
        Employee empFT = new Employee("Jane", "Doe", "Manager", "HR", 60000.0, true);
        Employee empPT = new Employee("Bob", "Ross", "Artist", "Design", 40000.0, false);

        // ACT & ASSERT
        assertTrue(empFT.toString().contains("Full-Time"));
        assertTrue(empPT.toString().contains("Part-Time"));
    }
}