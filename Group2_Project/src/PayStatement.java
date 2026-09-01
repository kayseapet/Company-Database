import java.time.LocalDate;

public class PayStatement {
    private int payId; // Unique identifier for the pay statement
    private int empId;
    private double amount;
    // Assuming payPeriod is a date representing the pay period for this statement  
    private LocalDate startDate;
    private LocalDate endDate;
    private String payPeriod; // Assuming this is the date of the pay statement

    

    public PayStatement() {
    }

    public PayStatement( int empId, double amount, LocalDate startDate, LocalDate endDate) 
    {
        
        this.empId = empId;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payPeriod = startDate.toString() + " to " + endDate.toString(); // Example format
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

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

    @Override
    public String toString() {
        return String.format(
                "Pay Statement [ID: %d, Employee ID: %d, Period: %s, Amount: %.2f]",
                payId, empId, payPeriod, amount); // Assuming no deductions for simplicity
    }
}