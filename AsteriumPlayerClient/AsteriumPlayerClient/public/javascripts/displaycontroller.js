

function displayWaitingForPlayers(div) {
    div.innerHTML = "Waiting for players to be ready...";

    div.appendChild(document.createElement("br"));

    var btn = document.createElement("BUTTON");

    if (playerIsReady) {
        btn.innerHTML = 'UNREADY';
    } else {
        btn.innerHTML = 'READY';
    }

    btn.setAttribute("onClick", "toggleReady()");

    div.appendChild(btn);
}

function displayLocations(div) {
    div.innerHTML = "Please Select A Location...";



}

//=====AbstractDisplayController

function AbstractDisplayController() {
}

AbstractDisplayController.prototype = new AbstractDisplayController();
AbstractDisplayController.prototype.constructor = AbstractDisplayController;

AbstractDisplayController.prototype.init = function () {
}

AbstractDisplayController.prototype.display = function (div) {
    div.innerHTML = "This Display Controller is not correctly implemented."
}

//=====JoinLobbyDisplayController

function JoinLobbyDisplayController() {
    this.nameInput = document.createElement("input");
    this.lobby_id = document.createElement("input");
    this.btn = document.createElement("BUTTON");
    this.init();
}

JoinLobbyDisplayController.prototype = Object.create(JoinLobbyDisplayController.prototype);
JoinLobbyDisplayController.prototype.constructor = JoinLobbyDisplayController;


JoinLobbyDisplayController.prototype.init = function () {
    this.nameInput.setAttribute("id", "name");
    this.nameInput.setAttribute("name", "name");

    this.lobby_id.setAttribute("id", "lobby_id");
    this.lobby_id.setAttribute("name", "lobby_id");

    this.btn.innerHTML = 'Join Lobby';
    this.btn.setAttribute("onClick", "joinAsPlayer()");
}

JoinLobbyDisplayController.prototype.display = function (div) {

    div.appendChild(this.nameInput);

    div.appendChild(document.createElement("br"));

    div.appendChild(this.lobby_id);

    div.appendChild(document.createElement("br"));

    div.appendChild(this.btn);
}

var joinAsLobbyDisplayController = new JoinLobbyDisplayController();