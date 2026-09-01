import java.sql.*;
import java.util.*;

public class ReportGenerator {

    private Connection connection;

    public void generateEmployeePayHistoryReport(int empId) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EMPLOYEE PAY HISTORY REPORT");
        System.out.println("=".repeat(60));
        String empSql = "SELECT * FROM employees WHERE emp_id = ?";
        String paySql = "SELECT * FROM pay_statements WHERE emp_id = ? ORDER BY pay_period DESC";

        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement empStmt = conn.prepareStatement(empSql)) {
                empStmt.setInt(1, empId);
                ResultSet empRs = empStmt.executeQuery();

                if (empRs.next()) {
                    System.out.printf("Employee ID: %d\n", empRs.getInt("emp_id"));
                    System.out.printf("Name: %s %s\n", empRs.getString("first_name"), empRs.getString("last_name"));
                    System.out.printf("Job Title: %s\n", empRs.getString("job_title"));
                    System.out.printf("Division: %s\n", empRs.getString("division"));
                    System.out.printf("Current Salary: $%.2f\n", empRs.getDouble("salary"));
                    System.out.printf("Hire Date: %s\n", empRs.getDate("hire_date"));
                    System.out.printf("SSN: %s\n", empRs.getString("ssn"));
                } else {
                    System.out.println("Employee not found.");
                    return;
                }
            }

            System.out.println("\nPAY HISTORY:");
            System.out.println("-".repeat(60));
            System.out.printf("%-12s %-12s %-12s %-12s\n", "Pay Period", "Gross Pay", "Deductions", "Net Pay");
            System.out.println("-".repeat(60));

            try (PreparedStatement payStmt = conn.prepareStatement(paySql)) {
                payStmt.setInt(1, empId);
                ResultSet payRs = payStmt.executeQuery();

                double totalGross = 0, totalDeductions = 0, totalNet = 0;
                int payCount = 0;

                while (payRs.next()) {
                    double gross = payRs.getDouble("gross_pay");
                    double deductions = payRs.getDouble("deductions");
                    double net = payRs.getDouble("net_pay");

                    System.out.printf("%-12s $%-11.2f $%-11.2f $%-11.2f\n",
                            payRs.getDate("pay_period"), gross, deductions, net);

                    totalGross += gross;
                    totalDeductions += deductions;
                    totalNet += net;
                    payCount++;
                }

                if (payCount > 0) {
                    System.out.println("-".repeat(60));
                    System.out.printf("%-12s $%-11.2f $%-11.2f $%-11.2f\n",
                            "TOTALS", totalGross, totalDeductions, totalNet);
                } else {
                    System.out.println("No pay history found for this employee.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error generating pay history report: " + e.getMessage());
        }
    }

    public void generateMonthlyPayByJobTitle(int year, int month) {
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("MONTHLY PAY BY JOB TITLE - %d/%d\n", month, year);
        System.out.println("=".repeat(60));

        String sql = """
                    SELECT e.job_title, SUM(ps.gross_pay) as total_pay, COUNT(ps.pay_id) as pay_count
                    FROM employees e
                    JOIN pay_statements ps ON e.emp_id = ps.emp_id
                    WHERE YEAR(ps.pay_period) = ? AND MONTH(ps.pay_period) = ?
                    GROUP BY e.job_title
                    ORDER BY total_pay DESC
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);
            stmt.setInt(2, month);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-30s %-15s %-10s\n", "Job Title", "Total Pay", "Pay Count");
            System.out.println("-".repeat(60));

            double grandTotal = 0;
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                double totalPay = rs.getDouble("total_pay");
                System.out.printf("%-30s $%-14.2f %-10d\n",
                        rs.getString("job_title"), totalPay, rs.getInt("pay_count"));
                grandTotal += totalPay;
            }

            if (hasData) {
                System.out.println("-".repeat(60));
                System.out.printf("%-30s $%-14.2f\n", "GRAND TOTAL", grandTotal);
            } else {
                System.out.println("No pay data found for the specified month/year.");
            }

        } catch (SQLException e) {
            System.err.println("Error generating job title report: " + e.getMessage());
        }
    }

    public void generateMonthlyPayByDivision(int year, int month) {
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("MONTHLY PAY BY DIVISION - %d/%d\n", month, year);
        System.out.println("=".repeat(60));

        String sql = """
                    SELECT e.division, SUM(ps.gross_pay) as total_pay, COUNT(ps.pay_id) as pay_count
                    FROM employees e
                    JOIN pay_statements ps ON e.emp_id = ps.emp_id
                    WHERE YEAR(ps.pay_period) = ? AND MONTH(ps.pay_period) = ?
                    GROUP BY e.division
                    ORDER BY total_pay DESC
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);
            stmt.setInt(2, month);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-30s %-15s %-10s\n", "Division", "Total Pay", "Pay Count");
            System.out.println("-".repeat(60));

            double grandTotal = 0;
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                double totalPay = rs.getDouble("total_pay");
                System.out.printf("%-30s $%-14.2f %-10d\n",
                        rs.getString("division"), totalPay, rs.getInt("pay_count"));
                grandTotal += totalPay;
            }

            if (hasData) {
                System.out.println("-".repeat(60));
                System.out.printf("%-30s $%-14.2f\n", "GRAND TOTAL", grandTotal);
            } else {
                System.out.println("No pay data found for the specified month/year.");
            }

        } catch (SQLException e) {
            System.err.println("Error generating division report: " + e.getMessage());
        }
    }
}
