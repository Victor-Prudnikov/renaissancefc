<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Something wrong - Renaissance FC</title>
<link rel="stylesheet" type="text/css" href="Styles/standard.css">
</head>
<body class="body">
	<jsp:include page="header.jsp" />
	<br>
	<br>
	<div class="center">
	<h1>Something wrong</h1>
	<strong>${error}</strong>
	<br><br>
	<input type="button" onclick="history.back();" value="Go Back"/>
	</div>
</body>
</html>