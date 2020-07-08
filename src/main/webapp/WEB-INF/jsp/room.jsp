<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Room</title>
<link href="<c:url value="/css/room.css"/>" rel="stylesheet">
<link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script type="text/javascript" charset="utf-8">
	
<%@include file="/js/socket.js"%>
	
</script>
</head>
<body>
	<table id="table">
		<tr>
			<td class="h1">Room "<span id="roomName">${roomName}</span>"
			</td>
			<td id="rightCol""><a href="/" id="main" class="h1">Main</a></td>
		</tr>
	</table>
	<button id="connect" class="btn btn-default">Connect to room.</button>
	<button id="disconnect" class="btn btn-default">Disconnect
		from room.</button>
	<div class="input-group mb-3">
		<div class="input-group-prepend">
			<button class="btn btn-outline-secondary" type="button" id="toogle">Change
				the state of the bulb</button>
		</div>
		<input type="text" class="btn btn-dark" placeholder="" aria-label=""
			aria-describedby="basic-addon1" id="bulbInf">
	</div>
</body>
</html>