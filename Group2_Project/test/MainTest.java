import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class MainTest {

    /**
     * Helper method to simulate user typing into the console
     */
    private void setMockInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        // Re-initialize the scanner in Main to use the new input stream
        Main.scanner = new Scanner(System.in);
    }

    @Test
    public void testValidMenuOptionAndExit() {
        // Simulates selecting option '1' then option '0' to exit
        setMockInput("1\n0\n");
        
        // If an exception occurs here, JUnit 4 marks the test as FAILED
        Main.main(new String[0]);
    }

    @Test
    public void testInvalidMenuInput() {
        // Simulates typing a letter "x" when a number is expected, 
        // then a valid "0" to exit. Tests your InputHandler's catch block.
        setMockInput("x\n0\n");
        
        Main.main(new String[0]);
    }

    @Test
    public void testEmptyInputHandling() {
        // Simulates just hitting 'Enter' then exiting
        setMockInput("\n0\n");
        
        Main.main(new String[0]);
    }

	@Test
	public void testReportMenuNavigation() {
		// Simulates selecting option '2' (Generate Reports), then '0' to go back
		setMockInput("2\n0\n");
		
		Main.main(new String[0]);
	}

	@Test
	public void testEmployeeMenuNavigation() {
		// Simulates selecting option '1' (Employee Management), then '0' to go back to main menu, then '0' to exit
		setMockInput("1\n0\n0\n");
		
		Main.main(new String[0]);
	}

	@Test
	public void testMultipleInvalidInputsThenExit() {
		// Simulates typing invalid options multiple times before exiting
		setMockInput("abc\nxyz\n@#$\n0\n");
		
		Main.main(new String[0]);
	}

	@Test
	public void testRequestIntValidInput() {
		setMockInput("42\n0\n");
		
		Main.main(new String[0]);
	}

	@Test
	public void testRequestDoubleValidInput() {
		setMockInput("3.14\n0\n");
		
		Main.main(new String[0]);
	}

	@Test
	public void testRequestStringValidInput() {
		setMockInput("TestEmployee\n0\n");
		
		Main.main(new String[0]);
	}

	@Test
	public void testDirectExitFromMainMenu() {
		// Simulates immediately selecting exit option '0'
		setMockInput("0\n");
		
		Main.main(new String[0]);
	}
}