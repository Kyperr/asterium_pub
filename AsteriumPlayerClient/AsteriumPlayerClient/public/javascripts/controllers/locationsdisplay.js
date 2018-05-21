
//
//==========LocationsDisplayController
//

function LocationsDisplayController() {
    this.btnLocation = document.createElement("BUTTON");
    this.selectedLocation;
    this.init();
}

LocationsDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
LocationsDisplayController.prototype.constructor = LocationsDisplayController;


LocationsDisplayController.prototype.init = function () {
}

LocationsDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "What location would you like to explore?";

    div.appendChild(document.createElement("br"));

    var that = this;
    locations.forEach(location => {
        this.btnLocation = document.createElement("BUTTON");

        //Should map the id to a display-friendly string and get it here.

        that.btnLocation.innerHTML = location.location_id;
        that.btnLocation.setAttribute("onClick", "locationsDisplayController.selectLocation(\'" + location.location_id + "\')");
        div.appendChild(this.btnLocation);
        div.appendChild(document.createElement("br"));
    });
}
LocationsDisplayController.prototype.selectLocation = function (location_id) {
    console.log("location: " + location);
    this.selectedLocation = locations[location_id];
    activityDisplayController.display();
}


//Static instance. USE THIS ONE!
var locationsDisplayController = new LocationsDisplayController();
