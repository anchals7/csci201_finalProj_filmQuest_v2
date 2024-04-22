const Login = document.getElementById("Login");

Login.addEventListener("submit", function (event) {
    event.preventDefault();
    const username = document.getElementById("Username").value.trim();
    const password = document.getElementById("Password").value.trim();

    console.log("LOGGING IN: " + username + ", " + password);

    // make sure nothing is empty
    if (username !== "" && password !== "") {
        logIn(username, password);
    } else {
        alert("ERROR: All fields must be filled");
    }
});

function logIn(username, password) {
    let baseURL = "SignInOfUser";
    var userinfo = JSON.stringify({
        username: username,
        password: password
    });
    console.log(userinfo);
    fetch(baseURL, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
        body: userinfo
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Failed to fetch data");
            }
            console.log("Login Response: " + response);
            return response.json();
        })
        .then((data) => {
            console.log("Login Data: " + data);

            //sign in using localstorage
            //window.location.href = "index.html";
            //const id = { id: data };
            //const idJSON = JSON.stringify(id);
            //localStorage.setItem("userid", idJSON);
        })
        .catch(function (error) {
            console.log("request failed", error);
        });
}
