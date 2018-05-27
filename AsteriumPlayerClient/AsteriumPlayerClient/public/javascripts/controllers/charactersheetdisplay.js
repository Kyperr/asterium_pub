//
//==========CharacterSheetDisplayController
//

function CharacterSheetDisplayController() {
}

CharacterSheetDisplayController.prototype = new CharacterSheetDisplayController();
CharacterSheetDisplayController.prototype.constructor = AbstractDisplayController;

CharacterSheetDisplayController.prototype.init = function () {
}

CharacterSheetDisplayController.prototype.display = function () {
    var div = document.getElementById("character");
    div.innerHTML = "";

    var nameDiv = document.createElement("div");
    nameDiv.setAttribute("class", "leading");
    nameDiv.innerHTML = user;
    div.appendChild(nameDiv);

}

var characterSheetDisplayController = new CharacterSheetDisplayController();