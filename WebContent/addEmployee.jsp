<!DOCTYPE html>

<html>

<head>
	<title>Add Employee</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-employee-style.css">
</head>


<body>

	<div id="wrapper">
		<div id="header">
			<h2>Employee Management System</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Add Employee</h3>
		
		<form action="ControllerServlet" method="GET">
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Title</label></td>
						<td>
							<select name="title">
								<option>-</option>
								<option>Mr</option>
								<option>Mrs</option>
							</select>
						</td> 
					</tr>
					<tr>
						<td><label>First Name</label></td>
						<td><input type="text" name="firstName" /></td> 
					</tr>
					<tr>
						<td><label>Last Name</label></td>
						<td><input type="text" name="lastName" /></td> 
					</tr>
					<tr>
						<td><label>Job Title</label></td>
						<td><input type="text" name="jobTitle" /></td> 
					</tr>
					<tr>
						<td><label>Email</label></td>
						<td><input type="text" name="email" /></td> 
					</tr>
					<tr>
						<td><label>Phone Number</label></td>
						<td><input type="text" name="phone" /></td> 
					</tr>
					<tr>
						<td><label>Date of Birth</label></td>
						<td><input type="date" name="dateOfBirth" /></td> 
					</tr>
					<tr>
						<td><label>Address</label></td>
						<td><input type="text" name="address" /></td> 
					</tr>
					<tr>
						<td><label>Notes</label></td>
						<td>
							<textarea name="notes" placeholder="Add comments" rows="5"></textarea>
						</td> 
					</tr>
					<tr>
						<td><label></label></td>
						<td>
							<input type="submit" value="Save" class="save" />
							<input type="cancel" value="Cancel" class="cancel" onclick="location.href='./';"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>

	</div>

</body>

</html>