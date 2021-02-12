package ir.mghhrn.spock;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> getEmployeeById(Long id);
    void increaseWorkedHour(Long employeeId, Long incrementValue);
    List<Employee> getAllEmployees();
}
