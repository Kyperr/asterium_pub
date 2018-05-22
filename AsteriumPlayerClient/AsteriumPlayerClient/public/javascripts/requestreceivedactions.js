
var requestActions = {};

function syncPlayerClientData(request) {

    user = request.sync_player_client_data.character.character_name;

    locations = [];

    request.sync_player_client_data.locations.forEach(location => {
        locations[location.location_id] = location;
    });

    communalInventory = request.sync_player_client_data.communal_inventory;

    personalInventory = request.sync_player_client_data.character.personal_inventory;

    playerIsReady = request.sync_player_client_data.character.

    var newPhase = request.sync_player_client_data.game_phase_name;

    if (gamePhase != newPhase && gamePhase != "TURN_RESOLVE") {
        gamePhase = newPhase;
        phaseChangeStartingActions[newPhase]();
    }
}

requestActions["sync_player_client_data"] = syncPlayerClientData;


var phaseChangeStartingActions = {};

phaseChangeStartingActions["PLAYER_TURNS"] = function () {
    
    //Because I have secret server knowledge.
    console.log("Setting playerisready = false");
    playerIsReady = false;

    actionDisplayController.display();
};

phaseChangeStartingActions["TURN_RESOLVE"] = function () {
    console.log("TURN RESOLVE! SHOULD PROBABLY DISPLAY SOMETHING!");
};

phaseChangeStartingActions["END_SUMMARY"] = function () {
    endGameDisplayController.display();
};

