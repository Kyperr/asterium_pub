
//
//==========TurnSummaryDisplayController
//

function TurnSummaryDisplayController() {
    this.continueButton = document.createElement("BUTTON");
    this.init();
}

TurnSummaryDisplayController.prototype = Object.create(TurnSummaryDisplayController.prototype);
TurnSummaryDisplayController.prototype.constructor = AbstractDisplayController;

TurnSummaryDisplayController.prototype.init = function () {
    this.continueButton.innerHTML = "Continue";
    this.continueButton.setAttribute("onClick", "phaseChangeStartingActions[gamePhase]();");
    this.continueButton.setAttribute("class", "button");
}

TurnSummaryDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>Turn Summary(Not implemented).</b>";
    div.innerHTML += "<br/>";
    div.innerHTML += "Here is where a summary will display, detailing what changed during the last 'turn resolve'. This will include things such as community items gained, personal items gained, and events that transpired.";
    div.innerHTML += "<br/>";

    div.appendChild(this.continueButton);

}


var turnSummaryDisplayController = new TurnSummaryDisplayController();