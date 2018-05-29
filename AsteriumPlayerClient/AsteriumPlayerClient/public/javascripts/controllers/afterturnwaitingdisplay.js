
//
//==========AfterTurnWaitingDisplayController
//

function AfterTurnWaitingDisplayController() {
    this.btnReady = document.createElement("BUTTON");
    this.btnCancelTurn = document.createElement("BUTTON");
    this.displayDiv = document.createElement("div");
    this.init();
}

AfterTurnWaitingDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
AfterTurnWaitingDisplayController.prototype.constructor = ActivityDisplayController;


AfterTurnWaitingDisplayController.prototype.init = function () {
    this.btnCancelTurn.innerHTML = "CANCEL TURN";
    this.btnCancelTurn.onclick = function(){
        actionDisplayController.display();
        setReadyStatus(false);
    }
    this.btnCancelTurn.setAttribute("class", "button");

    this.btnReady.innerHTML = 'READY';
    this.btnReady.setAttribute("onClick", "toggleReady()");
    this.btnReady.setAttribute("class", "button");
}

AfterTurnWaitingDisplayController.prototype.update = function () {
    this.displayDiv.innerHTML = "";

    this.displayDiv.innerHTML = "<b>Waiting on other players...</b>";
    
        console.log("playerIsReady: " + playerIsReady);
    if (playerIsReady) {
        console.log();
        afterTurnWaitingDisplayController.btnReady.innerHTML = 'UNREADY';
    } else {
        afterTurnWaitingDisplayController.btnReady.innerHTML = 'READY';
    }

    this.displayDiv.appendChild(this.btnReady);

    this.displayDiv.appendChild(this.btnCancelTurn);
    
}


AfterTurnWaitingDisplayController.prototype.display = function () {
    var div = document.getElementById("action");
    div.innerHTML = "";
    this.update();
    div.appendChild(this.displayDiv);
}

//Static instance. USE THIS ONE!
var afterTurnWaitingDisplayController = new AfterTurnWaitingDisplayController();