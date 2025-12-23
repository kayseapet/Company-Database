import java.sql.*;
import java.util.Map;


// class that handles employee data access operations, such as retrieving, updating, and deleting employee records.
// This class interacts with the database to perform CRUD operations on employee data.
public class EmployeeDAO {
    private Connection conn;

    public EmployeeDAO() throws SQLException {
        try {
            this.conn = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public void insertEmployee(Map<String, String> inputData) throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO employee (");
        StringBuilder values = new StringBuilder();
        for (String key : inputData.keySet()) {
            sql.append(key).append(", ");
            values.append("?, ");
        }

        //clean up the last comma and space
        sql.setLength(sql.length() - 2);
        values.setLength(values.length() - 2);
        sql.append(") VALUES (").append(values).append(")");

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            for (String value : inputData.values()) {
                pstmt.setString(index++, value);
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

    public void viewAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employee";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            printResults(rs);
        } catch (SQLException e) {
            System.err.println("Error retrieving employees: " + e.getMessage());
            throw e;
        }
    }

    public void printResults(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t");
        }
        System.out.println();

        //print each row of the result set
        while (rs.next())
        {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }

        
    }

    public ResultSet selectEmployee(String colName, String colValue) throws SQLException {
        String sql = "SELECT * FROM employee WHERE " + colName + " = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, colValue);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs;
            }
        } catch (SQLException e) {
            System.err.println("Error selecting employee: " + e.getMessage());
            throw e;
        }
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

    public ResultSet updateSalary(double percentage, double minSalary, double maxSalary) throws SQLException {
        String sql = "UPDATE employee SET salary = salary * (1 + ? / 100) WHERE salary BETWEEN ? AND ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, percentage);
            pstmt.setDouble(2, minSalary);
            pstmt.setDouble(3, maxSalary);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " employees' salaries updated successfully.");
            // Return the updated result set
            return selectEmployee("salary", String.valueOf(minSalary)); // Example to return updated employees
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
