package ir.mghhrn.spock;

import org.checkerframework.checker.nullness.Opt;

import java.util.Optional;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String incrementWorkedHourOfEmployeeAndReturnTheirFirstName(Long employeeId, Long incrementValue) {
        Optional<Employee> employeeOptional = employeeRepository.getEmployeeById(employeeId);
        Employee employee = employeeOptional.orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeeRepository.increaseWorkedHour(employeeId, incrementValue);
        return employee.getFirstName();
    }

    public Optional<Employee> sillyGetById(Long employeeId) {
        return employeeRepository.getEmployeeById(employeeId);
    }
}
