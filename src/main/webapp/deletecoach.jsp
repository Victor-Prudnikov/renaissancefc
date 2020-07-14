<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delete Coach Record - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Are you sure you want to delete this record?</h1>
	<table class="table1">
		<thead>
			<tr>
				<th><b>Name</b></th>
				<th><b>Surname</b></th>
				<th><b>Birth Date</b></th>
				<th><b>Age</b></th>
				<th><b>Games</b></th>
				<th><b>Post</b></th>
			</tr>
		</thead>
		<tbody>
				<tr>
					<td>${coach.name}</td>
					<td>${coach.surname}</td>
					<td>${coach.birthDate}</td>
					<td>${coach.age}</td>
					<td>${coach.games}</td>
					<td>${coach.post}</td>
				</tr>
		</tbody>
	</table>
	<br><br>
	<div class="center">
	<form action="delete" method="POST">
		<input type="hidden" name="typerequest" value="coaches">
		<input type="hidden" name="id" value="${coach.id}">
  		<input type="submit" value="  OK  ">
  		<input type="submit" value ="Cancel" formaction="coachesview">
	</form>
	</div>
</body>
</html>