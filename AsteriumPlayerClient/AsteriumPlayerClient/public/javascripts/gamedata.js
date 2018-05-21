
var gameIsStarted = false;

var playerIsReady = false;

var gamePhase = "PLAYERS_JOINING";

var user;

var turnActionSelectedLocation;

var locations = [];

var personalInventory = [];

var communalInventory = [];

function getAuthToken(){
    var authToken = (localStorage.getItem("auth_token") == null) ? "" : localStorage.getItem("auth_token");
    return authToken;
}
