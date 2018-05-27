//
//==========GameTabsDisplayController
//

function GameTabsDisplayController() {
    this.actionTabButton = document.createElement("BUTTON");
    this.inventoryTabButton = document.createElement("BUTTON");
    this.playerTabButton = document.createElement("BUTTON");
    this.init();
}

GameTabsDisplayController.prototype = Object.create(GameTabsDisplayController.prototype);
GameTabsDisplayController.prototype.constructor = GameTabsDisplayController;

GameTabsDisplayController.prototype.init = function () {
    this.actionTabButton.innerHTML = "Action";
    this.actionTabButton.setAttribute("class", "tablink");
    this.actionTabButton.onclick = function () {
        gameTabsDisplayController.openPage("action");
    }

    this.inventoryTabButton.innerHTML = "Inventory";
    this.inventoryTabButton.setAttribute("class", "tablink");
    this.inventoryTabButton.onclick = function () {
        gameTabsDisplayController.openPage("inventory");
    }

    this.playerTabButton.innerHTML = "Character"
    this.playerTabButton.setAttribute("class", "tablink");
    this.playerTabButton.onclick = function () {
        gameTabsDisplayController.openPage("character");
    }
}

GameTabsDisplayController.prototype.display = function () {
    var div = document.getElementById("tabDiv");
    div.style.display = "block";

    div.appendChild(this.actionTabButton);
    div.appendChild(this.inventoryTabButton);
    div.appendChild(this.playerTabButton);
    
    document.getElementById("action").style.display = "block";
}

GameTabsDisplayController.prototype.openPage = function(divToShow) {
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    document.getElementById(divToShow).style.display = "block";
}

var gameTabsDisplayController = new GameTabsDisplayController();