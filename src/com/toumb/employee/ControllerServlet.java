package com.toumb.employee;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/ControllerServlet")
public class ControllerServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeDBUtil employeeDBUtil;
	
	@Resource(name="jdbc/web_employee_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			employeeDBUtil = new EmployeeDBUtil(dataSource);
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Read the command
			String command = request.getParameter("command");
			// If command is missing, default is the list of employees
			if(command == null) {
				command = "LIST";
			}
			
			switch(command) {
				case "LIST":
					listEmployees(request, response);
					break;
				case "ADD":
					addEmployee(request, response);
					break;
				case "LOAD":
					loadEmployee(request, response);
					break;
				case "UPDATE":
					updateEmployee(request, response);
					break;
				case "DELETE":
					deleteEmployee(request, response);
					break;
	            case "SEARCH":
	                searchEmployees(request, response);
	                break;
	            case "LOGIN":
	            	loadUser(request, response);
				default:
					listEmployees(request, response);
			}
			listEmployees(request, response);
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read employee id from form
		String employeeId = request.getParameter("employeeId");
		// Delete employee from database
		employeeDBUtil.deleteEmployee(employeeId);
		// Send to employee list page
		listEmployees(request, response);
	}

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read employee info from form
		int id = Integer.parseInt(request.getParameter("employeeId"));
		String title = request.getParameter("title");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String jobTitle = request.getParameter("jobTitle");
		String department = request.getParameter("department");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String stringDateOfBirth = request.getParameter("dateOfBirth");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateOfBirth = sdf.parse(stringDateOfBirth);
		java.sql.Date dateOfBirthSQL = new java.sql.Date(dateOfBirth.getTime());
		
		// Add one day, as every time a date is updated, the system subtracts one day
		// Couldn't find a better solution
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOfBirthSQL);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		dateOfBirthSQL = new Date(cal.getTimeInMillis());
		
		String address = request.getParameter("address");
		String notes = request.getParameter("notes");
		
		// Create new employee object
		Employee theEmployee = new Employee(id, title, firstName, lastName, jobTitle, department, email, phone, dateOfBirthSQL, address, notes);
		
		// Update database
		employeeDBUtil.updateEmployee(theEmployee);
		
		// Send back to employee list page
		listEmployees(request, response);
	}

	private void loadEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read employee id from form
		String employeeId = request.getParameter("employeeId");
	
		// Get employee from database
		Employee theEmployee = employeeDBUtil.getEmployee(employeeId);
		
		// Place employee in the request attribute
		request.setAttribute("THE_EMPLOYEE", theEmployee);
		
		// Send to JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/updateEmployee.jsp");
		dispatcher.forward(request, response);
		
	}

	private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read employee info from form
		String title = request.getParameter("title");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String jobTitle = request.getParameter("jobTitle");
		String department = request.getParameter("department");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		// Need to convert the birth date from util to sql, in order to store it in the database
		java.util.Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth"));
		java.sql.Date dateOfBirthSQL = new java.sql.Date(dateOfBirth.getTime());
		
		// Add one day, as every time a date is updated, the system subtracts one day
		// Couldn't find a better solution
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOfBirthSQL);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		dateOfBirthSQL = new Date(cal.getTimeInMillis());
		
		String address = request.getParameter("address");
		String notes = request.getParameter("notes");
		
		// Create new employee object
		Employee theEmployee = new Employee(title, firstName, lastName, jobTitle, department, email, phone, dateOfBirthSQL, address, notes);
				
		// Update database
		employeeDBUtil.addEmployee(theEmployee);
		
		// Send back to employee list page
		listEmployees(request, response);
		
	}

	private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Get employees from DB Util
		List<Employee> employees = employeeDBUtil.getEmployees();
		
		// Add employees to request
		request.setAttribute("EMPLOYEE_LIST", employees);
		
		// Send to JSP page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/listEmployees.jsp");
		dispatcher.forward(request, response);
				
	}
	
	private void searchEmployees(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Get keyword from form
        String searchKeyword = request.getParameter("searchKeyword");
        
        // Search employees
        List<Employee> employees = employeeDBUtil.searchEmployees(searchKeyword);
        
        // Add employees to the request
        request.setAttribute("EMPLOYEE_LIST", employees);
                
        // Send to JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/listEmployees.jsp");
        dispatcher.forward(request, response);
    }
	
	private void loadUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read employee id from form
		String username = request.getParameter("username");
		String password = request.getParameter("password");
	
		// Get employee from database
		User user = employeeDBUtil.getUser(username, password);
		
		// Check credentials
		if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
			// Send to JSP Employee list page
			listEmployees(request, response);
		} else if((user.getUsername() == "error") || (user.getPassword() == "error")) {
			// Send to JSP error page
			RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
	        dispatcher.forward(request, response);
		}
		
	}
}
