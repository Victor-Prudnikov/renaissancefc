<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Goalkeeper Record - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Edit Goalkeeper Record</h1>
	<form class="divform" action="edit" method="POST">
		<input type="hidden" name="typerequest" value="keepers">
		<input type="hidden" name="id" value="${keeper.id}">
		<input type="hidden" name="KeeperOldNumber" value="${keeper.number}">
		<label for="KeeperNumber">Number:</label><br>
  		<input type="number" id="KeeperNumber" name="KeeperNumber" min="0" value="${keeper.number}"><br>
		<label for="KeeperName">Name:</label><br>
  		<input type="text" id="KeeperName" name="KeeperName" value="${keeper.name}"><br>
  		<label for="KeeperSurname">Surname:</label><br>
  		<input type="text" id="KeeperSurname" name="KeeperSurname" value="${keeper.surname}"><br>
  		<label for="KeeperBirthDate">Birth Date:</label><br>
  		<input type="date" id="KeeperBirthDate" name="KeeperBirthDate" value="${keeper.birthDateForForm}"><br>
  		<label for="KeeperGames">Games:</label><br>
  		<input type="number" id="KeeperGames" name="KeeperGames" min="0" value="${keeper.games}"><br>
  		<label for="KeeperGoals">Goals:</label><br>
  		<input type="number" id="KeeperGoals" name="KeeperGoals" min="0" value="${keeper.goals}"><br>
  		<label for="KeeperHeight">Height:</label><br>
  		<input type="number" id="KeeperHeight" name="KeeperHeight" min="0" value="${keeper.height}"><br>
  		<label for="KeeperWeight">Weight:</label><br>
  		<input type="number" id="KeeperWeight" name="KeeperWeight" min="0" value="${keeper.weight}"><br>
  		<label for="GoalsConceded">Goals conceded:</label><br>
  		<input type="number" id="GoalsConceded" name="GoalsConceded" min="0" value="${keeper.goalsConceded}"><br>
  		<label for="PenaltySaved">Penalty saved:</label><br>
  		<input type="number" id="PenaltySaved" name="PenaltySaved" min="0" value="${keeper.penaltySaved}"><br>
  		<label for="KeeperPosition">Position:</label><br>
  		<input type="text" id="KeeperPosition" name="KeeperPosition" value="Goalkeeper" disabled><br><br>
  		<input type="submit" value="  OK  ">
  		<input type="submit" value ="Cancel" formaction="teamview">
	</form>
</body>
</html>