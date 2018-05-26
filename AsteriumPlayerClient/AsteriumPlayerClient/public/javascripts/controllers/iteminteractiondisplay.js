
//
//==========ItemInteractionDisplayController
//

function ItemInteractionDisplayController() {
    this.useButton = document.createElement("BUTTON");
    this.cancelButton = document.createElement("BUTTON");
    this.init();
}

ItemInteractionDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ItemInteractionDisplayController.prototype.constructor = ItemInteractionDisplayController;

ItemInteractionDisplayController.prototype.init = function () {
    this.useButton.innerHTML = "USE";
    this.useButton.setAttribute("onClick", "itemInteractionDisplayController.useItem()");
    this.useButton.setAttribute("class", "button");

    this.cancelButton.innerHTML = "CANCEL";
    this.cancelButton.setAttribute("onClick", "actionDisplayController.display()");
    this.cancelButton.setAttribute("class", "button");
}

ItemInteractionDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>What would you like to do with this item?</b>";
    
    div.appendChild(document.createElement("br"));

    if(viewInventoryDisplayController.selectedInventory != null){
        div.appendChild(this.useButton);
    } 

    div.appendChild(this.cancelButton);
}

ItemInteractionDisplayController.prototype.useItem = function () {
    var targets = [user];
    var item = viewInventoryDisplayController.selectedInventory;
    var isCommunal = viewInventoryDisplayController.isCommunal;

    var div = document.getElementById("centralDiv");

    var modalDiv = document.createElement("div");
    modalDiv.class = "modal-dialog";

    modalDiv.innerHTML = item + ":<br/>";
    modalDiv.innerHTML += item + "Item Description";

    modalDiv.appendChild();


    if (confirm('Are you sure you want to use this ' + item + '?')) {
        useItemAction(item, targets, isCommunal);
    } else {
        // Do nothing!
    }
}


//Static instance. USE THIS ONE!
var itemInteractionDisplayController = new ItemInteractionDisplayController();
