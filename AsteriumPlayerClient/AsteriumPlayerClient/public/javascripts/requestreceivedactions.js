
var requestActions = {};

function syncPlayerClientData(request){
    locations = request.sync_player_client_data.locations;
}

requestActions["sync_player_client_data"] = syncPlayerClientData;