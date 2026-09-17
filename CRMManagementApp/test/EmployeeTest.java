import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    @DisplayName("Success Case: Changing and Retriving Employee's full name")
    void testFullName() {
        /* functions tested: Employee(), set/get FirstName, set/get LastName, set/get FullName*/
        Employee emp = new Employee();
        emp.setFirstName("John");
        emp.setLastName("Doe");

        assertEquals(String.format("%s %s",emp.getFirstName(), emp.getLastName()), emp.getFullName(), "Full name should be first name + space + last name");
    }

    @Test
    void testEmployeeGettersAndSetters(){
        /*functions tested (both get() and set()): fullTime, JobTitle, Division, Salary */
        Employee emp = new Employee();
        emp.setFullTime(false);
        emp.setJobTitle("Engineer");
        emp.setDivision("Data");
        emp.setSalary(10000);
        Employee employee = new Employee(null,null,"Engineer","Data",10000,false);
        // assert that all parameters in both emp and employee have the same value
        assertTrue((emp.getJobTitle() == employee.getJobTitle()) && (emp.getDivision() == employee.getDivision()) && (emp.getSalary() == employee.getSalary()) && (emp.getFullTime() == employee.getFullTime()));
        assertNotEquals(employee, emp);;
    }   

    @Test
    @DisplayName("Success Case: Test Full-Time Status String")
    void testToStringStatus() {
        Employee empFT = new Employee("Jane", "Doe", "Manager", "HR", 60000.0, true);
        Employee empPT = new Employee("Bob", "Ross", "Artist", "Design", 40000.0, false);

        assertTrue(empFT.toString().contains("Full-Time"));
        assertTrue(empPT.toString().contains("Part-Time"));
    }
}