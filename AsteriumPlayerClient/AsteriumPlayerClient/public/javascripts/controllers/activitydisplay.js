
//
//==========ActivityDisplayController
//

function ActivityDisplayController() {
    this.selectedActivity;
    this.btnCancel = document.createElement("BUTTON");
    this.init();
}

ActivityDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ActivityDisplayController.prototype.constructor = ActivityDisplayController;


ActivityDisplayController.prototype.init = function () {

    this.btnCancel.innerHTML = "CANCEL";
    this.btnCancel.setAttribute("onClick", "actionDisplayController.display()");
    this.btnCancel.setAttribute("class", "button");
}


ActivityDisplayController.prototype.display = function () {
    var div = document.getElementById("action");

    var location = locationsDisplayController.selectedLocation;

    div.innerHTML = "<b>What activity will you do in the " + location.location_name + "?</b>";

    div.appendChild(document.createElement("br"));

    var that = this;

    location.activities.forEach(activity => {
        that.btnActivity = document.createElement("BUTTON");
        that.btnActivity.innerHTML = activity;
        that.btnActivity.setAttribute("onClick", "activityDisplayController.selectActivity(\'" + activity + "\')");
        that.btnActivity.setAttribute("class", "button");
        div.appendChild(that.btnActivity);
    });

    div.innerHTML += "Location items:";

    var locationItemDiv = document.createElement("div");
    locationItemDiv.setAttribute("class", "itemDiv");

    var counter = 0;
    personalInventory.forEach(inventory => {

        if (inventory.is_location_item) {
            inventory.use_locations.forEach((locationType) => {
                if (location.location_type == locationType) {
                    counter++;
                    var btnInventory = document.createElement("BUTTON");
                    btnInventory.innerHTML = inventory.item_name;
                    btnInventory.onclick = function () {
                        itemTurnActivity(inventory, location);
                    }
                    btnInventory.setAttribute("class", "button");
                    locationItemDiv.appendChild(btnInventory);

                }
            });
        }
    });

    if (counter == 0) {
        locationItemDiv.innerHTML = "Empty..."
    }

    div.appendChild(locationItemDiv);

    div.appendChild(this.btnCancel);
}
ActivityDisplayController.prototype.selectActivity = function (activity) {
    console.log("location: " + location);
    this.selectedActivity = activity;
    turnActivity();
}

//Static instance. USE THIS ONE!
var activityDisplayController = new ActivityDisplayController();
