
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
    this.cancelButton.innerHTML = "CANCEL";
    this.cancelButton.setAttribute("onClick", "actionDisplayController.display()");
    this.cancelButton.setAttribute("class", "button");
}

ViewInventoryDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>What item would you like to use?</b>";

    div.appendChild(document.createElement("br"));
    div.appendChild(document.createElement("br"));

    div.innerHTML += "Personal Inventory:";
    div.appendChild(document.createElement("br"));

    var personalItemDiv = document.createElement("div");
    personalItemDiv.setAttribute("class", "itemDiv");

    personalInventory.forEach(inventory => {
        var btnInventory = document.createElement("BUTTON");
        btnInventory.innerHTML = inventory.item_name;
        btnInventory.setAttribute("onClick", "viewInventoryDisplayController.selectInventory(\'" + inventory.item_name + "\', false)");
        btnInventory.setAttribute("class", "button");
        personalItemDiv.appendChild(btnInventory);
    });

    if(personalInventory.length == 0){
        personalItemDiv.innerHTML = "Empty..." 
    }

    div.appendChild(personalItemDiv);

    div.appendChild(document.createElement("br"));

    div.innerHTML += "Communal Inventory:";

    div.appendChild(document.createElement("br"));

    var communalItemDiv = document.createElement("div");
    communalItemDiv.setAttribute("class", "itemDiv");

    communalInventory.forEach(inventory => {
        var btnInventory = document.createElement("BUTTON");
        btnInventory.innerHTML = inventory.item_name;
        btnInventory.setAttribute("onClick", "viewInventoryDisplayController.selectInventory(\'" + inventory.item_name + "\', true)");//swap this to the item's I.D. later.
        btnInventory.setAttribute("class", "button");
        communalItemDiv.appendChild(btnInventory);
    });
    
    if(communalInventory.length == 0){
        communalItemDiv.innerHTML = "Empty..." 
    }

    div.appendChild(communalItemDiv);

    div.appendChild(this.cancelButton);
}

ViewInventoryDisplayController.prototype.selectInventory = function (inventoryName, isCommunal) {
    this.selectedInventory = inventoryName;
    this.isCommunal = isCommunal;
    itemInteractionDisplayController.display();
}

//Static instance. USE THIS ONE!
var viewInventoryDisplayController = new ViewInventoryDisplayController();
