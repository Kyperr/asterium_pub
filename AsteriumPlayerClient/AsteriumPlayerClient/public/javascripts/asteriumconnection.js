var socket = new WebSocket("ws://localhost:8080/AsteriumWebServer/Game");

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

socket.onopen = function () {
    checkIfIsInGame();
}

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

    responseActions[uuid] = processJoinAsPlayerResponse;

    socket.send(JSON.stringify(message));

}

function checkIfIsInGame() {
    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "query_is_in_game",
                    "query_is_in_game":
                        {},
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
        }
    socket.send(JSON.stringify(message));
    responseActions[uuid] = processQueryIsInGameResponse;
}

function toggleReady() {
    console.log("Toggling ready!");
    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "toggle_ready_up",
                    "toggle_ready_up":
                        {},
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
        }
    socket.send(JSON.stringify(message));
    responseActions[uuid] = processToggleReadyUpResponse;
}

function turnActivity(){
    console.log("Sending turn action!");
    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "turn_action",
                    "turn_action":
                        {
                            "location_id": locationsDisplayController.selectedLocation.location_id,
                            "activity_name": activityDisplayController.selectedActivity
                        },
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
        }
    socket.send(JSON.stringify(message));
    responseActions[uuid] = processTurnActionResponse;
}

function useItemAction(itemName, targets, isCommunal){//Should use itemID later.  
    console.log("Sending use-item!");

    //Ew, remove this after the MVP
    if(itemName == "Rescue Beacon"){
        usedTheBeacon = true;
    }


    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "use_item",
                    "use_item":
                        {
                            "item": {
                                "item_id": itemName
                            },
                            "targets": targets,
                            "is_communal": isCommunal
                        },
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
        }
    socket.send(JSON.stringify(message));
    responseActions[uuid] = processUseItemResponse;
}

//***Utils***

//Created uuid
function genUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}