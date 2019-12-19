package com.toumb.employee;

import java.io.IOException;
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
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// Create new employee object
		Employee theEmployee = new Employee(id, firstName, lastName, email);
		
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
		int id = Integer.parseInt(request.getParameter("employeeId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// Create new employee object
		Employee theEmployee = new Employee(id, firstName, lastName, email);
				
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
}
