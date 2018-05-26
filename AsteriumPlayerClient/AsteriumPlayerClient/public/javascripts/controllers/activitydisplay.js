
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
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>What activity will you do in the " + locationsDisplayController.selectedLocation.location_name + "?</b>";

    div.appendChild(document.createElement("br"));

    var that = this;

    locationsDisplayController.selectedLocation.activities.forEach(activity => {
        that.btnActivity = document.createElement("BUTTON");
        that.btnActivity.innerHTML = activity;
        that.btnActivity.setAttribute("onClick", "activityDisplayController.selectActivity(\'" + activity + "\')");
        that.btnActivity.setAttribute("class", "button");
        div.appendChild(that.btnActivity);
    });

    div.appendChild(this.btnCancel);
}
ActivityDisplayController.prototype.selectActivity = function (activity) {
    console.log("location: " + location);
    this.selectedActivity = activity;
    turnActivity();
}

//Static instance. USE THIS ONE!
var activityDisplayController = new ActivityDisplayController();
