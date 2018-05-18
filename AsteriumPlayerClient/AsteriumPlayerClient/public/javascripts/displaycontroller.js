
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

function displayWaitingForPlayers(div) {
    div.innerHTML = "Waiting for other players:";

    div.appendChild(document.createElement("br"));

    var btn = document.createElement("BUTTON");

    if (playerIsReady) {
        btn.innerHTML = 'READY';
    } else {
        btn.innerHTML = 'UNREADY';
    }

    btn.setAttribute("onClick", "toggleReady()");

    div.appendChild(btn);
}