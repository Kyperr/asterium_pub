
var playerIsReady;

function getAuthToken(){
    var authToken = (localStorage.getItem("auth_token") == null) ? "" : localStorage.getItem("auth_token");
    return authToken;
}
