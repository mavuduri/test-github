package com.chase.model;

public class EmpCsv {

	private int empId;
	private String empName;
	private int salary;
	private String street;
	private String city;
	private String state;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "EmpCsv [empId=" + empId + ", empName=" + empName + ", salary=" + salary + ", street=" + street
				+ ", city=" + city + ", state=" + state + "]";
	}
	
	
}
