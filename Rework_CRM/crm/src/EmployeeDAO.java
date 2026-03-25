import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


// class that handles employee data access operations, such as retrieving, updating, and deleting employee records.
// This class interacts with the database to perform CRUD operations on employee data.
public class EmployeeDAO {
    private Connection conn;

    public Connection getConnection() 
    {
        return this.conn;
    }

    public EmployeeDAO() throws SQLException 
    {
        try 
        {
            this.conn = DBConnection.getConnection();
        } 
        catch (SQLException e) 
        {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public void insertEmployee(Map<String, String> inputData) throws SQLException 
    {
        StringBuilder sql = new StringBuilder("INSERT INTO employee (");
        StringBuilder values = new StringBuilder();
        for (String key : inputData.keySet()) 
        {
            sql.append(key).append(", ");
            values.append("?, ");
        }

        //clean up the last comma and space
        sql.setLength(sql.length() - 2);
        values.setLength(values.length() - 2);
        sql.append(") VALUES (").append(values).append(")");

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString()))
        {
            int index = 1;
            for (String value : inputData.values()) {
                // Change from setString to setObject
                pstmt.setObject(index++, value);
            }
            pstmt.executeUpdate();
            System.out.println("New employee added successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting employee: " + e.getMessage());
            throw e;
        }
    }

    public void updateEmployee(int empId, Map<String, String> inputData) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE employee SET ");
        for (String key : inputData.keySet()) {
            sql.append(key).append(" = ?, ");
        }

        //clean up the last comma and space
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE emp_id = ?");

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            for (String value : inputData.values()) {
                pstmt.setString(index++, value);
            }
            pstmt.setInt(index, empId);
            pstmt.executeUpdate();
            System.out.println("Employee ID " + empId + " updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            throw e;
        }
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next())
            {
                employees.add(getEmployee(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
            throw e;
        }
        return employees;
    }

    //Helper method to convert the SQL row to a java object
    private Employee getEmployee(ResultSet rs) throws SQLException
    {
        Employee emp = new Employee();
        emp.setEmpId(rs.getInt("emp_id"));
        emp.setFirstName(rs.getString("first_name"));
        emp.setLastName(rs.getString("last_name"));
        emp.setSalary(rs.getDouble("salary"));
        emp.setJobTitle(rs.getString("job_title"));
        emp.setDivision(rs.getString("division"));
        emp.setFullTime(rs.getBoolean("fullTime"));
        return emp;
    }



    public void printEmployees(List<Employee> employees) 
    {
        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.printf("%-5s | %-20s | %-20s | %-15s | %-10s\n", 
                        "ID", "Name", "Position", "Division", "Salary");
        System.out.println("-".repeat(80));

        for (Employee emp : employees) {
            System.out.printf("%-5d | %-20s | %-20s | %-15s | $%-10.2f\n",
                            emp.getEmpId(), 
                            emp.getFullName(), 
                            emp.getJobTitle(), 
                            emp.getDivision(), 
                            emp.getSalary());
        }
        System.out.println("=".repeat(80) + "\n");
    }

    public List<Employee> selectEmployee(String colName, String colValue) throws SQLException {
        List<Employee> results = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE " + colName + " = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, colValue);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Use your existing helper method to convert row to object
                    results.add(getEmployee(rs));
                }
            }
        }
        return results; // The list remains open and usable even after the connection block closes
    }

    public boolean employeeExists(int empId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employee WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking employee existence: " + e.getMessage());
            throw e;
        }
        return false;
    }

    public boolean deleteEmployee(int empId) throws SQLException {
        String sql = "DELETE FROM employee WHERE emp_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            throw e;
        }
    }

    public boolean alterEmployeeTable(String colName, String colType) throws SQLException {
        String sql = "ALTER TABLE employee ADD COLUMN " + colName + " " + colType;
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Column " + colName + " added successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error altering employee table: " + e.getMessage());
            throw e;
        }
    }

    public int updateSalary(double percentage, double minSalary, double maxSalary) throws SQLException {
        String sql = "UPDATE employee SET salary = salary * (1 + ? / 100.0) WHERE salary BETWEEN ? AND ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, percentage);
            pstmt.setDouble(2, minSalary);
            pstmt.setDouble(3, maxSalary);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " employees' salaries updated successfully.");
            return rowsAffected; //returns the number of rows affected
        } catch (SQLException e) {
            System.err.println("Error updating salaries: " + e.getMessage());
            throw e;
        }
    }

    public void addPayStatement(int empId, PayStatement payStatement) throws SQLException {
        String sql = "INSERT INTO pay_statement (emp_id, amount, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empId);
            pstmt.setDouble(2, payStatement.getAmount());
            pstmt.setDate(3, Date.valueOf(payStatement.getStartDate()));
            pstmt.setDate(4, Date.valueOf(payStatement.getEndDate()));
            pstmt.executeUpdate();
            System.out.println("Pay statement added successfully for Employee ID " + empId);
        } catch (SQLException e) {
            System.err.println("Error adding pay statement: " + e.getMessage());
            throw e;
        }
    }

    
}
