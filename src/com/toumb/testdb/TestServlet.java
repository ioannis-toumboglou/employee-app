package com.toumb.testdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Define datasource/connection pool for Resource Injection
	@Resource(name="jdbc/web_employee_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set up printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		// Get connection to database
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResults = null;
		
		try {
			myConnection = dataSource.getConnection();
			// Create SQL statements
			String sql = "SELECT * FROM employee";
			myStatement = myConnection.createStatement();
			
			// Execute SQL queries
			myResults = myStatement.executeQuery(sql);
			
			// Process result
			while(myResults.next()) {
				String email = myResults.getString("email");
				out.println(email);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
