
//
//==========LocationsDisplayController
//

function LocationsDisplayController() {
    this.selectedLocation;
    this.init();
}

LocationsDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
LocationsDisplayController.prototype.constructor = LocationsDisplayController;


LocationsDisplayController.prototype.init = function () {
}


LocationsDisplayController.prototype.display = function () {
    var div = document.getElementById("action");

    div.innerHTML = "<b>What location would you like to explore?</b>";

    div.appendChild(document.createElement("br"));

    locations.forEach(location => {
        var btnLocation = document.createElement("BUTTON");

        //Should map the id to a display-friendly string and get it here.

        btnLocation.innerHTML = location.location_name;
        btnLocation.onclick = function(){
            locationsDisplayController.selectLocation(location);
        }
        btnLocation.setAttribute("class", "button");
        div.appendChild(btnLocation);
    });
}

LocationsDisplayController.prototype.selectLocation = function (location) {
    console.log("location: " + location);
    this.selectedLocation = location;
    activityDisplayController.display();
}


//Static instance. USE THIS ONE!
var locationsDisplayController = new LocationsDisplayController();
