
//
//==========ViewInventoryDisplayController
//

function ViewInventoryDisplayController() {
    this.init();
}

ViewInventoryDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ViewInventoryDisplayController.prototype.constructor = ViewInventoryDisplayController;

ActivityDisplayController.prototype.init = function () {
}

ViewInventoryDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>What item would you like to use?</b>";

    div.appendChild(document.createElement("br"));

    div.innerHTML = "Personal Inventory:";

    personalInventory.forEach(inventory => {
        
    });

    div.appendChild(document.createElement("br"));

    div.innerHTML = "Communal Inventory:";

}

ViewInventoryDisplayController.prototype.selectActivity = function (activity) {
    console.log("location: " + location);
    this.selectedActivity = activity;
    turnActivity();
}

//Static instance. USE THIS ONE!
var viewInventoryDisplayController = new ViewInventoryDisplayController();
