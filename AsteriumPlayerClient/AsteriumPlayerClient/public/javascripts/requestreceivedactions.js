
var requestActions = {};

function syncPlayerClientData(request) {

    user = request.sync_player_client_data.character.character_name;

    locations = [];

    request.sync_player_client_data.locations.forEach(location => {
        if(location.map_location == 1) {
            controlRoom = location;
        } else {
            locations[location.map_location] = location;
        }
    });

    communalInventory = request.sync_player_client_data.communal_inventory;

    personalInventory = request.sync_player_client_data.character.personal_inventory;

    playerIsReady = request.sync_player_client_data.character.ready;

    var newPhase = request.sync_player_client_data.game_phase_name;

    if (gamePhase != newPhase) {
        if(gamePhase != "TURN_RESOLVE"){
            phaseChangeStartingActions[newPhase]();
        }
        gamePhase = newPhase;
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
    turnSummaryDisplayController.display();
};

phaseChangeStartingActions["END_SUMMARY"] = function () {
    endGameDisplayController.display();
};

