
//
//==========JoinLobbyDisplayController
//

function JoinLobbyDisplayController() {
    this.nameInput = document.createElement("input");
    this.lobby_id = document.createElement("input");
    this.btn = document.createElement("BUTTON");
    this.init();
}

JoinLobbyDisplayController.prototype = Object.create(AbstractDisplayController.prototype);
JoinLobbyDisplayController.prototype.constructor = JoinLobbyDisplayController;


JoinLobbyDisplayController.prototype.init = function () {
    this.nameInput.setAttribute("id", "name");
    this.nameInput.setAttribute("name", "name");
    this.nameInput.setAttribute("class", "input-text");

    this.lobby_id.setAttribute("id", "lobby_id");
    this.lobby_id.setAttribute("name", "lobby_id");
    this.lobby_id.setAttribute("class", "input-text");

    this.btn.innerHTML = 'Join Lobby';
    this.btn.setAttribute("onClick", "joinAsPlayer()");
    this.btn.setAttribute("class", "button");
}

JoinLobbyDisplayController.prototype.display = function () {
    var div = document.getElementById("centralDiv");
    
    div.innerHTML = "";

    div.appendChild(document.createElement("br"));//Ew, all these br's. Use CSS, Daniel! Sincerely, Daniel.

    var joinDiv = document.createElement("div");
    joinDiv.setAttribute("class", "leading");
    joinDiv.innerHTML = "Join A lobby";
    div.appendChild(joinDiv);
    
    div.appendChild(document.createElement("br"));

    div.innerHTML += "Name:";

    div.appendChild(document.createElement("br"));

    div.appendChild(this.nameInput);

    div.appendChild(document.createElement("br"));

    div.innerHTML += "Lobby ID:";

    div.appendChild(document.createElement("br"));

    div.appendChild(this.lobby_id);
    
    div.appendChild(document.createElement("br"));

    div.appendChild(this.btn);
}


//Static instance. USE THIS ONE!
var joinLobbyDisplayController = new JoinLobbyDisplayController();
