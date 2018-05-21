

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

AbstractDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

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

JoinLobbyDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

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

WaitingForPlayersDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    console.log("waiting");
    div.innerHTML = "Waiting for players to be ready...";

    div.appendChild(document.createElement("br"));

    div.appendChild(this.btn);
}

//Static instance. USE THIS ONE!
var waitingForPlayersDisplayController = new WaitingForPlayersDisplayController();

//
//==========ActionDisplayController
//

function ActionDisplayController() {
    this.btnExplore = document.createElement("BUTTON");
    this.btnStay = document.createElement("BUTTON");
    this.btnInventory = document.createElement("BUTTON");
    this.init();
}

ActionDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ActionDisplayController.prototype.constructor = ActionDisplayController;


ActionDisplayController.prototype.init = function () {
    this.btnExplore.setAttribute("onClick", "locationsDisplayController.display()");
    this.btnExplore.innerHTML = "EXPLORE";

    this.btnStay.setAttribute("onClick", "toggleReady()");
    this.btnStay.innerHTML = "STAY";

    this.btnInventory.setAttribute("onClick", "toggleReady()");
    this.btnInventory.innerHTML = "INVENTORY";
}

ActionDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    console.log("action");

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

//
//==========LocationsDisplayController
//

function LocationsDisplayController() {
    this.btnLocation = document.createElement("BUTTON");
    this.selectedLocation;
    this.init();
}

LocationsDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
LocationsDisplayController.prototype.constructor = LocationsDisplayController;


LocationsDisplayController.prototype.init = function () {
}

LocationsDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "What location would you like to explore?";

    div.appendChild(document.createElement("br"));

    var that = this;
    locations.forEach(location => {
        this.btnLocation = document.createElement("BUTTON");

        //Should map the id to a display-friendly string and get it here.

        that.btnLocation.innerHTML = location.location_id;
        that.btnLocation.setAttribute("onClick", "locationsDisplayController.selectLocation(\'" + location.location_id + "\')");
        div.appendChild(this.btnLocation);
        div.appendChild(document.createElement("br"));
    });
}
LocationsDisplayController.prototype.selectLocation = function (location_id) {
    console.log("location: " + location);
    this.selectedLocation = locations[location_id];
    activityDisplayController.display();
}


//Static instance. USE THIS ONE!
var locationsDisplayController = new LocationsDisplayController();

//
//==========ActivityDisplayController
//

function ActivityDisplayController() {
    this.selectedActivity;
    this.init();
}

ActivityDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ActivityDisplayController.prototype.constructor = ActivityDisplayController;


ActivityDisplayController.prototype.init = function () {
}

ActivityDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "What activity will you do in the " + locationsDisplayController.selectedLocation.location_id + "?";

    div.appendChild(document.createElement("br"));

    var that = this;
    locationsDisplayController.selectedLocation.activities.forEach(activity => {

        this.btnActivity = document.createElement("BUTTON");
        that.btnActivity.innerHTML = activity;
        that.btnActivity.setAttribute("onClick", "activityDisplayController.selectActivity(\'" + activity + "\')");
        div.appendChild(this.btnActivity);
        div.appendChild(document.createElement("br"));
    });
}
ActivityDisplayController.prototype.selectActivity = function (activity) {
    console.log("location: " + location);
    this.selectedActivity = activity;
    turnActivity();
}

//Static instance. USE THIS ONE!
var activityDisplayController = new ActivityDisplayController();

//
//==========AfterTurnWaitingDisplayController
//

function AfterTurnWaitingDisplayController() {
    this.btnReady = document.createElement("BUTTON");
    this.btnCancelTurn = document.createElement("BUTTON");
    this.init();
}

AfterTurnWaitingDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
AfterTurnWaitingDisplayController.prototype.constructor = ActivityDisplayController;


AfterTurnWaitingDisplayController.prototype.init = function () {
    this.btnCancelTurn.innerHTML = "Cancel Turn";
    this.btnCancelTurn.setAttribute("onClick", "");

    this.btnReady.innerHTML = 'READY';
    this.btnReady.setAttribute("onClick", "toggleReady()");
}

AfterTurnWaitingDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "Waiting on other players(Cancel doesn't work.)...";

    div.appendChild(document.createElement("br"));

    div.appendChild(this.btnReady);

    div.appendChild(document.createElement("br"));

    div.appendChild(this.btnCancelTurn);
}

//Static instance. USE THIS ONE!
var afterTurnWaitingDisplayController = new AfterTurnWaitingDisplayController();