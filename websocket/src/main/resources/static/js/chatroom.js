var ws = null;

function setConnected(connected) {
}

function showMessage(message) {
    console.log(message);
    var jsonMessage = JSON.parse(message);

    $("#chatArea").append(jsonMessage.name + ' : ' + jsonMessage.message + '\n');

    var textArea = $('#chatArea');
    textArea.scrollTop( textArea[0].scrollHeight - textArea.height()   );

}


function connect() {
    ws = new SockJS('/ws');
    ws.onmessage = function(e) {
        showMessage(e.data);
    }
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }
    setConnected(false);
    console.log("Disconnected");
}

function send() {
    ws.send(JSON.stringify({'chatRoomId': chatRoomId, 'message': $("#chatInput").val()}));
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