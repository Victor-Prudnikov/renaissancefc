<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<h1>Welcome to the Renaissance FC fan-site</h1>
	<br><br>
	<div class="divform">
		<nav class="navbarindex">
			<ul>
				<li><a href="/renaissancefc/coachesview">List of coaches</a></li>
				<li><a href="/renaissancefc/teamview">Players squad</a></li>
				<li><a href="/renaissancefc/fans.jsp">Our fans</a></li>
				<li><a href="/renaissancefc/about.jsp">About club</a></li>
			</ul>
		</nav>
	</div>
</body>
</html>