package com.bigcompany.employeeanalyzer.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.bigcompany.employeeanalyzer.model.Employee;

public class CsvEmployeeReader {

	public static Map<Integer, Employee> readEmployees(String filePath) {
		Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			br.readLine(); // Ignoring header section to map
			String line;
			while ((line = br.readLine()) != null) {
				if (line.trim().isBlank()) {
					continue;
				}
				Employee employee = parseEmployee(line);
				if (employee != null) {
					employeeMap.put(employee.getId(), employee);
				}
			}
			for (Employee employee : employeeMap.values()) {
				if (employee.getManagerId() != null) {
					Employee manager = employeeMap.get(employee.getManagerId());
					if (manager != null) {
						manager.addSubordinates(employee);
						employee.setManager(manager);
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Exception occured while parsing CSV file"+e.getMessage());
		}
		return employeeMap;
	}

	private static Employee parseEmployee(String line) {
		String[] values = line.split(",");
		if (values.length > 0) {
			try {
				int id = 0;
				String firstName = null;
				String lastName = null;
				double salary = 0.0;
				Integer managerId = null;
				id = parseInt(values[0], "id");
				firstName = getTrimmedValue(values[1]);
				lastName = getTrimmedValue(values[2]);
				salary = parseDouble(values[3], "salary");
				if (values.length > 4 && !values[4].isBlank()) {
					managerId = Integer.parseInt(values[4].trim());
				}
				return new Employee(id, firstName, lastName, salary, managerId);
			} catch (Exception e) {
                System.out.println("Skipping invalid row: " + line + " | Reason: " + e.getMessage());
			}
		}
		return null;

	}

	private static String getTrimmedValue(String value) {
		return value != null ? value.trim() : null;
	}
	
	private static int parseInt(String value, String fieldName) {
	    if (value == null || value.trim().isEmpty()) {
	        throw new IllegalArgumentException(fieldName + " cannot be empty");
	    }
	    return Integer.parseInt(value.trim());
	}

	private static double parseDouble(String value, String fieldName) {
	    if (value == null || value.trim().isEmpty()) {
	        throw new IllegalArgumentException(fieldName + " cannot be empty");
	    }
	    return Double.parseDouble(value.trim());
	}

}
