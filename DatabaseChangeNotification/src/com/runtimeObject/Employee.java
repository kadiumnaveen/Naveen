package com.runtimeObject;

public class Employee {
	
	private int Id;
	private String name;
	private String designation;
	private String surname;
	private Department dept;

	public Employee() {

	}

	public Employee(String name, String designation, String surname, int employeeId, Department dept) {
		super();
		this.name = name;
		this.designation = designation;
		this.surname = surname;
		this.Id = employeeId;
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

}
