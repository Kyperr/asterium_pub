
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
    useButton.innerHTML = "USE";
    useButton.setAttribute("onClick", "itemInteractionDisplayController.useItem()");

    cancelButton.innerHTML = "CANCEL";
    useButtcancelButtonon.setAttribute("onClick", "actionDisplayController.display()");
}

ItemInteractionDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>What would you like to do with this item?</b>";
    
    div.appendChild(document.createElement("br"));

    if(viewInventoryDisplayController.selectedInventory != null){
        div.appendChild(this.useButton);
        div.appendChild(document.createElement("br"));
    } 

    div.appendChild(this.cancelButton);
}

ItemInteractionDisplayController.prototype.useItem = function () {
    var targets = [user];
    var item = viewInventoryDisplayController.selectedInventory;
    var isCommunal = viewInventoryDisplayController.isCommunal;
    useItemAction(item, targets, isCommunal);
}


//Static instance. USE THIS ONE!
var itemInteractionDisplayController = new ItemInteractionDisplayController();
