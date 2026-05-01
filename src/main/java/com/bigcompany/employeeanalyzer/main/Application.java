package com.bigcompany.employeeanalyzer.main;

import java.util.List;
import java.util.Map;

import com.bigcompany.employeeanalyzer.model.Employee;
import com.bigcompany.employeeanalyzer.reader.CsvEmployeeReader;
import com.bigcompany.employeeanalyzer.service.EmployeeAnalyzer;

public class Application {
	public static void main(String[] args) {
		Map<Integer, Employee> employees = CsvEmployeeReader.readEmployees("src/main/resources/employee.csv");
		EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
		Map<String, List<String>> employeeAnalysisReport = analyzer.analyse(employees);
		analyzer.printReport(employeeAnalysisReport);
	}
}
