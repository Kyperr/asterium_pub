
var requestActions = {};

function syncPlayerClientData(request) {

    user = request.sync_player_client_data.character.character_name;
    character = request.sync_player_client_data.character;

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
        //If old phase was turn resolve(because player hasn't hit continue), or if it is end_summary
        if(gamePhase != "TURN_RESOLVE" || newPhase == "END_SUMMARY"){
            phaseChangeStartingActions[newPhase]();
        }
        gamePhase = newPhase;
    }

    characterSheetDisplayController.update();
    locationsDisplayController.update();
    viewInventoryDisplayController.update();
}

requestActions["sync_player_client_data"] = syncPlayerClientData;

//

function summary(request) {
    summaryStrings = request.summary.summary;
    turnSummaryDisplayController.update();
}
requestActions["summary"] = summary;

//

var phaseChangeStartingActions = {};

phaseChangeStartingActions["PLAYER_TURNS"] = function () {
    actionDisplayController.display();
};

phaseChangeStartingActions["TURN_RESOLVE"] = function () {
    turnSummaryDisplayController.display();
};

phaseChangeStartingActions["END_SUMMARY"] = function () {
    endGameDisplayController.display();
};

