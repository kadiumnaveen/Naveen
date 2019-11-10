package com.newDCN;

public class Employee {
	
	private String name;
	private String designation;
	private String surname;
	private int employeeId;
	private Department dept;
	private Student stdnt;
	

	public Employee(){
		
	}
	public Employee(String s){
		this.name =s;
		
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
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	public Student getStdnt() {
		return stdnt;
	}
	public void setStdnt(Student stdnt) {
		this.stdnt = stdnt;
	}
	

}
