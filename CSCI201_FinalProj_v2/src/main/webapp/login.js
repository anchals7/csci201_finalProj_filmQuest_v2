// Get user's input
document.getElementById("Login").addEventListener("submit", function (event) {
    event.preventDefault();
    const username = document.getElementById("Username").value.trim();
    const password = document.getElementById("Password").value.trim();

    console.log("LOGGING IN: " + username + ", " + password);

    // ensure neither field is empty
    if (
        username !== null &&
        username !== "" &&
        password !== "" &&
        password !== null
    ) {
        var credentials = JSON.stringify({
            username: username,
            password: password
        });
        console.log(credentials);
        UserLogin(credentials);
    } else {
        alert("ERROR: All fields must be filled");
    }
});

// verify the credentials
function UserLogin(credentials) {
    let servlet = "SignInOfUser";
    fetch(servlet, {
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
        .then((id) => {
            console.log("Login Data: " + data);

            if (id === -1) {
                alert("Invalid Username or Password.");
            } else {
                Redirect(id);
            }
        })
        .catch(function (error) {
            console.log("request failed", error);
        });
}

// Log in user and redirect them to the search page
function Redirect(data) {
    localStorage.setItem("userid", data);
    window.location.href = "search.html";
}
