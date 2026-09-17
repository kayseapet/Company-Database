//import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PayStatementTest {
    private PayStatement ps;

    @BeforeEach 
    void exampleStatement(){
        ps = new PayStatement(123, 0, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 15));
    }

    @Test
    void testPayPeriodFormatting() { 
        /* functions tested: getPayPeriod, setStartDate, getStartDate, setEndDate, getEndDate  */
        String expectedPeriod = "2023-01-01 to 2023-01-15";
        assertEquals(expectedPeriod, ps.getPayPeriod(), "Pay period string should match start to end date format");
        
        ps.setStartDate(LocalDate.of(2023, 6, 6));
        assertEquals(LocalDate.of(2023, 6, 6), ps.getStartDate(), "Start getter and setter should change and retrive Start Date");

        ps.setEndDate(LocalDate.of(2023,6,29));
        assertEquals(LocalDate.of(2023,6,29), ps.getEndDate(), "End getter and setter change and retrive End Date");
    }

    @Test
    void testAmountAccess(){
        double before = ps.getAmount();
        ps.setAmount(100.1);
        double after = ps  .getAmount();
        //ASSERT
        assertEquals(0,before," before test" );
        assertEquals(100.1,after," after test");
    }

    @Test 
    void testToString(){
        String expectedString = "Pay Statement [ID: 0, Employee ID: 123, Period: 2023-01-01 to 2023-01-15, Amount: 0.00]" ;
        assertEquals(expectedString, ps.toString(), "Pay statement object should be converted to a string and retain its information");
    }

    /* tests needing to be added:
        - get & set EmpId
        - start and end date setters and getters
        - toString function.

        //ARRANGE
        // ACT
        //ASSERT
    */
}