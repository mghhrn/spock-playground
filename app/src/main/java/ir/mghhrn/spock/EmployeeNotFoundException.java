package ir.mghhrn.spock;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long employeeId) {
        super(String.format("Employee with id %d not found!", employeeId));
    }
}
