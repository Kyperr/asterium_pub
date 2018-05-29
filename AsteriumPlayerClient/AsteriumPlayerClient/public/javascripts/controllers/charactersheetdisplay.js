//
//==========CharacterSheetDisplayController
//

function CharacterSheetDisplayController() {
    this.displayDiv = document.createElement("div");
    this.nameDiv = document.createElement("div");
    this.statDiv = document.createElement("div");
    this.loadoutDiv = document.createElement("div");
    this.exposureDiv = document.createElement("div");
    this.init();
}

CharacterSheetDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
CharacterSheetDisplayController.prototype.constructor = CharacterSheetDisplayController;

CharacterSheetDisplayController.prototype.init = function () {
    this.nameDiv.setAttribute("class", "leading");
    this.statDiv.setAttribute("class", "grid-container");
    this.loadoutDiv.setAttribute("class", "grid-container");
    this.exposureDiv.setAttribute("class", "exposure");
}

CharacterSheetDisplayController.prototype.update = function () {
    this.displayDiv.innerHTML = "";
    this.statDiv.innerHTML = "";
    this.loadoutDiv.innerHTML = "";
    this.exposureDiv.innerHTML = "";

    this.nameDiv.innerHTML = user;
    this.displayDiv.appendChild(this.nameDiv);
    this.displayDiv.appendChild(this.nameDiv);


    //Stats
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
    luckDiv.innerHTML = "Luck: " + character.stats.luck + "/10";
    this.statDiv.appendChild(luckDiv);

    var intuDiv = document.createElement("div");
    intuDiv.setAttribute("class", "grid-content");
    intuDiv.innerHTML = "Intuition: " + character.stats.intuition + "/10";
    this.statDiv.appendChild(intuDiv);

    //Exposure
    this.displayDiv.appendChild(this.exposureDiv);
    this.exposureDiv.innerHTML = "Exposure: " + character.exposure;

    //Gear
    this.displayDiv.appendChild(this.loadoutDiv);

    //Head
    var headGear = character.loadout.HEAD;
    var headDiv = document.createElement("div");
    headDiv.setAttribute("class", "grid-content");
    headDiv.innerHTML = "Head:<br/>"
    if (headGear != null) {
        var headImage = document.createElement('img');
        headImage.setAttribute("class", "gear-image");
        headImage.src = "images/helmet.png";
        headDiv.appendChild(headImage);
        headDiv.innerHTML += headGear.item_name;
    } else {
        var nothingEq = document.createElement("div");
        nothingEq.setAttribute("class", "small");
        nothingEq.innerHTML += "Nothing Equipped";
        headDiv.appendChild(nothingEq);
    }
    this.loadoutDiv.appendChild(headDiv);

    //Chest
    var chestGear = character.loadout.CHEST;
    var chestDiv = document.createElement("div");
    chestDiv.setAttribute("class", "grid-content");
    chestDiv.innerHTML = "Chest:<br/>"
    if (chestGear != null) {
        var chestImage = document.createElement('img');
        chestImage.setAttribute("class", "gear-image");
        chestImage.src = "images/chest.png";
        chestDiv.appendChild(chestImage);
        chestDiv.innerHTML += chestGear.item_name;
    } else {
        var nothingEq = document.createElement("div");
        nothingEq.setAttribute("class", "small");
        nothingEq.innerHTML += "Nothing Equipped";
        chestDiv.appendChild(nothingEq);
    }
    this.loadoutDiv.appendChild(chestDiv);

    //Arms
    var armsGear = character.loadout.ARMS;
    var armsDiv = document.createElement("div");
    armsDiv.setAttribute("class", "grid-content");
    armsDiv.innerHTML = "Arms:<br/>"
    if (armsGear != null) {
        var legsImage = document.createElement('img');
        legsImage.setAttribute("class", "gear-image");
        legsImage.src = "images/arms.png";
        armsDiv.appendChild(chestImage);
        armsDiv.innerHTML += armsGear.item_name;
    } else {
        var nothingEq = document.createElement("div");
        nothingEq.setAttribute("class", "small");
        nothingEq.innerHTML += "Nothing Equipped";
        armsDiv.appendChild(nothingEq);
    }
    this.loadoutDiv.appendChild(armsDiv);

    //Legs
    var legsGear = character.loadout.LEGS;
    var legsDiv = document.createElement("div");
    legsDiv.setAttribute("class", "grid-content");
    legsDiv.innerHTML = "Legs:<br/>"
    if (legsGear != null) {
        var legsImage = document.createElement('img');
        legsImage.setAttribute("class", "gear-image");
        legsImage.src = "images/legs.png";
        legsDiv.appendChild(chestImage);
        legsDiv.innerHTML += legsGear.item_name;
    }else {
        var nothingEq = document.createElement("div");
        nothingEq.setAttribute("class", "small");
        nothingEq.innerHTML += "Nothing Equipped";
        legsDiv.appendChild(nothingEq);
    }
    this.loadoutDiv.appendChild(legsDiv);
}

CharacterSheetDisplayController.prototype.display = function () {
    var div = document.getElementById("character");
    div.innerHTML = "";

    div.appendChild(this.displayDiv);

}

var characterSheetDisplayController = new CharacterSheetDisplayController();