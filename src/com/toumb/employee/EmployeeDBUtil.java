package com.toumb.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

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
			// Get a connection to database
			myConn = dataSource.getConnection();
			// Create an SQL statement
			String sql = "SELECT * FROM employee ORDER BY last_name";
			myStatement = myConn.createStatement();
			// Execute the query
			myRes = myStatement.executeQuery(sql);
			// Process the result
			while(myRes.next()) {
				// Retrieve data from row
				int employeeId = myRes.getInt("id");
				String title = myRes.getString("title");
				String firstName = myRes.getString("first_name");
				String lastName = myRes.getString("last_name");
				String jobTitle = myRes.getString("job_title");
				String email = myRes.getString("email");
				String phone = myRes.getString("phone");
				Date dateOfBirth = myRes.getDate("date_of_birth");
				String address = myRes.getString("address");
				String notes = myRes.getString("notes");
				//Create a new Employee and add to the employee list
				Employee employee = new Employee(employeeId, title, firstName, lastName, jobTitle, email, phone, dateOfBirth, address, notes);
				employees.add(employee);
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
	public void addEmployee(Employee employee) throws Exception {
		Connection myConn = null;
		PreparedStatement myStatement = null;
		
		try {
			// Connect to database
			myConn = dataSource.getConnection();
			// Create SQL query for insert
			String sql = "INSERT INTO employee (title, first_name, last_name, job_title, email, phone, date_of_birth, address, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			myStatement = myConn.prepareStatement(sql);
			myStatement.setString(1, employee.getTitle());
			myStatement.setString(2, employee.getFirstName());
			myStatement.setString(3, employee.getLastName());
			myStatement.setString(4, employee.getJobTitle());
			myStatement.setString(5, employee.getEmail());
			myStatement.setString(6, employee.getPhone());
			myStatement.setDate(7, employee.getDateOfBirth());
			myStatement.setString(8, employee.getAddress());
			myStatement.setString(9, employee.getNotes());
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
				String title = myRes.getString("title");
				String firstName = myRes.getString("first_name");
				String lastName = myRes.getString("last_name");
				String jobTitle = myRes.getString("job_title");
				String email = myRes.getString("email");
				String phone = myRes.getString("phone");
				Date dateOfBirth = myRes.getDate("date_of_birth");				
				String address = myRes.getString("address");
				String notes = myRes.getString("notes");
				// Create an Employee
				employee = new Employee(employeeId, title, firstName, lastName, jobTitle, email, phone, dateOfBirth, address, notes);
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
			String sql = "UPDATE employee SET title=?, first_name=?, last_name=?, job_title=?, email=?, phone=?, date_of_birth=?, address=?, notes=? WHERE id=?";
			// Create prepared statement
			myStatement = myConn.prepareStatement(sql);
			// Set parameters
			myStatement.setString(1, employee.getTitle());
			myStatement.setString(2, employee.getFirstName());
			myStatement.setString(3, employee.getLastName());
			myStatement.setString(4, employee.getJobTitle());
			myStatement.setString(5, employee.getEmail());
			myStatement.setString(6, employee.getPhone());
			myStatement.setDate(7, employee.getDateOfBirth());
			myStatement.setString(8, employee.getAddress());
			myStatement.setString(9, employee.getNotes());
			myStatement.setInt(10, employee.getId());
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

    public List<Employee> searchEmployees(String searchKeyword) throws Exception {
        List<Employee> employees = new ArrayList<>();
        
        Connection myConn = null;
        PreparedStatement myStatement = null;
        ResultSet myRes = null;
        
        try {
            // Get a connection to database
            myConn = dataSource.getConnection();
            // Only search by keyword if searchKeyword is not empty
            if(searchKeyword != null && searchKeyword.trim().length() > 0) {
                // Create an SQL statement to search for employees by either first name, last name, job title or email
                String sql = "select * from employee where lower(first_name) like ? or lower(last_name) like ?"
                		   + " or lower(job_title) like ? or lower(email) like ?";
                // Create prepared statement
                myStatement = myConn.prepareStatement(sql);
                String searchKeywordLike = "%" + searchKeyword.toLowerCase() + "%";
                myStatement.setString(1, searchKeywordLike);
                myStatement.setString(2, searchKeywordLike);
                myStatement.setString(3, searchKeywordLike);
                myStatement.setString(4, searchKeywordLike);
            } else {
                // Create an SQL statement to get all employees if search field left blank
                String sql = "select * from employee order by last_name";
                // Create prepared statement
                myStatement = myConn.prepareStatement(sql);
            }
            // Execute the query
            myRes = myStatement.executeQuery();
            // Process the result
            while (myRes.next()) {
            	// Retrieve data from row
                int employeeId = myRes.getInt("id");
                String title = myRes.getString("title");
				String firstName = myRes.getString("first_name");
				String lastName = myRes.getString("last_name");
				String jobTitle = myRes.getString("job_title");
				String email = myRes.getString("email");
				String phone = myRes.getString("phone");
				Date dateOfBirth = myRes.getDate("date_of_birth");
				String address = myRes.getString("address");
				String notes = myRes.getString("notes");
				//Create a new Employee and add to the employee list
				Employee employee = new Employee(employeeId, title, firstName, lastName, jobTitle, email, phone, dateOfBirth, address, notes);
				employees.add(employee);           
            }
            return employees;
        }
        
        finally {
        	// Close all the JDBC objects
            close(myConn, myStatement, myRes);
        }
    }
    
 // Method to retrieve an User using the username and password
 	public User getUser(String username, String password) throws Exception {
 		User user = null;
 		Connection myConn = null;
 		PreparedStatement myStatement = null;
 		ResultSet myRes = null;
 		
 		try {
 			// Connect to database
 			myConn = dataSource.getConnection();
 			// Create an SQL statement
 			String sql = "SELECT * FROM user WHERE username=? AND password=?";
 			// Create prepared statement
 			myStatement = myConn.prepareStatement(sql);
 			// Set parameters
 			myStatement.setString(1, username);
 			myStatement.setString(2, password);
 			// Execute statement
 			myRes = myStatement.executeQuery();
 			// Retrieve data from database row
 			if(myRes.next()) {
				String theUsername = myRes.getString("username");
 				String thePassword = myRes.getString("password");
 				// Create an Employee
 				user = new User(theUsername, thePassword);
 			} else {
 				user = new User("error", "error");
 			}
 			return user;
 			
 		} finally {
 			// Close all the JDBC objects
 			close(myConn, myStatement, myRes);
 		}	
 	}
}
