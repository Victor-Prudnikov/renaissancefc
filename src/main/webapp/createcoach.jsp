<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create New Coach Record - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Create New Coach Record</h1>
	<form class="divform" action="create" method="POST">
		<input type="hidden" name="typerequest" value="coach">
		<label for="CoachName">Name:</label><br>
  		<input type="text" id="CoachName" name="CoachName"><br>
  		<label for="CoachSurname">Surname:</label><br>
  		<input type="text" id="CoachSurname" name="CoachSurname"><br>
  		<label for="CoachBirthDate">Birth Date:</label><br>
  		<input type="date" id="CoachBirthDate" name="CoachBirthDate"><br>
  		<label for="CoachGames">Games:</label><br>
  		<input type="number" id="CoachGames" name="CoachGames" min="0" value="0"><br>
  		<label for="CoachPost">Post:</label><br>
  		<input list="CoachPosts" name="CoachPost"><br>
  		<datalist id="CoachPosts">
  			<c:forEach items="${postlist}" var="post">
  				<option value="${post.post}">
  			</c:forEach>
  		</datalist><br>
  		<input type="submit" value="  OK  ">
  		<input type="submit" value ="Cancel" formaction="coachesview">
	</form>
</body>
</html>