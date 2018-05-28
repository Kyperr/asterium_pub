//
//==========ViewInventoryDisplayController
//

function ViewInventoryDisplayController() {
    this.displayDiv;
    this.cancelButton = document.createElement("BUTTON");
    this.selectedInventory;
    this.isCommunal;

    //Modal Elements
    this.modal = document.createElement("div");
    this.modalContent = document.createElement("div");
    this.modalClose = document.createElement("BUTTON");
    this.modalUse = document.createElement("BUTTON");

    this.init();
}

ViewInventoryDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ViewInventoryDisplayController.prototype.constructor = ViewInventoryDisplayController;

ViewInventoryDisplayController.prototype.init = function () {

    this.displayDiv = document.createElement("div");

    //Modal Dialog:
    this.modal.setAttribute("class", "modal");
    this.modal.appendChild(this.modalContent);

    this.modalContent.setAttribute("class", "modal-content");

    this.modalUse.innerHTML = "Use Item";
    this.modalUse.setAttribute("onClick", "viewInventoryDisplayController.useItem()");
    this.modalUse.setAttribute("class", "button");

    this.modalClose.innerHTML = "Close";
    this.modalClose.setAttribute("onClick", "viewInventoryDisplayController.setModalDisplay('none')");
    this.modalClose.setAttribute("class", "button");

}


ViewInventoryDisplayController.prototype.update = function () {
    
    this.displayDiv.innerHTML = "";

    var persInvDiv = document.createElement("div");
    persInvDiv.setAttribute("class", "leading");
    persInvDiv.innerHTML = "Personal Inventory";
    this.displayDiv.appendChild(persInvDiv);

    var personalItemDiv = document.createElement("div");
    personalItemDiv.setAttribute("class", "itemDiv");

    personalInventory.forEach(inventory => {
        var btnInventory = document.createElement("BUTTON");
        btnInventory.innerHTML = inventory.item_name;
        btnInventory.onclick = function(){
            viewInventoryDisplayController.selectInventory(inventory, false);
        }
        btnInventory.setAttribute("class", "button");
        personalItemDiv.appendChild(btnInventory);
    });

    if (personalInventory.length == 0) {
        personalItemDiv.innerHTML = "Empty..."
    }

    this.displayDiv.appendChild(personalItemDiv);

    var comInvDiv = document.createElement("div");
    comInvDiv.setAttribute("class", "leading");
    comInvDiv.innerHTML = "Communal Inventory";
    this.displayDiv.appendChild(comInvDiv);

    var communalItemDiv = document.createElement("div");
    communalItemDiv.setAttribute("class", "itemDiv");

    communalInventory.forEach(inventory => {
        var btnInventory = document.createElement("BUTTON");
        btnInventory.innerHTML = inventory.item_name;
        btnInventory.onclick = function(){
            viewInventoryDisplayController.selectInventory(inventory, true);
        }
        btnInventory.setAttribute("class", "button");
        communalItemDiv.appendChild(btnInventory);
    });

    if (communalInventory.length == 0) {
        communalItemDiv.innerHTML = "Empty..."
    }

    this.displayDiv.appendChild(communalItemDiv);
}

ViewInventoryDisplayController.prototype.display = function () {

    this.update();

    var div = document.getElementById("inventory");

    div.innerHTML = "";

    div.appendChild(this.displayDiv);

    //Modal stuff.
    div.appendChild(this.modal);
}

ViewInventoryDisplayController.prototype.selectInventory = function (inventory, isCommunal) {

    this.selectedInventory = inventory;
    this.isCommunal = isCommunal;

    var div = document.getElementById("action");

    this.modalContent.innerHTML = inventory.item_name + ":<br/>";
    this.modalContent.innerHTML += inventory.item_description;

    this.modalContent.appendChild(document.createElement("br"));

    if(inventory.is_location_item){
        var span = document.createElement("span");

        span.innerHTML = "This item must be used at:";

        inventory.use_locations.forEach((location) => {
            console.log("Locations to use at:");
            console.log(location);
        });

        this.modalContent.appendChild(span);

        this.modalContent.appendChild(document.createElement("br"));
    } else {
        this.modalContent.appendChild(this.modalUse);
    }
    this.modalContent.appendChild(this.modalClose);

    console.log("GETTING HERE~!");
    this.modal.style.display = "block";

    //itemInteractionDisplayController.display();
}

ViewInventoryDisplayController.prototype.useItem = function () {
    var targets = [user];
    var item = viewInventoryDisplayController.selectedInventory;
    var isCommunal = viewInventoryDisplayController.isCommunal;

    this.modal.style.display = "none";
    useItemAction(item, targets, isCommunal);
}

ViewInventoryDisplayController.prototype.setModalDisplay = function (display) {
    this.modal.style.display = display;
}
//Static instance. USE THIS ONE!
var viewInventoryDisplayController = new ViewInventoryDisplayController();
