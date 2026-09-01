// class that manages employee-related operations based on the requests from input handler, & helps with writing the SQL queries.
import java.sql.*;
import java.util.Map;
public class EmployeeManager {
    public EmployeeDAO employeeDAO;

    public EmployeeManager() throws SQLException {
        this.employeeDAO = new EmployeeDAO();
    }

    // to add a new employee
    // inputData is a map of column names and their corresponding values
    public void addEmployee(Map<String, String> inputData) throws SQLException {
        employeeDAO.insertEmployee(inputData);
    }

    // to edit an employee's information
    public void updateEmployee(int empId, Map<String, String> inputData) throws SQLException {
        employeeDAO.updateEmployee(empId, inputData);
    }
    // to view all employees in the database
    public void viewAllEmployees() throws SQLException {
        employeeDAO.viewAllEmployees();
    }

    public void filterEmployees(String colName, String val) throws SQLException {
        ResultSet rs = employeeDAO.selectEmployee(colName, val);
        employeeDAO.printResults(rs);
    }

    public ResultSet searchEmployee(String colName, String colValue) throws SQLException {
        ResultSet rs = employeeDAO.selectEmployee(colName, colValue);
        // if there are more than one employee with the same name, it will ask the user to select one in input handler
        employeeDAO.printResults(rs);
        return rs;
    }

    // to delete an employee by their ID
    public void deleteEmployee(int empId) throws SQLException {
        boolean sucessful = employeeDAO.deleteEmployee(empId);
        if (sucessful) {
            System.out.println("Employee with ID " + empId + " deleted successfully.");
        } else {
            System.out.println("Failed to delete employee with ID " + empId + ".");
        }
    }

    public void increaseSalaryByPercentage(double percentage, double min, double max) throws SQLException {
        ResultSet rs = employeeDAO.updateSalary(percentage, min, max);
        if (rs != null) {
            System.out.println("Salaries updated successfully for employees within the specified range.");
            employeeDAO.printResults(rs);
        } else {
            System.out.println("No employees found within the specified salary range.");
        }
       
    }

    public void addPayStatement(int empId, PayStatement payStatement) throws SQLException {
        employeeDAO.addPayStatement(empId, payStatement);
    }


    
}
