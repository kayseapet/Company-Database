import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class InputHandler {
    private EmployeeManager employeeManager;

    // Add a constructor for Dependency Injection
    public InputHandler(EmployeeManager manager) {
        this.employeeManager = manager;
    }
    
    public Map<String, String> requestAddEmployee(Map<String, String> schema) {
        // Implementation for adding an employee
        Map<String, String> inputData = new LinkedHashMap<>();
        if (schema == null || schema.isEmpty()) {
            System.out.println("No employee schema found. Cannot add employee.");
            return inputData;
        }
        else {
            for(Map.Entry<String, String> entry : schema.entrySet()) {
                String key = entry.getKey();
                String type = entry.getValue().toUpperCase();
                
                String value = null;
                switch (type) {
                    case "INT":
                        value = String.valueOf(Main.requestInt("Enter " + key + ": "));
                        break;
                    case "DOUBLE":
                        value = String.valueOf(Main.requestDouble("Enter " + key + ": "));
                        break;
                    case "BOOLEAN":
                        value = String.valueOf(Main.requestBoolean("Enter " + key + ": "));
                        break;
                    case "VARCHAR":
                        value = Main.requestString("Enter " + key + ": ");
                        break;
                    case "DATE":
                        value = String.valueOf(Main.requestDate("Enter " + key + ": "));
                        break;
                    default:
                        value = Main.requestString("Enter " + key + ": ");
                        break;
                }
                
                while (value.isEmpty()) {
                    System.out.println("This field cannot be empty. Please enter a valid value.");
                    value = Main.requestString("Enter " + key + ": ");
                }
                inputData.put(key, value);
            }
            return inputData;
        }
        

    

    }
    public PayStatement requestAddPayStatement() {
        // Implementation for adding a pay statement
        int empId = Main.requestInt("Enter Employee ID for Pay Statement: ");
        double amount = Main.requestDouble("Enter Pay Statement Amount: ");
        LocalDate startDate = Main.requestDate("Enter Pay Statement Start Date: ");
        LocalDate endDate = Main.requestDate("Enter Pay Statement End Date: ");
        
        // Create a PayStatement object and return it
        return new PayStatement(empId, amount, startDate, endDate);
    }

    public void requestEditEmployee(Map<String, String> schema) {
        // Implementation for editing an employee
        Map<String, String> inputData = new LinkedHashMap<>();
        int empId = Main.requestInt("Enter Employee ID to edit: ");
        int editType = Main.requestInt("Enter 1 to edit Employee Info, Enter 2 to add Employee Pay Statement: ");
        while (editType < 1 || editType > 2) {
            System.out.println("Invalid choice. Please enter 1 or 2.");
            editType = Main.requestInt("Enter 1 to edit Employee Info, Enter 2 to add Employee Pay Statement: ");
        }
        
        // If the user chooses to edit employee info
        if(editType == 1) 
        {
            inputData = requestUpdateEmployee(schema);
            if (inputData.isEmpty()) {
                System.out.println("No data provided for update.");
                return;
            }
            try {
                employeeManager.updateEmployee(empId, inputData);
                System.out.println("Employee with ID " + empId + " updated successfully.");
            } catch (SQLException e) {
                System.err.println("Error updating employee: " + e.getMessage());
            }
        }
        // If the user chooses to add a pay statement
        else{ // If the user chooses to add a pay statement
            // Implementation for adding a pay statement
            int empIdForPay = Main.requestInt("Enter Employee ID to add Pay Statement: ");
            
            // Create a PayStatement object and add it to the inputData map
            PayStatement payStatement = requestAddPayStatement();
            try {
                employeeManager.addPayStatement(empIdForPay, payStatement);
                System.out.println("Pay Statement added successfully for Employee ID " + empIdForPay + ".");
            } catch (SQLException e) {
                System.err.println("Error adding Pay Statement: " + e.getMessage());
            }
            
        }
    }

    public Map<String,String> requestUpdateEmployee(Map<String, String> schema) {
        // Implementation for updating an employee
        Map<String, String> inputData = new LinkedHashMap<>();
        if (schema == null || schema.isEmpty()) {
            System.out.println("No employee schema found. Cannot update employee.");
            return inputData;
        }
        else {
            for(Map.Entry<String, String> entry : schema.entrySet()) {
                String key = entry.getKey();
                String type = entry.getValue().toUpperCase();
                
                String value = null;
                switch (type) {
                    case "INT":
                        value = String.valueOf(Main.requestInt("Enter new " + key + ": "));
                        break;
                    case "DOUBLE":
                        value = String.valueOf(Main.requestDouble("Enter new " + key + ": "));
                        break;
                    case "BOOLEAN":
                        value = String.valueOf(Main.requestBoolean("Enter new " + key + ": "));
                        break;
                    case "VARCHAR":
                        value = Main.requestString("Enter new " + key + ": ");
                        break;
                    case "DATE":
                        value = String.valueOf(Main.requestDate("Enter new " + key + ": "));
                        break;
                    default:
                        value = Main.requestString("Enter new " + key + ": ");
                        break;
                }
                
                while (value.isEmpty()) {
                    System.out.println("This field cannot be empty. Please enter a valid value.");
                    value = Main.requestString("Enter new " + key + ": ");
                }
                inputData.put(key, value);
            }
            return inputData;
        }
    }


    public void requestViewEmployee() {
        // Implementation for viewing an employee
        boolean viewAll = Main.requestBoolean("View all employees? (true/false): ");
        if (viewAll) 
        {
            // Fetch and display all employees
            try {
                employeeManager.getAllEmployees();
            } catch (SQLException e) {
                System.err.println("Error retrieving employees: " + e.getMessage());
            }
        } else {
            // display filtered employees
            String filterCol = Main.requestString("Enter column to filter by (e.g., name, job title, etc.): ");
            boolean colExists = SchemaManager.doesColExist(filterCol);
            while (!colExists) {
                System.out.println("Column does not exist. Please try again.");
                filterCol = Main.requestString("Enter column to filter by (e.g., name, job title, etc.): ");
                colExists = SchemaManager.doesColExist(filterCol);
            }
            String filterValue = Main.requestString("Enter value to filter by: ");
            try {
                employeeManager.filterEmployees(filterCol, filterValue);
            } catch (SQLException e) {
                System.err.println("Error retrieving filtered employees: " + e.getMessage());
            } 
        }
    }


    public void requestDeleteEmployee() {
        // Implementation for deleting an employee
        int empId = Main.requestInt("Enter Employee ID to delete: ");
        boolean exists = false;
        try {
            exists=  employeeManager.employeeDAO.employeeExists(empId);
        } catch (SQLException e) {
            System.err.println("Error checking employee existence: " + e.getMessage());
            return;
        }   
        while (!exists) {
            System.out.println("Employee with ID " + empId + " does not exist. Please enter a valid Employee ID.");
            empId = Main.requestInt("Enter Employee ID to delete: ");
            try {
                employeeManager.employeeDAO.employeeExists(empId);
            } catch (SQLException e) {
                System.err.println("Error checking employee existence: " + e.getMessage());
                return;
            }
        }
        //confirm if the employee exists before deleting
        boolean confirm = Main.requestBoolean("Are you sure you want to delete Employee ID " + empId + "? (true/false): ");
        if (confirm) {
            try {
                employeeManager.deleteEmployee(empId);
            } catch (SQLException e) {
                System.err.println("Error deleting employee: " + e.getMessage());
            }
        } 
        else 
        {
            System.out.println("Deletion cancelled.");
        }


    }
    
    public void requestSearchEmployee() {
        String searchCol = Main.requestString("Enter search term (name, job title, etc.): ");
        if (!SchemaManager.doesColExist(searchCol)) {
            System.out.println("Column does not exist.");
            return;
        }
        String searchValue = Main.requestString("Enter value to search for: ");
        try {
            List<Employee> rs = employeeManager.searchEmployee(searchCol, searchValue);
            // FIX: Manager already prints, but we verify empty list here
            if (rs == null || rs.isEmpty()) {
                System.out.println("No employees found.");
            }
        } catch (SQLException e) {
            System.err.println("Error searching: " + e.getMessage());
        }
    }
    
    public void requestAddEmployeeColumn() {
        // Implementation for adding a new column to the employee table
        String colName = Main.requestString("Enter new column name: ");
        String colType = Main.requestString("Enter column type (e.g., VARCHAR(50), INT): ");
        boolean colExists = SchemaManager.doesColExist(colName);
        while (colExists) {
            System.out.println("Column already exists. Please enter a different column name.");
            colName = Main.requestString("Enter new column name: ");
            colExists = SchemaManager.doesColExist(colName);
        }
        boolean success = SchemaManager.addCol(colName, colType);
        if (success) {
            System.out.println("Column " + colName + " added successfully.");
        } else {
            System.out.println("Failed to add column " + colName + ".");
        }

    }
    public void requestIncreaseSalary() {
        // Implementation for increasing salary by percentage within a range
        double percentage = Main.requestDouble("Enter percentage increase (e.g., 10 for 10%): ");
        double minSalary = Main.requestDouble("Enter minimum salary for increase: ");
        double maxSalary = Main.requestDouble("Enter maximum salary for increase: ");
        try {
            employeeManager.increaseSalaryByPercentage(percentage, minSalary, maxSalary);
        } catch (SQLException e) {
            System.err.println("Error updating salaries: " + e.getMessage());
        }
        

    }


}
