<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create New Field Player Record - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Create New Field Player Record</h1>
	<form class="divform" action="create" method="POST">
		<input type="hidden" name="typerequest" value="fieldplayer">
		<label for="PlayerNumber">Number:</label><br>
  		<input type="number" id="PlayerNumber" name="PlayerNumber" min="0"><br>
		<label for="PlayerName">Name:</label><br>
  		<input type="text" id="PlayerName" name="PlayerName"><br>
  		<label for="PlayerSurname">Surname:</label><br>
  		<input type="text" id="PlayerSurname" name="PlayerSurname"><br>
  		<label for="PlayerBirthDate">Birth Date:</label><br>
  		<input type="date" id="PlayerBirthDate" name="PlayerBirthDate"><br>
  		<label for="PlayerGames">Games:</label><br>
  		<input type="number" id="PlayerGames" name="PlayerGames" min="0" value="0"><br>
  		<label for="PlayerGoals">Goals:</label><br>
  		<input type="number" id="PlayerGoals" name="PlayerGoals" min="0" value="0"><br>
  		<label for="PlayerHeight">Height, sm:</label><br>
  		<input type="number" id="PlayerHeight" name="PlayerHeight" min="0" value="0"><br>
  		<label for="PlayerWeight">Weight, kg:</label><br>
  		<input type="number" id="PlayerWeight" name="PlayerWeight" min="0" value="0"><br>
  		<label for="PlayerPosition">Position:</label><br>
  		<select id="PlayerPosition" name="PlayerPosition">
  			<c:forEach items="${positionlist}" var="position">
  				<option value="${position.position}">${position.position}</option>
  			</c:forEach>
  		</select>
  		<br><br>
  		<input type="submit" value="  OK  ">
  		<input type="submit" value ="Cancel" formaction="teamview">
	</form>
</body>
</html>