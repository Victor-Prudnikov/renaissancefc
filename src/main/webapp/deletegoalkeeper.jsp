<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delete Goalkeeper Record - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Are you sure you want to delete this record?</h1>
	<table class="table1">
		<thead>
			<tr>
				<th><b>№</b></th>
				<th><b>Name</b></th>
				<th><b>Surname</b></th>
				<th><b>Birth Date</b></th>
				<th><b>Age</b></th>
				<th><b>Games</b></th>
				<th><b>Goals</b></th>
				<th><b>Height</b></th>
				<th><b>Weight</b></th>
				<th><b>Conceded</b></th>
				<th><b>Pen.Saved</b></th>
				<th><b>Position</b></th>
			</tr>
		</thead>
		<tbody>
				<tr>
					<td>${keeper.number}</td>
					<td>${keeper.name}</td>
					<td>${keeper.surname}</td>
					<td>${keeper.birthDate}</td>
					<td>${keeper.age}</td>
					<td>${keeper.games}</td>
					<td>${keeper.goals}</td>
					<td>${keeper.height}</td>
					<td>${keeper.weight}</td>
					<td>${keeper.goalsConceded}</td>
					<td>${keeper.penaltySaved}</td>
					<td>${keeper.position}</td>
				</tr>
		</tbody>
	</table>
	<br><br>
	<div class="center">
	<form action="delete" method="POST">
		<input type="hidden" name="typerequest" value="keepers">
		<input type="hidden" name="id" value="${keeper.id}">
  		<input type="submit" value="  OK  ">
  		<input type="submit" value ="Cancel" formaction="teamview">
	</form>
	</div>
</body>
</html>