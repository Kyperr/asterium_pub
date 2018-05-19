
var requestActions = {};

function syncPlayerClientData(request){
    locations = request.sync_player_client_data.locations;

    var newPhase = request.sync_player_client_data.game_phase_name;
    
    if(gamePhase != newPhase){
        phaseChangeStartingActions[newPhase]();
    }


}

requestActions["sync_player_client_data"] = syncPlayerClientData;


var phaseChangeStartingActions = {};

phaseChangeStartingActions["PLAYER_TURNS"] = function(){
    actionDisplayController.display(document.getElementById("centralDiv"));   
};

phaseChangeStartingActions[""] = function(){
    actionDisplayController.display(document.getElementById("centralDiv"));   
};