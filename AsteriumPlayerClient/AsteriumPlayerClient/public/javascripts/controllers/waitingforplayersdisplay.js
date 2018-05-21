
//
//==========WaitingForPlayersDisplayController
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