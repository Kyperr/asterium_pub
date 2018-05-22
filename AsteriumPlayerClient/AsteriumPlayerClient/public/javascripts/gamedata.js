
var gameIsStarted = false;

var playerIsReady = false;

var gamePhase = "PLAYERS_JOINING";

var user;

var turnActionSelectedLocation;

var locations = [];

var personalInventory = [];

//Just a variable for the mvp.
var usedTheBeacon = false;

var communalInventory = [];

function getAuthToken(){
    var authToken = (localStorage.getItem("auth_token") == null) ? "" : localStorage.getItem("auth_token");
    return authToken;
}
