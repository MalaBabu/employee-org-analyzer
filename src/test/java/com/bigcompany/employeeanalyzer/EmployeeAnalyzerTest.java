package com.bigcompany.employeeanalyzer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.bigcompany.employeeanalyzer.model.Employee;
import com.bigcompany.employeeanalyzer.reader.CsvEmployeeReader;
import com.bigcompany.employeeanalyzer.service.EmployeeAnalyzer;

public class EmployeeAnalyzerTest {

    private Map<Integer, Employee> employees;
    private EmployeeAnalyzer employeeAnalyzer;

    @BeforeEach
    void setUp() {
        employees = CsvEmployeeReader.readEmployees("src/test/resources/employee.csv");
        employeeAnalyzer = new EmployeeAnalyzer();
    }

    private Map<String, List<String>> report() {
        return employeeAnalyzer.analyze(employees);
    }

    private boolean contains(List<String> list, String text) {
        return list != null && list.stream().anyMatch(msg -> msg.contains(text));
    }

    private boolean contains(List<String> list, String text1,String text2) {
        return list != null && list.stream().anyMatch(msg -> msg.contains(text1) && msg.contains(text2));
    }

    @Nested
    class EmployeeDataTests {

        @Test
        void shouldLoadEmployeesSuccessfully() {
            assertNotNull(employees);
            assertEquals(15, employees.size());
        }

        @Test
        void shouldFindEmployeeById() {
            Employee emp = employees.get(123);
            assertNotNull(emp);
            assertEquals("Joe", emp.getFirstName());
        }

        @Test
        void shouldHandleManagerRelationship() {
            Employee emp = employees.get(124);
            assertEquals(123, emp.getManagerId());
        }

        @Test
        void shouldDetectCEO() {
            Employee ceo = employees.get(123);
            assertNull(ceo.getManagerId());
        }
    }


    @Nested
    class SalaryRuleTests {

        @Test
        void shouldReturnCorrectNumberOfSalaryViolations() {
            Map<String, List<String>> r = report();

            int total = r.get(EmployeeAnalyzer.UNDERPAID_ISSUES).size()
                      + r.get(EmployeeAnalyzer.OVERPAID_ISSUES).size();

            assertEquals(7, total);
        }

        @Test
        void shouldDetectUnderpaidManagers() {
            Map<String, List<String>> r = report();
            List<String> underpaid = r.get(EmployeeAnalyzer.UNDERPAID_ISSUES);

            assertTrue(contains(underpaid, "Employee 305"));
            assertTrue(contains(underpaid, "Employee 306"));
            assertTrue(contains(underpaid, "Employee 307"));
            assertTrue(contains(underpaid, "Employee 123"));
            assertTrue(contains(underpaid, "Employee 124"));
        }

        @Test
        void shouldDetectOverpaidManagers() {
            Map<String, List<String>> r = report();
            List<String> overpaid = r.get(EmployeeAnalyzer.OVERPAID_ISSUES);

            assertTrue(contains(overpaid, "Employee 500"));
        }
        @Test
        void shouldNotDetectUnderpaidInOverpaidManagers() {
            Map<String, List<String>> r = report();
            List<String> overpaid = r.get(EmployeeAnalyzer.OVERPAID_ISSUES);

            assertFalse(contains(overpaid, "Employee 305"));
        }
        @Test
        void shouldNotDetectOverpaidInUnderpaidManagers() {
            Map<String, List<String>> r = report();
            List<String> overpaid = r.get(EmployeeAnalyzer.UNDERPAID_ISSUES);

            assertFalse(contains(overpaid, "Employee 500"));
        }
    }


    @Nested
    class ReportingLineTests {

        @Test
        void shouldReturnAllHierarchyViolations() {
            Map<String, List<String>> r = report();
            List<String> hierarchy = r.get(EmployeeAnalyzer.REPORTLINE_ISSUES);
            assertEquals(2, hierarchy.size());
        }

        @Test
        void shouldDetectHierarchyViolations() {
            Map<String, List<String>> r = report();
            List<String> hierarchy = r.get(EmployeeAnalyzer.REPORTLINE_ISSUES);

            assertFalse(contains(hierarchy, "Employee 307","by 1 level"));
            assertTrue(contains(hierarchy, "Employee 308","by 1 level"));
            assertFalse(contains(hierarchy, "Employee 306"));
        }

        @Test
        void shouldDetectNoHierarchyViolationsForCeo() {
    		List<String> reportLineViolations = new ArrayList<>();

    		Employee ceo = employees.get(123);
    		assertNull(ceo.getManagerId());
    		employeeAnalyzer.checkReportingLines(ceo,0,reportLineViolations);
    		assertFalse(contains(reportLineViolations, "Employee 123"));
        }
    }
}