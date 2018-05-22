
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
    this.btnCancelTurn.innerHTML = "CANCEL TURN";
    this.btnCancelTurn.setAttribute("onClick", "actionDisplayController.display()");
    this.btnCancelTurn.setAttribute("class", "button");

    this.btnReady.innerHTML = 'READY';
    this.btnReady.setAttribute("onClick", "toggleReady()");
    this.btnReady.setAttribute("class", "button");
}

AfterTurnWaitingDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>Waiting on other players(Cancel doesn't work.)...</b>";

    div.appendChild(this.btnReady);

    div.appendChild(this.btnCancelTurn);
}

//Static instance. USE THIS ONE!
var afterTurnWaitingDisplayController = new AfterTurnWaitingDisplayController();