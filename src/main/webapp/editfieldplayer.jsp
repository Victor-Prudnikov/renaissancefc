<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Field Player Record - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Edit Field Player Record</h1>
	<form class="divform" action="edit" method="POST">
		<input type="hidden" name="typerequest" value="players">
		<input type="hidden" name="id" value="${player.id}">
		<input type="hidden" name="PlayerOldNumber" value="${player.number}">
		<label for="PlayerNumber">Number:</label><br>
  		<input type="number" id="PlayerNumber" name="PlayerNumber" min="0" value="${player.number}"><br>
		<label for="PlayerName">Name:</label><br>
  		<input type="text" id="PlayerName" name="PlayerName" value="${player.name}"><br>
  		<label for="PlayerSurname">Surname:</label><br>
  		<input type="text" id="PlayerSurname" name="PlayerSurname" value="${player.surname}"><br>
  		<label for="PlayerBirthDate">Birth Date:</label><br>
  		<input type="date" id="PlayerBirthDate" name="PlayerBirthDate" value="${player.birthDateForForm}"><br>
  		<label for="PlayerGames">Games:</label><br>
  		<input type="number" id="PlayerGames" name="PlayerGames" min="0" value="${player.games}"><br>
  		<label for="PlayerGoals">Goals:</label><br>
  		<input type="number" id="PlayerGoals" name="PlayerGoals" min="0" value="${player.goals}"><br>
  		<label for="PlayerHeight">Height:</label><br>
  		<input type="number" id="PlayerHeight" name="PlayerHeight" min="0" value="${player.height}"><br>
  		<label for="PlayerWeight">Weight:</label><br>
  		<input type="number" id="PlayerWeight" name="PlayerWeight" min="0" value="${player.weight}"><br>
  		<label for="PlayerPosition">Position:</label><br>
  		<select id="PlayerPosition" name="PlayerPosition">
  			<c:forEach items="${positionlist}" var="position">
  				<option value="${position.position}"
  					<c:if test="${player.position == position.position}"> selected</c:if>
  				>${position.position}</option>
  			</c:forEach>
  		</select><br><br>
  		<input type="submit" value="  OK  ">
  		<input type="submit" value ="Cancel" formaction="teamview">
	</form>
</body>
</html>