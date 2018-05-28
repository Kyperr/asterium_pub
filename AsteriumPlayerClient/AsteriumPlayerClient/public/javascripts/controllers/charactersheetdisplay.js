//
//==========CharacterSheetDisplayController
//

function CharacterSheetDisplayController() {
}

CharacterSheetDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
CharacterSheetDisplayController.prototype.constructor = CharacterSheetDisplayController;

CharacterSheetDisplayController.prototype.init = function () {
}

CharacterSheetDisplayController.prototype.display = function () {
    var div = document.getElementById("character");
    div.style.display = "block";
    div.innerHTML = "";

    var nameDiv = document.createElement("div");
    nameDiv.setAttribute("class", "leading");
    nameDiv.innerHTML = "TEST";
    div.appendChild(nameDiv);

}

var characterSheetDisplayController = new CharacterSheetDisplayController();