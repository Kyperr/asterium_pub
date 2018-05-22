//
//==========EndGameDisplayController
//

function EndGameDisplayController() {
}

EndGameDisplayController.prototype = new EndGameDisplayController();
EndGameDisplayController.prototype.constructor = AbstractDisplayController;

EndGameDisplayController.prototype.init = function () {
}

EndGameDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "";

    var endGameDiv = document.createElement("div");
    endGameDiv.setAttribute("class", "endGameDev");

    if(usedTheBeacon){
        endGameDiv.innerHTML = "<b>You Won!<n/>";
    } else {
        endGameDiv.innerHTML = "<b>Game Over.</b>"
    }

    div.appendChild(endGameDiv);
}

var endGameDisplayController = new EndGameDisplayController();