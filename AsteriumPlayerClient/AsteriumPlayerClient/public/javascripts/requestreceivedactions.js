
var requestActions = {};

function syncPlayerClientData(request) {

    locations = [];
    request.sync_player_client_data.locations.forEach(location => {
        locations[location.location_id] = location;
    });
    var newPhase = request.sync_player_client_data.game_phase_name;

    if (gamePhase != newPhase) {
        console.log("Should be going to new phase now.");
        phaseChangeStartingActions[newPhase]();
    }


}

requestActions["sync_player_client_data"] = syncPlayerClientData;


var phaseChangeStartingActions = {};

phaseChangeStartingActions["PLAYER_TURNS"] = function () {
    actionDisplayController.display();
};

phaseChangeStartingActions["TURN_RESOLVE"] = function () {
    console.log("TURN RESOLVE! SHOULD PROBABLY DISPLAY SOMETHING!");
};