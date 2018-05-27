
var responseActions = {};

function processQueryIsInGameResponse(response) {
    if (gamePhase == "PLAYERS_JOINING") {
        var isInGame = response.query_is_in_game.is_in_game;
        if (isInGame) {
            waitingForPlayersDisplayController.display();
            gameTabsDisplayController.display();
            viewInventoryDisplayController.display();
        } else {
            joinLobbyDisplayController.display();
        }
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

function processToggleReadyUpResponse(response) {
    if (response.error_code == 0) {
        playerIsReady = response.toggle_ready_up.player_is_ready;

        if (playerIsReady) {
            waitingForPlayersDisplayController.btn.innerHTML = 'UNREADY';
            afterTurnWaitingDisplayController.btnReady.innerHTML = 'UNREADY';
        } else {
            waitingForPlayersDisplayController.btn.innerHTML = 'READY';
            afterTurnWaitingDisplayController.btnReady.innerHTML = 'READY';
        }
    } else {
        console.log("Failed to toggle ready up status, error_code: " + response.error_code);
    }
}

function processTurnActionResponse(response){
    if (response.error_code == 0) {
        console.log("Turn action was a success.");
        afterTurnWaitingDisplayController.display();
    } else {
        console.log("Failed to do turn, error_code: " + response.error_code);
    }
}

function processUseItemResponse(response){
    if (response.error_code == 0) {
        console.log("Item usage was a success.");
        viewInventoryDisplayController.display();
    } else {
        console.log("Failed to do turn, error_code: " + response.error_code);
    }
}