import java.util.Scanner;
/* Main Class: where the Menu Options are displayed, and the user decides what feature they'd like to interact with in the system (viewing the database, editing the database, or generating a report), and gives the input required.*/

public class Main {
    private Scanner scanner; // object needed to accept user input

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
            System.out.print("\nSelect an option: ");
            System.out.println("[ 1 ] Edit Database");
            System.out.println("[ 2 ] View Database");
            System.out.println("[ 3 ] Generate Reports");
            System.out.println("[ 9 ] Exit");

            String choice = scanner.nextLine().trim(); // to get user input

            //choosing next menu based on the user input
            switch(choice)
            {
                case "1":
                    printEditMenu();
                    break;
                case "2":
                    printViewMenu();
                    break;
                case "3":
                    printReportMenu();
                    break;
                case "9":
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    scanner.nextLine();
            }
        }
    } 

    // Establishing Different Menu Interfaces & Calls InputHandler Methods based on the choice the User makes
    private void printViewMenu()
    {
        while(true)
        {
            System.out.println("=".repeat(60));
            System.out.println("\tEMPLOYEE MANAGEMENT SYSTEM\t");
            System.out.println("=".repeat(60));
            System.out.println("\n" + "=".repeat(40));
            System.out.println("VIEW DATABASE");
            System.out.println("=".repeat(40));
            System.out.print("\nWhich table would you like to view?: ");
            System.out.println("[ 0 ] Back");
            System.out.println("[ 1 ] Employee Table");      
            System.out.println("[ 2 ] Company Jobs Table");
            System.out.println("[ 3 ] Company Divisions Table");
            System.out.println("[ 9 ] Exit");

            String choice = scanner.nextLine().trim(); // to get user input

            //choosing next menu based on the user input
            switch(choice)
            {
                case "0":
                    printMainMenu();
                    break;
                case "1":
                    printEditMenu();
                    break;
                case "2":
                    printViewMenu();
                    break;
                case "3":
                    printReportMenu();
                    break;
                case "9":
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    scanner.nextLine();
            }
        }
    }

    private void printEditMenu()
    {
        while (true)
        {
            System.out.println("=".repeat(60));
            System.out.println("\tEMPLOYEE MANAGEMENT SYSTEM\t");
            System.out.println("=".repeat(60));
            System.out.println("\n" + "=".repeat(40));
            System.out.println("EDIT DATABASE");
            System.out.println("=".repeat(40));
            System.out.print("\nWhich table would you like to edit?: ");
            System.out.println("[ 0 ] Back");
            System.out.println("[ 1 ] Employee Table");
            System.out.println("[ 2 ] Company Jobs Table");
            System.out.println("[ 3 ] Company Divisions Table");
            System.out.println("[ 9 ] Exit");

            String choice = scanner.nextLine().trim(); // to get user input

            //choosing next menu based on the user input
            switch(choice)
            {
                case "0":
                    printMainMenu();
                    break;
                case "1":
                    printEditMenu();
                    break;
                case "2":
                    printViewMenu();
                    break;
                case "3":
                    printReportMenu();
                    break;
                case "9":
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    scanner.nextLine();
            }
        }
    }
    
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
                case "0":
                    printMainMenu();
                    break;
                case "1":
                    printEditMenu();
                    break;
                case "2":
                    printViewMenu();
                    break;
                case "3":
                    printReportMenu();
                    break;
                case "9":
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    scanner.nextLine();
            }
        }

    }
    // to end the program, and the DB Connection
    private void exitApplication()
    {
        System.out.println("=".repeat(60));
        System.out.println("       EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("Thank you for using Employee Management System!");
        System.out.println("Goodbye!");
    }

    public static void main(String[] args) throws Exception {
       if (!SchemaManager.initializeSchema())
       {
        System.err.println("Failed to initialize database. Exiting...");
            return;
       }
       else
       {
        //to start with the main menu
        Main program = new Main();
        program.printMainMenu();
       }
    }
}
