package com.DCN;

public class Employee {
	
	private int Employ_ID; 
	private String First_Name; 
	private String Last_Name; 
	private String City_Name;
	
	public Employee(int employ_ID,String first_Name,String last_Name,String city_Name) {
		super();
		Employ_ID = employ_ID;
		Last_Name = last_Name;
		First_Name = first_Name;
		City_Name = city_Name;
	}
	
	public int getEmploy_ID() {
		return Employ_ID;
	}
	public void setEmploy_ID(int employ_ID) {
		Employ_ID = employ_ID;
	}
	public String getLast_Name() {
		return Last_Name;
	}
	public void setLast_Name(String last_Name) {
		Last_Name = last_Name;
	}
	public String getFirst_Name() {
		return First_Name;
	}
	public void setFirst_Name(String first_Name) {
		First_Name = first_Name;
	}
	public String getCity_Name() {
		return City_Name;
	}
	public void setCity_Name(String city_Name) {
		City_Name = city_Name;
	} 

}
