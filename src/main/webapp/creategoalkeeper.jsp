<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create New Goalkeeper Record - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Create New Goalkeeper Record</h1>
	<form class="divform" action="create" method="POST">
		<input type="hidden" name="typerequest" value="goalkeeper">
		<label for="KeeperNumber">Number:</label><br>
  		<input type="number" id="KeeperNumber" name="KeeperNumber" min="0"><br>
		<label for="KeeperName">Name:</label><br>
  		<input type="text" id="KeeperName" name="KeeperName"><br>
  		<label for="KeeperSurname">Surname:</label><br>
  		<input type="text" id="KeeperSurname" name="KeeperSurname"><br>
  		<label for="KeeperBirthDate">Birth Date:</label><br>
  		<input type="date" id="KeeperBirthDate" name="KeeperBirthDate"><br>
  		<label for="KeeperGames">Games:</label><br>
  		<input type="number" id="KeeperGames" name="KeeperGames" min="0" value="0"><br>
  		<label for="KeeperGoals">Goals:</label><br>
  		<input type="number" id="KeeperGoals" name="KeeperGoals" min="0" value="0"><br>
  		<label for="KeeperHeight">Height, sm:</label><br>
  		<input type="number" id="KeeperHeight" name="KeeperHeight" min="0" value="0"><br>
  		<label for="KeeperWeight">Weight, kg:</label><br>
  		<input type="number" id="KeeperWeight" name="KeeperWeight" min="0" value="0"><br>
  		<label for="GoalsConceded">Goals conceded:</label><br>
  		<input type="number" id="GoalsConceded" name="GoalsConceded" min="0" value="0"><br>
  		<label for="PenaltySaved">Penalty saved:</label><br>
  		<input type="number" id="PenaltySaved" name="PenaltySaved" min="0" value="0"><br>
  		<label for="KeeperPosition">Position:</label><br>
  		<input type="text" id="KeeperPosition" name="KeeperPosition" value="Goalkeeper" disabled><br><br>
  		<input type="submit" value="  OK  ">
  		<input type="submit" value ="Cancel" formaction="teamview">
	</form>
</body>
</html>