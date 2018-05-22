
//
//==========AbstractDisplayController
//

function AbstractDisplayController() {
}

AbstractDisplayController.prototype = new AbstractDisplayController();
AbstractDisplayController.prototype.constructor = AbstractDisplayController;

AbstractDisplayController.prototype.init = function () {
}

AbstractDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");

    div.innerHTML = "<b>This Display Controller is not correctly implemented.</b>"
}