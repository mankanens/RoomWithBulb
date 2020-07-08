var stompClient = null;

function setConnected(connected) {
	if (connected) {
		$("#connect").css('visibility', 'hidden');
		$("#disconnect").css('visibility', 'visible');
		$("#bulbInf").css('visibility', 'visible');
		$("#toogle").css('visibility', 'visible');
	} else {
		$("#connect").css('visibility', 'visible');
		$("#disconnect").css('visibility', 'hidden');
		$("#bulbInf").css('visibility', 'hidden');
		$("#toogle").css('visibility', 'hidden');
	}
	$("#bulbInf").html("");
}

function connect() {
	var socket = new SockJS('/gs-guide-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/bulbInf', function(greeting) {
			var controllerResponse = JSON.parse(greeting.body).content;
			var bulbStatus = controllerResponse.substr(0, 1);
			var roomName = controllerResponse.slice(1,
					controllerResponse.length);
			var pageRoomName = $("#roomName").html();
			pageRoomName = pageRoomName.replace('&amp;', '&');
			if (roomName == pageRoomName) {
				if (bulbStatus == 'f') {
					showBulbInfo('Bulb is off');
					$("#bulbInf").attr('class', 'btn btn-danger');
				} else {
					showBulbInfo('Bulb is on');
					$("#bulbInf").attr('class', 'btn btn-success');
				}
			}
		});
		sendRoomInfo(false);
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendRoomInfo(isToogle) {
	if (isToogle) {
		stompClient.send("/room/checkBulb", {}, JSON.stringify({
			'content' : 't' + $('#roomName').html()
		}));
	} else {
		stompClient.send("/room/checkBulb", {}, JSON.stringify({
			'content' : 'f' + $('#roomName').html()
		}));
	}
}

function showBulbInfo(message) {
	$("#bulbInf").val(message);
}

$(function() {
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#toogle").click(function() {
		sendRoomInfo(true);
	});
});