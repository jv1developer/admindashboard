<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Details</title>
</head>
<body>
<h1>Admin Dashboard - Details</h1>
<a href="/logout">Logout</a>
<h1>Welcome ${user.getFirstName()} ${user.getLastName()}!</h1>
<h2>Here are your details:</h2>
<p>ID: ${user.getId()}</p>
<p>First Name: ${user.getFirstName()}</p>
<p>Last Name: ${user.getLastName()}</p>
<p>Role: ${user.getRole()}</p>
<p>Sign Up Date: ${user.getCreatedAt()}</p>
<p>Last Sign In Date: ${user.getSignInDate()}</p>
</body>
</html>