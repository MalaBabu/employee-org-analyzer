package com.bigcompany.employeeanalyzer.printer;

import java.util.List;
import java.util.Map;

import com.bigcompany.employeeanalyzer.model.Employee;
import com.bigcompany.employeeanalyzer.service.EmployeeAnalyzer;

public class ReportPrinter {

	/**
	 * Prints the organization analysis report to the console.
	 *
	 * Includes:
	 * - Underpaid managers
	 * - Overpaid managers
	 * - Employees with excessive reporting level
	 */
	public void printReport(Map<String, List<String>> report) {
		System.out.println("\n************************ ORGANIZATION ANALYSIS REPORT ************************\n");
		System.out.println("UNDERPAID MANAGERS:");
		printSection(report,EmployeeAnalyzer.UNDERPAID_ISSUES);
		System.out.println("\nOVERPAID MANAGERS:");
		printSection(report,EmployeeAnalyzer.OVERPAID_ISSUES);
		System.out.println("\nEMPLOYEES WITH TOO LONG REPORTING LINE:");
		printSection(report,EmployeeAnalyzer.REPORTLINE_ISSUES);

	}

	public  void printSection(Map<String, List<String>> report, String key) {
		List<String> list = report.get(key);
		if (list == null || list.isEmpty()) {
			list = List.of("-None");
		}
		list.forEach(System.out::println);
	}
	
	public static String formatUnderpaidMessage(Employee e, double amount) {
		return String.format("- Employee %d (%s) is underpaid by %.2f", e.getId(), e.getFullName(), amount);
	}
	public static String formatOverpaidMessage(Employee e, double amount) {
		return String.format("- Employee %d (%s) is overpaid by %.2f", e.getId(), e.getFullName(), amount);
	}

	public static  String formatReportingLineMessage(Employee employee, int excessLevel) {
		return String.format("- Employee %d (%s) exceeds by %d level%s", employee.getId(), employee.getFullName(),
				excessLevel, excessLevel > 1 ? "s" : "");
	}
}
