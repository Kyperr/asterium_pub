
var responseActions = {};

function processQueryIsInGameResponse(response) {
    var isInGame = response.query_is_in_game.is_in_game;
    if (isInGame) {
        document.getElementById("centralDiv").innerHTML = "<p>You are currently in a game!";
        if(gameIsStarted){

        } else {
            displayWaitingForPlayers(document.getElementById("centralDiv"));
        }
    } else {
        joinAsLobbyDisplayController.display(document.getElementById("centralDiv"));
    }
};

function processJoinAsPlayerResponse(response) {
    if (response.error_code == 0) {
        localStorage.setItem("auth_token", response.auth_token);
        console.log("AuthToken acquired: " + localStorage.getItem("auth_token"));
        checkIfIsInGame();
    } else {
        console.log("Failed to join lobby, error_code: " + response.error_code);
    }
}

function processToggleReadyUpResponse(response){
    if (response.error_code == 0) {
        playerIsReady = response.toggle_ready_up.player_is_ready;
        displayWaitingForPlayers(document.getElementById("centralDiv"));
    } else {
        console.log("Failed to toggle ready up status, error_code: " + response.error_code);
    }
}

