package com.bigcompany.employeeanalyzer.main;

import java.util.List;
import java.util.Map;

import com.bigcompany.employeeanalyzer.model.Employee;
import com.bigcompany.employeeanalyzer.reader.CsvEmployeeReader;
import com.bigcompany.employeeanalyzer.printer.ReportPrinter;
import com.bigcompany.employeeanalyzer.service.EmployeeAnalyzer;

public class Application {
	public static void main(String[] args) {
		
		// Step 1:read Employees from the CSV file and build organization hierarchy 
		Map<Integer, Employee> employees = CsvEmployeeReader.readEmployees("src/main/resources/employee.csv"); 
		EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
		
		// Step 2: Perform salary and reporting structure analysis 
		Map<String, List<String>> employeeAnalysisReport = analyzer.analyze(employees);
		
		
		// Step 3: Print the formatted report to console
		ReportPrinter reportPrinter=new ReportPrinter();
		reportPrinter.printReport(employeeAnalysisReport);
	}
}
