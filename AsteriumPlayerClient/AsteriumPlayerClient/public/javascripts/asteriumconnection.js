//The localhost. 
var socket = new WebSocket("ws://localhost:8080/AsteriumWebServer/Game");

//The server IP. 
//var socket = new WebSocket("ws://35.230.4.196:8080/AsteriumWebServer/Game");

socket.onmessage = function (message) {
    var jsonObj = JSON.parse(message.data);
    console.log(jsonObj);
    if (jsonObj.hasOwnProperty("response")) {
        if (jsonObj.response.hasOwnProperty('message_id')) {
            var action = responseActions[jsonObj.response.message_id];
            if (action != null) {
                action(jsonObj.response);
            } else {
                console.log("Warning! An action was not found for " + jsonObj.action_name);
            }
        }
    }

    if (jsonObj.hasOwnProperty("request")) {
        if (jsonObj.request.hasOwnProperty('action_name')) {
            var action = requestActions[jsonObj.request.action_name];
            if (action != null) {
                action(jsonObj.request);
            } else {
                console.log("Warning! An action was not found for " + jsonObj.request.action_name);
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

function setReadyStatus(ready) {
    console.log("Toggling ready!");
    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "set_ready_status",
                    "set_ready_status":
                        {
                            "player_ready_status": ready
                        },
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
        }
    socket.send(JSON.stringify(message));
}

function turnActivity() {
    console.log("Sending turn action!");
    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "turn_action",
                    "turn_action":
                        {
                            "map_location": locationsDisplayController.selectedLocation.map_location,
                            "activity_name": activityDisplayController.selectedActivity
                        },
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
        }
    socket.send(JSON.stringify(message));
    responseActions[uuid] = processTurnActionResponse;
}

function itemTurnActivity(item, location) {
    console.log("Sending turn action!");
    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "item_turn_action",
                    "item_turn_action":
                        {
                            "map_location": location.map_location,
                            "item_name": item.item_name
                        },
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
        }
    console.log(JSON.stringify(message));
    socket.send(JSON.stringify(message));
    responseActions[uuid] = processTurnActionResponse;
}

function useItemAction(item, targets, isCommunal) {//Should use itemID later.  
    console.log("Sending use-item!");

    //Ew, remove this after the MVP
    if (item.item_name == "Rescue Beacon") {
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
                                "item_id": item.item_name
                            },
                            "is_equipped":false,
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

function unequip(item) {//Should use itemID later.  

    var targets = [user];
    var isCommunal = false;

    console.log("Sending use-item!");

    //Ew, remove this after the MVP
    if (item.item_name == "Rescue Beacon") {
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
                                "item_id": item.item_name
                            },
                            "is_equipped":true,
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

function discardItem(item) {//Should use itemID later.  
    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "discard_item",
                    "discard_item":
                        {
                            "item": {
                                "item_name": item.item_name
                            },
                        },
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
        }
    socket.send(JSON.stringify(message));
    responseActions[uuid] = processUseItemResponse;
}

function moveItemTo(item, toCommunal) {//Should use itemID later.  
    var uuid = genUUID();
    message =
        {
            "request":
                {
                    "action_name": "communal_inventory",
                    "communal_inventory":
                        {
                            "item": {
                                "item_name": item.item_name
                            },
                            "personal_to_communal":toCommunal
                        },
                    "auth_token": getAuthToken(),
                    "message_id": uuid
                }
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