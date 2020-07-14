<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Coaches - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Coaches</h1>
	<table class="table1">
		<thead>
			<tr>
				<th><b>Name</b></th>
				<th><b>Surname</b></th>
				<th><b>Birth Date</b></th>
				<th><b>Age</b></th>
				<th><b>Games</b></th>
				<th><b>Post</b></th>
				<th colspan="2"><b>Operation</b></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${coaches}" var="coach">
				<tr>
					<td>${coach.name}</td>
					<td>${coach.surname}</td>
					<td>${coach.birthDate}</td>
					<td>${coach.age}</td>
					<td>${coach.games}</td>
					<td>${coach.post}</td>
					<td class="button"><form action="edit" method="GET">
							<input type="submit" value="  Edit  ">
							<input type="hidden" name="typerequest" value="coaches">
							<input type="hidden" name="id" value="${coach.id}">
						</form></td>
					<td class="button"><form action="delete" method="GET">
							<input type="submit" value="Delete">
							<input type="hidden" name="typerequest" value="coaches">
							<input type="hidden" name="id" value="${coach.id}">
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