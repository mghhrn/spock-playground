package ir.mghhrn.spock;

import java.util.*;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final List<Employee> employeeList = new ArrayList<>(Arrays.asList(
            new Employee(1L, "John", "Doe", 100L),
            new Employee(2L, "Jack", "Sparrow", 777L),
            new Employee(3L, "Sherlock", "Holmes", 345L)
    ));

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public void increaseWorkedHour(Long employeeId, Long incrementValue) {
        Employee employee = employeeList.stream()
                .filter(e -> e.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employee.setTotalWorkedHour(employee.getTotalWorkedHour() + incrementValue);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(employeeList);
    }
}
