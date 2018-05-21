
//
//==========ViewInventoryDisplayController
//

function ViewInventoryDisplayController() {
    this.cancelButton = document.createElement("BUTTON");
    this.selectedInventory;
    this.isCommunal;
    this.init();
}

ViewInventoryDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ViewInventoryDisplayController.prototype.constructor = ViewInventoryDisplayController;

ViewInventoryDisplayController.prototype.init = function () {
    cancelButton.innerHTML = "CANCEL";
    cancelButton.setAttribute("onClick", "actionDisplayController.display()");
}

ViewInventoryDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>What item would you like to use?</b>";

    div.appendChild(document.createElement("br"));

    div.innerHTML += "Personal Inventory:";
    div.appendChild(document.createElement("br"));

    personalInventory.forEach(inventory => {
        var btnInventory = document.createElement("BUTTON");
        btnInventory.innerHTML = inventory.name;
        btnInventory.setAttribute("onClick", "viewInventoryDisplayController.selectInventory(\'" + inventory.name + "\', false)");
        div.appendChild(this.btnLocation);
        div.appendChild(document.createElement("br"));
    });

    div.appendChild(document.createElement("br"));

    div.append += "Communal Inventory:";
    div.appendChild(document.createElement("br"));

    communalInventory.forEach(inventory => {
        var btnInventory = document.createElement("BUTTON");
        btnInventory.innerHTML = inventory.name;
        btnInventory.setAttribute("onClick", "viewInventoryDisplayController.selectInventory(\'" + inventory.name + "\', true)");//swap this to the item's I.D. later.
        div.appendChild(this.btnLocation);
        div.appendChild(document.createElement("br"));
    });

    div.appendChild(this.cancelButton);
}

ViewInventoryDisplayController.prototype.selectInventory = function (inventoryName, isCommunal) {
    this.selectedInventory = inventoryName;
    this.isCommunal = isCommunal;
    itemInteractionDisplayController.display();
}

//Static instance. USE THIS ONE!
var viewInventoryDisplayController = new ViewInventoryDisplayController();
