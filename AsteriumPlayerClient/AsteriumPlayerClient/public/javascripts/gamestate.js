


function hasAnAuthToken() {
    console.log("Checking auth_token: " + localStorage.getItem("auth_token"));
    return localStorage.getItem("auth_token") != null;
}
