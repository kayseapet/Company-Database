import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @DisplayName("Test: requestInt handles invalid input gracefully")
    void testRequestIntInvalid() {
        // ARRANGE: User types 'abc' (invalid) then '10' (valid)
        String input = "abc\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.scanner = new Scanner(System.in);

        // ACT
        int result = Main.requestInt("Enter number: ");

        // ASSERT
        assertEquals(10, result, "Should ignore 'abc' and eventually return 10");
    }

    @Test
    @DisplayName("Test: requestDate handles invalid format")
    void testRequestDateRetry() {
        // ARRANGE: User types wrong format then correct format
        String input = "01-01-2023\n2023-01-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.scanner = new Scanner(System.in);

        // ACT
        java.time.LocalDate date = Main.requestDate("Enter date: ");

        // ASSERT
        assertEquals("2023-01-01", date.toString());
    }
}