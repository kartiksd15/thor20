<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="show"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successs</title>
</head>
<body>



	<h4>${Message}</h4>
	<a href="/com.xorkz.Registration/Register" modelAttribute="user">Back</a>

		<table>
			<tr>
				<td>User ID</td>
				<td><show:out value="${user.userId}" /></td>
			</tr>

			<tr>
				<td>Email</td>
				<td><show:out value="${user.email}" /></td>
			</tr>

			<tr>
				<td>Phone No</td>
				<td><show:out value="${user.phoneNo}" /></td>
			</tr>

			<tr>
				<td>Course</td>
				<td><show:out value="${user.course}" /></td>
			</tr>
			<tr>
				<td>Generate Password</td>
				<td><show:out value="${user.password}" /></td>
			</tr>
		</table>
	</show>

</body>
</html>