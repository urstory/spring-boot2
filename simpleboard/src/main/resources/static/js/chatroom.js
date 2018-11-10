var stompClient = null;

function setConnected(connected) {
}

function showMessage(message) {
    console.log(message);

    $("#chatArea").append(message.name + ' : ' + message.message + '\n');

    var textArea = $('#chatArea');
    textArea.scrollTop( textArea[0].scrollHeight - textArea.height()   );

}


function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/rooms/' + chatRoomId, function (chatMessage) {
            console.log('=-------------------------');
            console.log(chatMessage);
            console.log('=-------------------------');
            showMessage(JSON.parse(chatMessage.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function send() {
    stompClient.send("/app/rooms/" + chatRoomId, {}, JSON.stringify({'chatRoomId': chatRoomId, 'message': $("#chatInput").val()}));
    $("#chatInput").val('');
    $("#chatInput").focus();
}


$(function () {

    connect();

    $("#chatInput").keypress(function(e) {

        if (e.keyCode == 13){
            send();

        }
    });

    $( "#sendBtn" ).click(function() { send(); });
});