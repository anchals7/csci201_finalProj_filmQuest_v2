const Signup = document.getElementById("Signup");

Signup.addEventListener("submit", function (event) {
    event.preventDefault();
    const name = document.getElementById("Name").value.trim();
    const email = document.getElementById("Email").value.trim();
    const username = document.getElementById("Username").value.trim();
    const password = document.getElementById("Password").value.trim();
    const passwordConfirm = document
        .getElementById("PasswordConfirm")
        .value.trim();

    console.log(
        "REGISTERING: " +
            name +
            ", " +
            email +
            ", " +
            username +
            ", " +
            password +
            ", " +
            passwordConfirm
    );

    // make sure nothing is empty
    if (
        name !== "" &&
        email !== "" &&
        username !== "" &&
        password !== "" &&
        passwordConfirm !== ""
    ) {
        if (password === passwordConfirm) {
            signUp(name, email, username, password);
        } else {
            alert("ERROR: Passwords must match.");
        }
    } else {
        alert("ERROR: All fields must be filled.");
    }
});

function signUp(name, email, username, password) {
    let baseURL = "RegistrationOfUser";
    var userinfo = JSON.stringify({
        name: name,
        email: email,
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
            return response.json();
        })
        .then((data) => {
            console.log(data);
            if (data === "This user does not exist") {
                alert("ERROR: Invalid username.");
            } else if (data === "Incorrect password") {
                alert("ERROR: Invalid password.");
            } else {
                window.location.href = "index.html";
                const id = { id: data };
                const idJSON = JSON.stringify(id);
                localStorage.setItem("userid", idJSON);
            }
        })
        .catch(function (error) {
            console.log("request failed", error);
        });
}
