<!DOCTYPE html>

<html>

<head>
	<meta charset="ISO-8859-1">
	<title>System Login</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-employee-style.css">
</head>

<body>

	<div align="center">
		<h2>System Login</h2>
  		<form action="<%=request.getContextPath()%>/login" method="POST">
   
	   		<table>
	    		<tr>
	     			<td>
	     				<login-label><b>Username</b>
	     					<input type="text" name="username" required="required" class="login-input" />
	     				</login-label>
	     			</td>
	    		</tr>
	    		<tr>
	     			<td>
	     				<login-label><b>Password</b>
	     					<input type="password" name="password" required="required" class="login-input" />
	     				</login-label>
	     			</td>
	    		</tr>
			</table>
	   
	   		<input type="submit" value="Login" id="login-button" />
  		</form>
 	</div>

</body>

</html>