
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