//
//==========CharacterSheetDisplayController
//

function CharacterSheetDisplayController() {
    this.displayDiv = document.createElement("div");
    this.nameDiv = document.createElement("div");
    this.statDiv = document.createElement("div");
}

CharacterSheetDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
CharacterSheetDisplayController.prototype.constructor = CharacterSheetDisplayController;

CharacterSheetDisplayController.prototype.init = function () {
}

CharacterSheetDisplayController.prototype.update = function () {
    this.displayDiv.innerHTML = "";
    this.statDiv.innerHTML = "";
    
    this.nameDiv.setAttribute("class", "leading");
    this.nameDiv.innerHTML = user;
    this.displayDiv.appendChild(this.nameDiv);
    this.displayDiv.appendChild(this.nameDiv);


    this.statDiv.setAttribute("class", "grid-container");
    this.displayDiv.appendChild(this.statDiv);

    var healthDiv = document.createElement("div");
    healthDiv.setAttribute("class", "grid-content");
    healthDiv.innerHTML = "Health: " + character.stats.health + "/10";
    this.statDiv.appendChild(healthDiv);

    var stamDiv = document.createElement("div");
    stamDiv.setAttribute("class", "grid-content");
    stamDiv.innerHTML = "Stamina: " + character.stats.stamina + "/10";
    this.statDiv.appendChild(stamDiv);

    var luckDiv = document.createElement("div");
    luckDiv.setAttribute("class", "grid-content");
    luckDiv.innerHTML = "Luck: " + character.stats.luck+ "/10";
    this.statDiv.appendChild(luckDiv);

    var intuDiv = document.createElement("div");
    intuDiv.setAttribute("class", "grid-content");
    intuDiv.innerHTML = "Intuition: " + character.stats.intuition + "/10";
    this.statDiv.appendChild(intuDiv);
}

CharacterSheetDisplayController.prototype.display = function () {
    var div = document.getElementById("character");
    div.innerHTML = "";
    
    div.appendChild(this.displayDiv);

}

var characterSheetDisplayController = new CharacterSheetDisplayController();