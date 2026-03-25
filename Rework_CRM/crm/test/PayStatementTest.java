import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PayStatementTest {

    @Test
    void testPayPeriodFormatting() {
        // ARRANGE
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 15);
        
        // ACT
        PayStatement ps = new PayStatement(1, 2000.0, start, end);
        
        // ASSERT
        String expectedPeriod = "2023-01-01 to 2023-01-15";
        assertEquals(expectedPeriod, ps.getPayPeriod(), "Pay period string should match start to end date format");
    }
}