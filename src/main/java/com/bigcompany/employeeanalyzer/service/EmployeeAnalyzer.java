package com.bigcompany.employeeanalyzer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bigcompany.employeeanalyzer.model.Employee;

public class EmployeeAnalyzer {
	public static final String UNDERPAID_ISSUES = "underpaidManagers";
	public static final String OVERPAID_ISSUES = "overpaidManagers";
	public static final String REPORTLINE_ISSUES = "reportLineViolations";

	public Map<String, List<String>> analyse(Map<Integer, Employee> employees) {

	    Map<String, List<String>> employeeReport = new HashMap<>();
	    List<String> reportLineViolationList = new ArrayList<>();
	    List<String> overpaidSalaryViolation = new ArrayList<>();
	    List<String> underpaidSalaryViolation = new ArrayList<>();

	    for (Employee employee : employees.values()) {
	    	   // ===== MANAGER SALARY CHECK =====
	        if (employee.getSubordinates() != null && !employee.getSubordinates().isEmpty()) {
	            double averageSubordinateSalary = employee.getSubordinates()
	                    .stream()
	                    .mapToDouble(Employee::getSalary)
	                    .average()
	                    .orElse(0.0);
	            
	            double min = averageSubordinateSalary * 1.2;
	            double max = averageSubordinateSalary * 1.5;
	            
	            if (employee.getSalary() < min) {
	                underpaidSalaryViolation.add(String.format(
	                        "- Employee %d (%s) is underpaid by %.2f",
	                        employee.getId(),
	                        employee.getFullName(),
	                        (min - employee.getSalary())
	                ));
	            }
	            
	            if (employee.getSalary() > max) {
	                overpaidSalaryViolation.add(String.format(
	                        "- Employee %d (%s) is overpaid by %.2f",
	                        employee.getId(),
	                        employee.getFullName(),
	                        (employee.getSalary() - max)
	                ));
	            }
	        }

	        // ===== REPORTING LINE CHECK =====
	        int reportingLines = getReportingLines(employee);
	        if (reportingLines > 4) {
	            int excessLevel = reportingLines - 4;
	            reportLineViolationList.add(String.format(
	                    "- Employee %d (%s) exceeds by %d level%s",
	                    employee.getId(),
	                    employee.getFullName(),
	                    excessLevel,
	                    excessLevel > 1 ? "s" : ""
	            ));
	        }
	    }
	    employeeReport.put(UNDERPAID_ISSUES, underpaidSalaryViolation);
	    employeeReport.put(OVERPAID_ISSUES, overpaidSalaryViolation);
	    employeeReport.put(REPORTLINE_ISSUES, reportLineViolationList);
	    return employeeReport;
	}
	public int getReportingLines(Employee emp) {
			int reportingLevel = 0;
			Employee current = emp;
			while (current.getManager() != null) {
				reportingLevel++;
				current = current.getManager();
			}
			return reportingLevel;
	}

	public void printReport(Map<String, List<String>> report) {
		System.out.println("\n************************ ORGANIZATION ANALYSIS REPORT ************************\n");
		System.out.println("UNDERPAID MANAGERS:");
		report.getOrDefault(UNDERPAID_ISSUES, List.of()).forEach(System.out::println);
		System.out.println("\nOVERPAID MANAGERS:");
		report.getOrDefault(OVERPAID_ISSUES, List.of()).forEach(System.out::println);
		System.out.println("\nEMPLOYEES WITH TOO LONG REPORTING LINE:");
		report.getOrDefault(REPORTLINE_ISSUES, List.of()).forEach(System.out::println);
	}
}
