package com.toumb.employee;

import java.sql.Date;

public class Employee {
	private int id;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String jobTitle;
	private String email;
	private String phone;
	private Date dateOfBirth;
	private String address;
	
	public Employee(int id, String title, String firstName, String middleName, String lastName, String jobTitle,
			String email, String phone, Date dateOfBirth, String address) {
		this.id = id;
		this.title = title;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
	}
	
	public Employee(int id, String title, String firstName, String lastName, String email) {
		this.id = id;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public Employee(int id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public Employee(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", title=" + title + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", jobTitle=" + jobTitle + ", email=" + email + ", phone=" + phone
				+ ", dateOfBirth=" + dateOfBirth + ", address=" + address + "]";
	}
	
}
