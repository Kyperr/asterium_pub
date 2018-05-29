
//
//==========TurnSummaryDisplayController
//

function TurnSummaryDisplayController() {
    this.continueButton = document.createElement("BUTTON");
    this.displayDiv =  document.createElement("div");
    this.init();
}

TurnSummaryDisplayController.prototype = Object.create(TurnSummaryDisplayController.prototype);
TurnSummaryDisplayController.prototype.constructor = AbstractDisplayController;

TurnSummaryDisplayController.prototype.init = function () {
    this.continueButton.innerHTML = "Continue";
    this.continueButton.setAttribute("onClick", "phaseChangeStartingActions[gamePhase]();");
    this.continueButton.setAttribute("class", "button");
}

TurnSummaryDisplayController.prototype.update = function () {
    this.displayDiv.innerHTML = "";

    summaryStrings.forEach(string => {this.displayDiv.innerHTML += string});
    
    this.displayDiv.appendChild(this.continueButton);
}


TurnSummaryDisplayController.prototype.display = function () {
    var div = document.getElementById("action");

    div.innerHTML = "";

    div.appendChild(this.displayDiv);

}


var turnSummaryDisplayController = new TurnSummaryDisplayController();