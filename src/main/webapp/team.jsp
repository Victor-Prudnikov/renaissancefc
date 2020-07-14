<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Team - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Team</h1>
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
				<th colspan="2"><b>Operation</b></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${keepers}" var="keeper">
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
					<td class="button"><form action="edit" method="GET">
							<input type="submit" value="  Edit  ">
							<input type="hidden" name="typerequest" value="keepers">
							<input type="hidden" name="id" value="${keeper.id}">
						</form></td>
					<td class="button"><form action="delete" method="GET">
							<input type="submit" value="Delete">
							<input type="hidden" name="typerequest" value="keepers">
							<input type="hidden" name="id" value="${keeper.id}">
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
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
				<th><b>Position</b></th>
				<th colspan="2"><b>Operation</b></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${players}" var="player">
				<tr>
					<td>${player.number}</td>
					<td>${player.name}</td>
					<td>${player.surname}</td>
					<td>${player.birthDate}</td>
					<td>${player.age}</td>
					<td>${player.games}</td>
					<td>${player.goals}</td>
					<td>${player.height}</td>
					<td>${player.weight}</td>
					<td>${player.position}</td>
					<td class="button"><form action="edit" method="GET">
							<input type="submit" value="  Edit  ">
							<input type="hidden" name="typerequest" value="players">
							<input type="hidden" name="id" value="${player.id}">
						</form></td>
					<td class="button"><form action="delete" method="GET">
							<input type="submit" value="Delete">
							<input type="hidden" name="typerequest" value="players">
							<input type="hidden" name="id" value="${player.id}">
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<form class="center" action="/renaissancefc/create.jsp">
	<input type="submit" value="Create new record">
	</form>
</body>
</html>