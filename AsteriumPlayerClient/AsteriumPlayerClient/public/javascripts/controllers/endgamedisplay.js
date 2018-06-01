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
    var div = document.getElementById("tabDiv");

    div.innerHTML = "";

    var endGameDiv = document.createElement("div");
    endGameDiv.setAttribute("class", "endGameDiv");

    endGameDiv.innerHTML = "<b>Game Over</b>"

    div.appendChild(endGameDiv);
}

var endGameDisplayController = new EndGameDisplayController();
