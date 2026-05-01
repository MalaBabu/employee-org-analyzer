package com.bigcompany.employeeanalyzer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bigcompany.employeeanalyzer.model.Employee;
import com.bigcompany.employeeanalyzer.printer.ReportPrinter;

public class EmployeeAnalyzer {
	
	public static final String UNDERPAID_ISSUES = "underpaidManagers";
	public static final String OVERPAID_ISSUES = "overpaidManagers";
	public static final String REPORTLINE_ISSUES = "reportLineViolations";
	
	private static final double MIN_SALARY_FACTOR = 1.2;
	private static final double MAX_SALARY_FACTOR = 1.5;
	private static final int MAX_REPORT_LEVEL = 5; // 4 managers + CEO

	/**
	 * Analyzes all employees to identify:
	 * - Managers who are underpaid or overpaid based on the average salary of their direct subordinates
	 * - Employees whose reporting line depth exceeds the allowed threshold (more than 4 levels from CEO)
	 *
	 * @param employees map of employeeId to Employee objects
	 * @return structured report containing salary and reporting line violations
	 */
	public Map<String, List<String>> analyze(Map<Integer, Employee> employees) {

		Map<String, List<String>> employeeReport = new HashMap<>();
		List<String> reportLineViolations = new ArrayList<>();
		List<String> overpaidSalaryViolation = new ArrayList<>();
		List<String> underpaidSalaryViolation = new ArrayList<>();
		Employee ceo = null;
		for (Employee employee : employees.values()) {

			// 1. Manager Salary Validation

			if (employee.isManager()) {
				evaluateSalary(employee, underpaidSalaryViolation, overpaidSalaryViolation);
			}

			if (employee.isCEO()) {
				ceo = employee;
			}

		}
		
		// 2. Reporting level Validation
		checkReportingLines(ceo, 0, reportLineViolations);

		// collecting the report
		employeeReport.put(UNDERPAID_ISSUES, underpaidSalaryViolation);
		employeeReport.put(OVERPAID_ISSUES, overpaidSalaryViolation);
		employeeReport.put(REPORTLINE_ISSUES, reportLineViolations);
		return employeeReport;
	}
	
	
	

	
	/**
	 * Traverses the hierarchy from CEO and identifies employees
	 * whose reporting depth exceeds the allowed limit.
	 */
	public void checkReportingLines(Employee employee, int level, List<String> reportLineViolations) {
		if (employee != null) {
			if (level > MAX_REPORT_LEVEL) {
				int excessLevel = level - MAX_REPORT_LEVEL;
				reportLineViolations.add(ReportPrinter.formatReportingLineMessage(employee, excessLevel));
			}
			for (Employee sub : employee.getSubordinates()) {
				checkReportingLines(sub, level + 1, reportLineViolations);
			}
		}
	}
	
	
	/**
	 * Evaluates whether a manager's salary is within the acceptable range
	 * based on the average salary of their direct subordinates.
	 *
	 * A manager is:
	 * - underpaid if their salary is less than 20% above the average
	 * - overpaid if their salary exceeds 50% above the average
	 */
	private void evaluateSalary(Employee employee, List<String> underpaid, List<String> overpaid) {

		double avg = employee.getSubordinates().stream().mapToDouble(Employee::getSalary).average().orElse(0.0);

		double min = avg * MIN_SALARY_FACTOR;
		double max = avg * MAX_SALARY_FACTOR;

		// Minimum salary validation check
		double salary=employee.getSalary();
		if (employee.getSalary() < min) {
			underpaid.add(ReportPrinter.formatUnderpaidMessage(employee, (min-salary)));
		}
		// Maximum salary validation check
		if (employee.getSalary() > max) {
			overpaid.add(ReportPrinter.formatOverpaidMessage(employee, (salary-max)));
		}
	}

}
