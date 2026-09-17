import java.time.LocalDate;
// PayStatement Class: A pay statement for an employee, detailing their payment and the pay period.
public class PayStatement {
    private int payId; // unique identifier for the pay statement
    private int empId;
    private double amount; 
    private LocalDate startDate;
    private LocalDate endDate;
    private String payPeriod; // this is the start and end date of the pay statement
    
    
    public PayStatement( int empId, double amount, LocalDate startDate, LocalDate endDate) 
    {   //need to find a way to assign a payId as soon as it's made, but each ID must be unique.
        //currently, payID shoudl be 0
        this.empId = empId;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payPeriod = startDate.toString() + " to " + endDate.toString(); // Example format
    }

    
    
    //Getters & setters:
    public int getPayId() {
        return payId;
    }
    // should this function be allowed?
    // public void setPayId(int payId) {
    //     this.payId = payId;
    // }
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    // Converts a payStatement Object into a string
    @Override
    public String toString() {
        return String.format(
                "Pay Statement [ID: %d, Employee ID: %d, Period: %s, Amount: %.2f]",
                payId, empId, payPeriod, amount); // Assuming no deductions for simplicity
    }
}