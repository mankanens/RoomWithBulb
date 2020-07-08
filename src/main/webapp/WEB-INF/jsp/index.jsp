<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rooms with light bulbs</title>
<link href="<c:url value="/css/index.css"/>" rel="stylesheet"
	media="all">
<link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<h1 align="center">Rooms with light bulbs</h1>
	<div id="create">
		Create room
		<form action="/" method="post" class="form-inline">
			<select name="select">
				<c:forEach var="country" items="${countries}">
					<option value="${country}">${country}</option>
				</c:forEach>
			</select> <input type="submit" value="Create" class="btn button-default">
		</form>
		<div id="error"></div>
	</div>
	<div id="list">
		Room list:
		<c:forEach var="room" items="${rooms}">
			<div>
				<a href="/rooms/${room.getName()}">${room.getName()}</a>
			</div>
		</c:forEach>
	</div>
</body>
</html>