package com.toumb.employee;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class EmployeeDBUtil {
	private DataSource dataSource;
	
	// Constructor
	public EmployeeDBUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	// Method to retrieve Employees from database
	public List<Employee> getEmployees() throws Exception {
		List<Employee> employees = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStatement = null;
		ResultSet myRes = null;
		
		try {
			// Get a connection
			myConn = dataSource.getConnection();
			// Create an SQL statement
			String sql = "SELECT * FROM employee ORDER BY last_name";
			myStatement = myConn.createStatement();
			// Execute the query
			myRes = myStatement.executeQuery(sql);
			// Process the result
			while(myRes.next()) {
				// Retrieve data from row
				int id = myRes.getInt("id");
				String firstName = myRes.getString("first_name");
				String lastName = myRes.getString("last_name");
				String email = myRes.getString("email");
				//Create a new Employee and add to the employee list
				Employee tempEmployee = new Employee(id, firstName, lastName, email);
				employees.add(tempEmployee);
			}
			return employees;
			
		} finally {
			// Close all the JDBC objects
			close(myConn, myStatement, myRes);
		}
	}

	// Method to close JDBC objects
	private void close(Connection myConn, Statement myStatement, ResultSet myRes) {
		try {
			if(myRes != null)
				myRes.close();
			if(myStatement != null)
				myStatement.close();
			if(myConn != null)
				myConn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method to insert an Employee in the database
	public void addEmployee(Employee theEmployee) throws Exception {
		Connection myConn = null;
		PreparedStatement myStatement = null;
		
		try {
			// Connect to database
			myConn = dataSource.getConnection();
			// Create SQL query for insert
			String sql = "INSERT INTO employee (first_name, last_name, email) VALUES (?, ?, ?)";
			myStatement = myConn.prepareStatement(sql);
			myStatement.setString(1, theEmployee.getFirstName());
			myStatement.setString(2, theEmployee.getLastName());
			myStatement.setString(3, theEmployee.getEmail());
			// Execute SQL query
			myStatement.execute();
		} finally {
			// Close all the JDBC objects
			close(myConn, myStatement, null);
		}
	}
	
	// Method to retrieve an Employee using the id
	public Employee getEmployee(String theEmployeeId) throws Exception {
		Employee employee = null;
		Connection myConn = null;
		PreparedStatement myStatement = null;
		ResultSet myRes = null;
		int employeeId;
		
		try {
			// Convert employee id to int
			employeeId = Integer.parseInt(theEmployeeId);
			// Connect to database
			myConn = dataSource.getConnection();
			// Create an SQL statement
			String sql = "SELECT * FROM employee WHERE id=?";
			// Create prepared statement
			myStatement = myConn.prepareStatement(sql);
			// Set parameters
			myStatement.setInt(1, employeeId);
			// Execute statement
			myRes = myStatement.executeQuery();
			// Retrieve data from database row
			if(myRes.next()) {
				String firstName = myRes.getString("first_name");
				String lastName = myRes.getString("last_name");
				String email = myRes.getString("email");
				// Create an Employee
				employee = new Employee(employeeId, firstName, lastName, email);
			} else {
				throw new Exception("Unable to find Employee Id: " + employeeId);
			}
			return employee;
			
		} finally {
			// Close all the JDBC objects
			close(myConn, myStatement, myRes);
		}	
	}
	
	// Method to update an Employee record
	public void updateEmployee(Employee employee) throws Exception {
		Connection myConn = null;
		PreparedStatement myStatement = null;
		
		try {
			// Connect to database
			myConn = dataSource.getConnection();
			// Create an SQL statement
			String sql = "UPDATE employee SET first_name=?, last_name=?, email=? WHERE id=?";
			// Create prepared statement
			myStatement = myConn.prepareStatement(sql);
			// Set parameters
			myStatement.setString(1, employee.getFirstName());
			myStatement.setString(2, employee.getLastName());
			myStatement.setString(3, employee.getEmail());
			myStatement.setInt(4, employee.getId());
			// Execute SQL query
			myStatement.execute();
		} finally {
			// Close all the JDBC objects
			close(myConn, myStatement, null);
		}
	}
	
	// Method to delete an Employee using the id
	public void deleteEmployee(String theEmployeeId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStatement = null;
		
		try {
			// Convert employee id to integer
			int employeeId = Integer.parseInt(theEmployeeId);
			// Connect to database
			myConn = dataSource.getConnection();
			// Create an SQL statement
			String sql = "DELETE FROM employee WHERE id=?";
			// Create prepared statement
			myStatement = myConn.prepareStatement(sql);
			// Set parameters
			myStatement.setInt(1, employeeId);
			// Execute statement
			myStatement.execute();
		} finally {
			// Close all the JDBC objects
			close(myConn, myStatement, null);
		}
	}
}
