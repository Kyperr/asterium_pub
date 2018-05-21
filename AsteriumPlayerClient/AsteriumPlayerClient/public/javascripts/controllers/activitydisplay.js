
//
//==========ActivityDisplayController
//

function ActivityDisplayController() {
    this.selectedActivity;
    this.init();
}

ActivityDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
ActivityDisplayController.prototype.constructor = ActivityDisplayController;


ActivityDisplayController.prototype.init = function () {
}

ActivityDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>What activity will you do in the " + locationsDisplayController.selectedLocation.location_id + "?</b>";

    div.appendChild(document.createElement("br"));

    var that = this;
    locationsDisplayController.selectedLocation.activities.forEach(activity => {

        this.btnActivity = document.createElement("BUTTON");
        that.btnActivity.innerHTML = activity;
        that.btnActivity.setAttribute("onClick", "activityDisplayController.selectActivity(\'" + activity + "\')");
        div.appendChild(this.btnActivity);
        div.appendChild(document.createElement("br"));
    });
}
ActivityDisplayController.prototype.selectActivity = function (activity) {
    console.log("location: " + location);
    this.selectedActivity = activity;
    turnActivity();
}

//Static instance. USE THIS ONE!
var activityDisplayController = new ActivityDisplayController();
