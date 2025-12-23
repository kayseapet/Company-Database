
import java.util.List;
import java.util.ArrayList;

public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String division;
    private double salary;
    private boolean fullTime;
    private List<PayStatement> payStatements;

    public Employee() {
        this.payStatements = new ArrayList<>();
    }

    public Employee( String firstName, String lastName, 
            String jobTitle, String division, double salary, boolean fullTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.division = division;
        this.salary = salary;
        this.fullTime = fullTime;
        this.payStatements = new ArrayList<>();
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<PayStatement> getPayStatements() {
        return payStatements;
    }

    public void setPayStatements(List<PayStatement> payStatements) {
        this.payStatements = payStatements;
    }

    @Override
    public String toString() {
        return String.format(
                "Employee [ID: %d, Name: %s %s, Position: %s, Division: %s, Salary: $%.2f, Status: %s]",
                empId, firstName, lastName, jobTitle, division, salary, fullTime ? "Full-Time" : "Part-Time");
    }
}