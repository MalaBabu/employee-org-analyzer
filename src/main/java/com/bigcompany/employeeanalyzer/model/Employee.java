package com.bigcompany.employeeanalyzer.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	
    private final int id;
    private final String firstName;
    private final String lastName;
    private final double salary;
    private final Integer managerId;
    
    private Employee manager;
    private List<Employee> subordinates=new ArrayList<Employee>();

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Employee> subordinates) {
		this.subordinates = subordinates;
	}

	public void addSubordinates(Employee employee) {
		this.subordinates.add(employee);
	}
	
	public int getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public double getSalary() {
		return salary;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public Employee(int id, String firstName, String lastName, double salary, Integer managerId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.managerId = managerId;
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
    public boolean isManager() {
        return !this.subordinates.isEmpty();
    }
    
	public boolean isCEO() {
		return this.managerId == null;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
				+ ", managerId=" + managerId + "]";
	}
	
}
