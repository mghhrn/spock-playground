package ir.mghhrn.spock

import spock.lang.Shared
import spock.lang.Specification

class EmployeeRepositorySpecification extends Specification {

    /*** fields ***/
    // static fields are shared and should be used for constants
    static final PI = 3.141592654

    // a shared object
    @Shared seenEmployeeIds = new ArrayList<Long>();

    // This type of field will be instantiated for each 'feature method'
    def someInt = 12
    EmployeeRepository employeeRepository


    /*** fixture methods ***/
    /****  The invocation order:
     super.setupSpec
     sub.setupSpec
     super.setup
     sub.setup
     feature method
     sub.cleanup
     super.cleanup
     sub.cleanupSpec
     super.cleanupSpec
     */
    def setupSpec() {}    // runs once -  before the first feature method
    def setup() {         // runs before every feature method
        employeeRepository = new EmployeeRepositoryImpl()
    }
    def cleanup() {}      // runs after every feature method
    def cleanupSpec() {}  // runs once -  after the last feature method


    /*** feature methods ***/
    def "employee with id 1 is existed"() {
        given:
        // we could instead of using 'setup()' fixture method, initialize the
        // employeeRepository in this 'given' block
        Long employeeId = 1L
        Employee employee

        // when-then should be used to describe methods with side effects
        when:
        employee = employeeRepository.getEmployeeById(employeeId).get()
        someInt += 1

        then:
        verifyAll(employee) {
            firstName == "John"
            lastName == "Doe"
        }
        someInt == 13
        notThrown(NoSuchElementException)

        // the expect block should be used for purely functional methods
        expect:
        employee.totalWorkedHour == 100L
    }


    def "someInt field is intact for each feature method"() {
        // notice that in previous method, we increased the value of 'someInt' but it must stay intact for each method
        expect:
        someInt == 12
    }


    // Spock provides a way to attach textual descriptions to blocks and we used it next feature method
    def "employee with id 6 is not existed"() {
        given: "initialize Long variable with value 6"
        Long employeeId = 6L

        when: "try to retrieve the employee with initialized id"
        employeeRepository.getEmployeeById(employeeId).get()

        then: "verify that the employee did not exist by observing the NoSuchElementException"
        NoSuchElementException e = thrown()
    }

    // data-driven testing
    def "employees with id 1, 2, and 3 are existed with their exact 'workedHour'    "() {
        given:
        Employee employee = employeeRepository.getEmployeeById(id).get()

        expect:
        with(employee) {
            employeeMatchesWithIdAndFirstNameAndWorkedHour(employee, name, workedHour)
            getId() == id
        }

        // the test will be repeated for each data set that we have defined here
        where:
        id << [1L, 2L, 3L]
        name << ["John", "Jack", "Sherlock"]
        workedHour << [100L, 777L, 345L]
    }


    // we use 'and' label to describe logically different parts of a block
    def "sample cleanup"() {
        given: "initialize a file variable"
        def file = new File("temp-file")

        and: "create the file"
        file.createNewFile()

        and: "init some silly variable :)"
        def number = 1

        when:
        number++

        then:
        number == 2

        // if you comment the 'cleanup' block and run the test, you will see the file will be existed
        //  even after the test has been finished
        cleanup:
        file?.delete()  // Groovy’s safe dereference operator '?.' simplifies writing defensive code
    }


    /*** helper methods ***/
    void employeeMatchesWithIdAndFirstNameAndWorkedHour(Employee e, String firstName, Long workedHour) {
        // we factored out some conditions into this helper method
        // pay attention that the method returns 'void' and
        //  each condition is changed to EXPLICIT condition by adding 'assert' keyword before it
        assert e.getFirstName() == firstName
        assert e.getTotalWorkedHour() == workedHour
    }


    /***
     * Some notes:
     * Spock provides an interception-based extension mechanism.
     * Extensions are activated by annotations called directives.
     * Currently, Spock ships with the following directives:
     *  '@Timeout, @Ignore, @IgnoreRest, @FailsWith'
     * We can implement our own directives and extensions.
     */
}
