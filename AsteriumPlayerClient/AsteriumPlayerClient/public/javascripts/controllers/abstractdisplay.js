
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
    var div = document.getElementById("action");

    div.innerHTML = "<b>This Display Controller is not correctly implemented.</b>"
}

function deshitify(str) {
    var frags = str.toLowerCase().split('_');
    for (i = 0; i < frags.length; i++) {
        frags[i] = frags[i].charAt(0).toUpperCase() + frags[i].slice(1);
    }
    return frags.join(' ');
}