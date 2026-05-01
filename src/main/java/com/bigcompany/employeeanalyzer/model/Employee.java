package com.bigcompany.employeeanalyzer.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	
	private int Id;
	private String firstName;
	private String lastName;
	private double salary;
	private Integer managerId;
    
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
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public Employee(int id, String firstName, String lastName, double salary, Integer managerId) {
		super();
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.managerId = managerId;
	}
	public Employee() {
		super();
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	@Override
	public String toString() {
		return "Employee [Id=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
				+ ", managerId=" + managerId + "]";
	}
	
}
