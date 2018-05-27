
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
    this.btnExplore.setAttribute("class", "button");
    this.btnExplore.innerHTML = "EXPLORE";

    this.btnStay.innerHTML = "STAY";
    //this.btnStay.setAttribute("onClick", "locationsDisplayController.display()");
    this.btnStay.onclick = function(){
        locationsDisplayController.selectLocation(controlRoom);

    }
    this.btnStay.setAttribute("class", "button");

    this.btnInventory.setAttribute("onClick", "viewInventoryDisplayController.display()");
    this.btnInventory.setAttribute("class", "button");
    this.btnInventory.innerHTML = "INVENTORY";
}

ActionDisplayController.prototype.display = function () {
    var div = document.getElementById("action");

    console.log("action");

    div.innerHTML = "<b>What action would you like to do?</b>";

    div.appendChild(this.btnExplore);
    div.appendChild(this.btnStay);
    div.appendChild(this.btnInventory);
}

//Static instance. USE THIS ONE!
var actionDisplayController = new ActionDisplayController();