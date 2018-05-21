
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

    //this.btnStay.setAttribute("onClick", "toggleReady()");
    this.btnStay.innerHTML = "STAY";

    this.btnInventory.setAttribute("onClick", "viewInventoryDisplayController.display()");
    this.btnInventory.innerHTML = "INVENTORY";
}

ActionDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    console.log("action");

    div.innerHTML = "<b>What action would you like to do?</b>";

    div.appendChild(document.createElement("br"));
    div.appendChild(this.btnExplore);
    div.appendChild(document.createElement("br"));
    div.appendChild(this.btnStay);
    div.appendChild(document.createElement("br"));
    div.appendChild(this.btnInventory);
}

//Static instance. USE THIS ONE!
var actionDisplayController = new ActionDisplayController();