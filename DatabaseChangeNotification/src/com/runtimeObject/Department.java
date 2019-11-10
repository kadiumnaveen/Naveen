package com.runtimeObject;

public class Department {
	
	private String depName;
	private String[] funtions;
	
	
	public Department(String depName, String[] funtions) {
		super();
		this.depName = depName;
		this.funtions = funtions;
	}
	
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String[] getFuntions() {
		return funtions;
	}
	public void setFuntions(String[] funtions) {
		this.funtions = funtions;
	}
	

}
