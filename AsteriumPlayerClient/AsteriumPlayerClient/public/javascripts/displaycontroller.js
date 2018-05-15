

window.onload = function () {
    var authToken = localStorage.getItem("auth_token");
    if (authToken == null) {
        console.log("test1");
        document.getElementById("centralDiv").innerHTML = "<p>You should join a lobby.";
        addJoinLobby(document.getElementById("centralDiv"));
    } else {
        console.log("test2");
        document.getElementById("centralDiv").innerHTML = "<p>There is an auth_token: " + authToken;
    }
};

function addJoinLobby(div) {
    
    var name = document.createElement("input");
    name.setAttribute("id", "name");
    name.setAttribute("name", "name");
    div.appendChild(name);

    div.appendChild(document.createElement("br"));

    var lobby_id = document.createElement("input");
    lobby_id.setAttribute("id", "lobby_id");
    lobby_id.setAttribute("name", "lobby_id");
    div.appendChild(lobby_id);
    
    div.appendChild(document.createElement("br"));

    var btn = document.createElement("BUTTON");
    btn.innerHTML = 'Join Lobby';
    btn.setAttribute("onClick", "joinAsPlayer()");
    div.appendChild(btn);

}