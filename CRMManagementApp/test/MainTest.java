import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @DisplayName("Test: requestInt handles invalid input gracefully")
    void testRequestInt() {
        //User types 'abc' (invalid) then '10' (valid)
        String input = "abc\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.scanner = new Scanner(System.in);

        int result = Main.requestInt("Enter number: ");
        assertEquals(10, result, "Should ignore 'abc' and eventually return 10");
    }

    @Test
    void testRequestDouble(){
        //Valid
    }

    @Test
    @DisplayName("Test: requestDate handles invalid format")
    void testRequestDateRetry() {
        //User types wrong format then correct format
        String input = "01-01-2023\n2023-01-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Main.scanner = new Scanner(System.in);

        java.time.LocalDate date = Main.requestDate("Enter date: ");

        assertEquals("2023-01-01", date.toString());
    }
}