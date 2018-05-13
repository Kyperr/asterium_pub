var socket = new WebSocket("ws://localhost:8080/AsteriumWebServer/Game");

var responseActions = {};
var requestActions = {};




socket.onmessage = function (message) {

    var jsonObj = JSON.parse(message.data);
    console.log(jsonObj);
    if (jsonObj.hasOwnProperty("response")) {
        if (jsonObj.response.hasOwnProperty('message_id')) {
            var action = responseActions[jsonObj.response.message_id];
            if (action !== null) {
                action(jsonObj.response);
            }
        }
    }

    if (jsonObj.hasOwnProperty("request")) {
        if (action !== null) {
            if (jsonObj.request.hasOwnProperty('action_name')) {
                var action = requestActions[jsonObj.request.action_name];
                action(jsonObj.request);
            }
        }
    }

};


//**********SENDING FUNCTIONS**********

function joinAsPlayer() {

    var lobby_id = document.getElementById("lobby_id").value;
    var name = document.getElementById("name").value;
    var uuid = genUUID();

    message =
        {
            "request":
                {
                    "action_name": "join_as_player",
                    "join_as_player":
                        {
                            "lobby_id": lobby_id,
                            "player_data":
                                {
                                    "name": name
                                }
                        },
                    "auth_token": "",
                    "message_id": uuid
                }
        }

    responseActions[uuid] = function (response) {

        if (response.error_code == 0) {
            localStorage.setItem("auth_token", response.auth_token);
            console.log("AuthToken acquired: " + localStorage.getItem("auth_token"));
        } else {
            console.log("Failed to join lobby, error_code: " + response.error_code);
        }
        location.reload();
    }

    socket.send(JSON.stringify(message));

}


//***Utils***

//Created uuid
function genUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}