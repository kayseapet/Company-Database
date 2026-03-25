import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
/* Main Class: where the Menu Options are displayed, and the user decides what feature they'd like to interact with in the system (viewing the database, editing the database, or generating a report), and gives the input required.*/

public class Main {
    public static Scanner scanner = new Scanner(System.in); // object needed to accept user input
    private InputHandler inputHandler; // object to handle user input for different operations

    public void start() {
        printMainMenu(); // Move menu loop here so main() is just an entry point
    }

    public Main(InputHandler handler) {
        this.inputHandler = handler;
    }

    // Main Menu: starting menu for the program, and asks user to choice how they'd like to interact with the system. Based on the choice, it displays a new menu.
    private void printMainMenu()
    {
        while(true)
        {
            System.out.println("=".repeat(60));
            System.out.println("\tEMPLOYEE MANAGEMENT SYSTEM\t");
            System.out.println("=".repeat(60));
            System.out.println("\n" + "=".repeat(40));
            System.out.println("MAIN MENU");
            System.out.println("=".repeat(40));
           System.out.print
           ("""
    
            Select an option:
            [ 1 ] Employee Management
            [ 2 ] Generate Reports

            [ 0 ] Exit
            """
            );

            String choice = scanner.nextLine().trim(); // to get user input

            //choosing next menu based on the user input
            switch(choice)
            {
                case "1" -> EmployeeMenu();
                case "2" -> printReportMenu();
                case "0" -> exitApplication();
                default -> {
                    System.out.println("Invalid option. Please try again.");
                    scanner.nextLine();
                }
            }
        }
    }

    // Establishing Different Menu Interfaces & Calls InputHandler Methods based on the choice the User makes
    private void EmployeeMenu()
    {
        while (true)
        {
            System.out.println("=".repeat(60));
            System.out.println("\tEMPLOYEE MANAGEMENT SYSTEM\t");
            System.out.println("=".repeat(60));
            System.out.println("\n" + "=".repeat(40));
            System.out.println("EMPLOYEE DATA");
            System.out.println("=".repeat(40));
            System.out.print
            ("""
    
            Which option would you like to choose?:
            [ 0 ] Back

            [ 1 ] Add Employee
            [ 2 ] View Employees (All or Filtered)
            [ 3 ] Edit Employee (Update Data, Add Pay Statement)
            [ 4 ] Delete Employee 
            [ 5 ] Search for Employee by Column Value

            [ 6 ] Add Employee Column
            [ 7 ] Increase Salary by Percentage (Within a Range)
        
            [ 9 ] Exit
            """
            );

            String choice = scanner.nextLine().trim(); // to get user input
            Map<String, String> schema = null;
            try {
                schema = SchemaManager.getEmployeeSchema(); // Get the employee schema for editing
            } catch (SQLException e) {
                System.err.println("Error retrieving employee schema: " + e.getMessage());
            }

            

            //choosing next menu based on the user input
            switch(choice)
            {
                case "0" -> printMainMenu();
                case "1" -> inputHandler.requestAddEmployee(schema);
                case "2" ->inputHandler.requestViewEmployee();
                case "3" -> inputHandler.requestEditEmployee(schema);
                case "4" -> inputHandler.requestDeleteEmployee();
                case "5" -> inputHandler.requestSearchEmployee();
                case "6" -> inputHandler.requestAddEmployeeColumn();
                case "7" -> inputHandler.requestIncreaseSalary();
                case "9" -> exitApplication();
                default -> {
                    System.out.println("Invalid option. Please try again.");
                    scanner.nextLine();
                }
            }
        }
    }

    
    // To request to edit an Employee's info
    private void printEditMenu()
    {

    }


    // To view the database, either the entire thing or a subsection of it
    private void printViewMenu()
    {
        
    }


    // Report Menu: displays the options for generating reports, and calls the ReportGenerator class to generate
    private void printReportMenu()
    {
        while (true)
        {
            System.out.println("=".repeat(60));
            System.out.println("\tEMPLOYEE MANAGEMENT SYSTEM\t");
            System.out.println("=".repeat(60));
            System.out.println("\n" + "=".repeat(40));
            System.out.println("GENERATE REPORT");
            System.out.println("=".repeat(40));
            System.out.print("\nWhich report would you like to generate?: ");
            System.out.println("[ 0 ] Back");
            System.out.println("[ 1 ] Full-time Employees Report");
            System.out.println("[ 2 ] Month's Total Pay by Job Title");
            System.out.println("[ 3 ] Month's Total Pay by Division");
            System.out.println("[ 9 ] Exit");

            String choice = scanner.nextLine().trim(); // to get user input

            //choosing next menu based on the user input
            switch(choice)
            {
                case "0" -> printMainMenu();
                case "1" -> printEditMenu();
                case "2" -> printViewMenu();
                case "3" -> printReportMenu();
                case "9" -> exitApplication();
                default -> {
                    System.out.println("Invalid option. Please try again.");
                    scanner.nextLine();
                }
            }
        }

    }


    // to end the program, and the DB Connection
    public static void exitApplication()
    {
        System.out.println("=".repeat(60));
        System.out.println("       EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("Thank you for using Employee Management System!");
        System.out.println("Goodbye!");
        System.out.println("=".repeat(60));
        // Close the database connection if it was opened
        try {
            DBConnection.disconnect();
            System.exit(0);
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    // Methods to handle user input for different data types
    public static Double requestDouble(String prompt) 
    {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) 
        {
            System.out.println("Invalid input. Please enter a valid decimal number.");
            scanner.next(); // clear the invalid input
            System.out.print(prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume the newline character
        return value;
    }

    public static int requestInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next(); // Clear invalid
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); //Consume the remaining newline
        return value;
    }

    public static String requestString(String prompt)
    {
        System.out.print(prompt);
        String value = scanner.nextLine();
        return value;
    }

    public static Boolean requestBoolean(String prompt) 
    {
        System.out.print(prompt);
        while (!scanner.hasNextBoolean()) 
        {
            System.out.println("Invalid input. Please enter 'true' or 'false'.");
            scanner.next(); // clear the invalid input
            System.out.print(prompt);
        }
        boolean value = scanner.nextBoolean();
        scanner.nextLine(); // consume the newline character
        return value;
    }
    public static LocalDate requestDate(String prompt)
    {
        
        while (true) {
            System.out.print(prompt);
            String dateInput = scanner.nextLine().trim();
            // Validate the date format (e.g., YYYY-MM-DD)
            try {
                return LocalDate.parse(dateInput);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        // 1. Initialize the Database Schema (Crucial for first-time use)
        if (!SchemaManager.initializeSchema()) {
            System.err.println("Fatal Error: Could not initialize database schema. Exiting...");
            return;
        }

        try {
            // 2. Initialize the "Engine" (Manager)
            EmployeeManager manager = new EmployeeManager();

            // 3. Initialize the "UI Logic" (InputHandler) with the Manager
            InputHandler inputHandler = new InputHandler(manager);

            // 4. Initialize the "Main Menu" (App) with the InputHandler
            Main program = new Main(inputHandler);

            // 5. Start the program loop
            program.printMainMenu();

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 6. Ensure the database disconnects when the loop ends
            try {
                DBConnection.disconnect();
            } catch (Exception e) {
                System.err.println("Error during shutdown: " + e.getMessage());
            }
        }
    }
}
