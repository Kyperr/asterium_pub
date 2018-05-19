

function displayLocations(div) {
    div.innerHTML = "Please Select A Location...";
}

//
//==========AbstractDisplayController
//

function AbstractDisplayController() {
}

AbstractDisplayController.prototype = new AbstractDisplayController();
AbstractDisplayController.prototype.constructor = AbstractDisplayController;

AbstractDisplayController.prototype.init = function () {
}

AbstractDisplayController.prototype.display = function (div) {
    div.innerHTML = "This Display Controller is not correctly implemented."
}

//
//==========JoinLobbyDisplayController
//

function JoinLobbyDisplayController() {
    this.nameInput = document.createElement("input");
    this.lobby_id = document.createElement("input");
    this.btn = document.createElement("BUTTON");
    this.init();
}

JoinLobbyDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
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

//Static instance. USE THIS ONE!
var joinAsLobbyDisplayController = new JoinLobbyDisplayController();

//
//==========JoinLobbyDisplayController
//

function WaitingForPlayersDisplayController() {
    this.btn = document.createElement("BUTTON");
    this.init();
}

WaitingForPlayersDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
WaitingForPlayersDisplayController.prototype.constructor = WaitingForPlayersDisplayController;


WaitingForPlayersDisplayController.prototype.init = function () {
    this.btn.innerHTML = 'READY';
    this.btn.setAttribute("onClick", "toggleReady()");
}

WaitingForPlayersDisplayController.prototype.display = function (div) {

    div.innerHTML = "Waiting for players to be ready...";

    div.appendChild(document.createElement("br"));

    div.appendChild(this.btn);
}

//Static instance. USE THIS ONE!
var waitingForPlayersDisplayController = new WaitingForPlayersDisplayController();

//
//==========MovementDisplayController
//

function ActionDisplayController() {
    this.btnExplore = document.createElement("BUTTON");
    this.btnStay = document.createElement("BUTTON");
    this.btnInventory = document.createElement("BUTTON");
}

ActionDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ActionDisplayController.prototype.constructor = ActionDisplayController;


ActionDisplayController.prototype.init = function () {
    this.btnExplore.setAttribute("onClick", "toggleReady()");
    this.btnExplore.innerHTML = "EXPLORE";

    this.btnStay.setAttribute("onClick", "toggleReady()");
    this.btnStay.innerHTML = "STAY";

    this.btnInventory.setAttribute("onClick", "toggleReady()");
    this.btnInventory.innerHTML = "INVENTORY";
}

ActionDisplayController.prototype.display = function (div) {

    div.innerHTML = "What action would you like to do?";

    div.appendChild(document.createElement("br"));
    div.appendChild(this.btnExplore);
    div.appendChild(document.createElement("br"));
    div.appendChild(this.btnStay);
    div.appendChild(document.createElement("br"));
    div.appendChild(this.btnInventory);
}

//Static instance. USE THIS ONE!
var actionDisplayController = new ActionDisplayController();

