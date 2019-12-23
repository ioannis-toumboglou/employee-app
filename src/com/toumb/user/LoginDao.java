package com.toumb.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class LoginDao {
	private DataSource dataSource;

    public boolean validate(User user) throws ClassNotFoundException {
        boolean status = false;
        
        Connection myConn = null;
        PreparedStatement myStatement = null;
        ResultSet myRes = null;
        
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	// Get a connection to database
        	myConn = dataSource.getConnection();
        	// Create an SQL statement
        	String sql = "SELECT * FROM user WHERE username=? AND password=?";
        	// Create prepared statement
        	myStatement = myConn.prepareStatement(sql);
        	// Execute the query
        	myRes = myStatement.executeQuery();
        	// Set parameters
        	myStatement.setString(1, user.getUsername());
        	myStatement.setString(2, user.getPassword());

            myRes = myStatement.executeQuery();
            
            if(myRes.next()) {
            	String dbUsername = myRes.getString("name");
            	String dbPassword = myRes.getString("password");
            	
            	if(username.equals(dbUsername) && password.equals(dbPassword)) {
            		status = true;
            	}
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        
        return status;
    }

    private void printSQLException(SQLException ex) {
    	
        for(Throwable e: ex) {
            if(e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}