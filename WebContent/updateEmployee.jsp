<!DOCTYPE html>

<html>

<head>
	<title>Update Employee</title>
	
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
		<h3>Update Employee</h3>
		
		<form action="ControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="employeeId" value="${THE_EMPLOYEE.id}" />
			
			<table>
				<tbody>
					<tr>
						<td><label><b>Title</b></label></td>
						<td>
							<select name="title">
								<option selected="selected">${THE_EMPLOYEE.title}</option>
								<option>-</option>
								<option>Mr</option>
								<option>Mrs</option>
							</select>
						</td> 
					</tr>
					<tr>
						<td><label><b>First Name</b> *</label></td>
						<td><input type="text" name="firstName" value="${THE_EMPLOYEE.firstName}" /></td> 
					</tr>
					<tr>
						<td><label><b>Last Name</b> *</label></td>
						<td><input type="text" name="lastName" value="${THE_EMPLOYEE.lastName}" /></td> 
					</tr>
					<tr>
						<td><label><b>Job Title</b></label></td>
						<td><input type="text" name="jobTitle" value="${THE_EMPLOYEE.jobTitle}" /></td> 
					</tr>
					<tr>
						<td><label><b>Email</b> *</label></td>
						<td><input type="text" name="email" value="${THE_EMPLOYEE.email}" /></td> 
					</tr>
					<tr>
						<td><label><b>Phone Number</b></label></td>
						<td><input type="text" name="phone" value="${THE_EMPLOYEE.phone}" /></td> 
					</tr>
					<tr>
						<td><label><b>Date of Birth</b> *</label></td>
						<td><input type="date" name="dateOfBirth" value="${THE_EMPLOYEE.dateOfBirth}" /></td> 
					</tr>
					<tr>
						<td><label><b>Address</b></label></td>
						<td><input type="text" name="address" value="${THE_EMPLOYEE.address}" /></td> 
					</tr>
					<tr>
						<td><label><b>Notes</b></label></td>
						<td>
							<textarea name="notes" placeholder="Add comments" rows="5">${THE_EMPLOYEE.notes}</textarea>
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
		
		<div id="fields_note">
			(*) required fields
		</div>
		
		<div style="clear: both;"></div>
		
	</div>

</body>

</html>