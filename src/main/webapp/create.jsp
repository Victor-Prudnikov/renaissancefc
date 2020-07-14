<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create New Record - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>What record do you want to create?</h1>
	<form class="center" action="create" method="GET">
		<select id="createlist" name="createlist">
  			<option value="coach">Coach</option>
  			<option value="goalkeeper">Goalkeeper</option>
  			<option value="fieldplayer">Field Player</option>
		</select>
  		<input type="submit" value="  OK  ">
  		<input type="button" onclick="history.back();" value="Go Back"/>
	</form>
</body>
</html>