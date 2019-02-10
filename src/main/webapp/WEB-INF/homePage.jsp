<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard - Home Page</title>
</head>
<body>
<h1>Admin Dashboard - Home Page</h1>
<a href="/logout">Logout</a>
<h1>Welcome ${user.getFirstName()} ${user.getLastName()}!</h1>
<%-- <p>${users}</p> --%>
<table border="1" style="width: 1000px; text-align: center">
	<thead>
		<tr>
			<td>Id</td>
			<td>Name</td>
			<td>Email</td>
			<td>Role</td>
			<td>Sign Up Date</td>
			<td>Last Sign In Date</td>
			<td>Action</td>
		</tr>
		<c:forEach var="index" items="${users
		}">
			<tr>
				<td>${index.getId()}</td>
				<td>${index.getFirstName()} ${index.getLastName()}</td>
				<td>${index.getEmail()}</td>
				<td>${index.getRole()}</td>
				<td>${index.getCreatedAt()}</td>
				<td>${index.getSignInDate()}</td>
				<td>
					<c:choose>
						<c:when test="${index.getRole() == 'super'}">
							<p>Super</p>
						</c:when>
						<c:when test="${index.getRole() == 'admin'}">
							<p>Administrator</p>
							<c:if test="${user.getRole() == 'super'}">
								<form method="post" action="/deleteUser">
									<input type="hidden" name="user_id" value="${index.getId()}">
									<input type="submit" value="Delete">
								</form>
							</c:if>
						</c:when>
						<c:otherwise>
							<form method="post" action="/deleteUser">
								<input type="hidden" name="user_id" value="${index.getId()}">
								<input type="submit" value="Delete">
							</form>
							<br>
							<form method="post" action="/makeAdmin">
								<input type="hidden" name="user_id" value="${index.getId()}">
								<input type="submit" value="Make Admin">
							</form>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</thead>
</table>
</body>
</html>