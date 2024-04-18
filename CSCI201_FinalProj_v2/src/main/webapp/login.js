const Signup = document.getElementById("Signup");
const Login = document.getElementById("Login");

function GoHome() {
    window.location.href = "index.html";
}

Signup.addEventListener("submit", function (event) {
    event.preventDefault();
    const usernameSignup = document
        .getElementById("UsernameSignup")
        .value.trim();
    const passwordSignup = document
        .getElementById("PasswordSignup")
        .value.trim();
    const passwordConfirm = document
        .getElementById("PasswordConfirm")
        .value.trim();
    const email = document.getElementById("Email").value.trim();

    console.log(usernameSignup);
    console.log(passwordSignup);
    console.log(passwordConfirm);
    console.log(email);

    // make sure nothing is empty
    if (
        usernameSignup !== "" &&
        passwordSignup !== "" &&
        passwordConfirm !== "" &&
        email !== ""
    ) {
        if (passwordSignup === passwordConfirm) {
            signUp(usernameSignup, passwordSignup, email);
        } else {
            alert("ERROR: Passwords must match");
        }
    } else {
        alert("ERROR: All fields must be filled");
    }
});

function signUp(username, password, email) {
    let baseURL = "SignupServlet";
    var userinfo = JSON.stringify({
        username: username,
        password: password,
        email: email
    });
    console.log(userinfo);
    fetch(baseURL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: userinfo
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Failed to fetch data");
            }
            return response.json();
        })
        .then((json) => {
            console.log(json);
        })
        .catch(function (error) {
            console.log("request failed", error);
        });
}

Login.addEventListener("submit", function (event) {
    event.preventDefault();
    const usernameLogin = document.getElementById("UsernameLogin").value.trim();
    const passwordLogin = document.getElementById("PasswordLogin").value.trim();

    console.log(usernameLogin);
    console.log(passwordLogin);

    // make sure nothing is empty
    if (usernameLogin !== "" && passwordLogin !== "") {
        logIn(usernameLogin, passwordLogin);
    } else {
        alert("ERROR: All fields must be filled");
    }
});

function logIn(username, password) {
    let baseURL = "LoginServlet";
    var userinfo = JSON.stringify({
        username: username,
        password: password
    });
    console.log(userinfo);
    fetch(baseURL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: userinfo
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Failed to fetch data");
            }
            console.log(response);
            return response.json();
        })
        .then((data) => {
            console.log(data);
            window.location.href = "index.html";
            const id = { id: data };
            const idJSON = JSON.stringify(id);
            localStorage.setItem('userid', idJSON);
        })
        .catch(function (error) {
            console.log("request failed", error);
        });
}
