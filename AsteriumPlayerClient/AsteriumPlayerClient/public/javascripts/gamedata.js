
var gameIsStarted = false;

var playerIsReady = false;

var locations = [];

function getAuthToken(){
    var authToken = (localStorage.getItem("auth_token") == null) ? "" : localStorage.getItem("auth_token");
    return authToken;
}
