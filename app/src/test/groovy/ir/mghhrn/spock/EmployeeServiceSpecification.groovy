package ir.mghhrn.spock

import spock.lang.Specification

class EmployeeServiceSpecification extends Specification {

    EmployeeRepository employeeRepository
    EmployeeService employeeService

    def setup() {
        employeeRepository = Mock(EmployeeRepository)
        employeeService = new EmployeeService(employeeRepository)
    }

    def "call once silly method of repository"() {
        when:
        employeeService.sillyGetById(1L)

        then:
        1 * employeeRepository.getEmployeeById(1L)
        0 * _    // don't allow any other interaction

        when:
        employeeService.sillyGetById(2L)

        // Interactions declared in a then: block are scoped to the preceding when: block:
        then:
        1 * employeeRepository.getEmployeeById(_)   // any single argument (including null)
        0 * _    // don't allow any other interaction (strict mocking)
    }


    def "increment work hour of employee with id 1"() {
        when:
        employeeService.incrementWorkedHourOfEmployeeAndReturnTheirFirstName(1L , 22L)

        then:
        1 * employeeRepository.getEmployeeById(1L) >> Optional.of(new Employee(1L, "Alex", "Franco", 2245L))  // Stubbing
        1 * employeeRepository.increaseWorkedHour(1L, !null)
    }
}
