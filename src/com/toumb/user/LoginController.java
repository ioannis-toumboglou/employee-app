package com.toumb.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LoginDao loginDao;

    public void init() {
        loginDao = new LoginDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Get form input
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // Create a user object with the input data
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        
        // Validate user credentials
        try {
            if(loginDao.validate(user)) {
                response.sendRedirect("listEmployees.jsp");
            } else {
            	HttpSession session = request.getSession();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}