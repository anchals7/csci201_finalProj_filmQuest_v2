document.getElementById("Signup").addEventListener("submit", function (event) {
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

    // ensure none of the fields are empty
    if (
        name !== "" &&
        email !== "" &&
        username !== "" &&
        password !== "" &&
        passwordConfirm !== ""
    ) {
        // ensure passwords match
        if (password === passwordConfirm) {
            var credentials = JSON.stringify({
                name: name,
                email: email,
                username: username,
                password: password
            });
            console.log(credentials);
            UserSignUp(credentials);
        } else {
            alert("ERROR: Passwords must match.");
        }
    } else {
        alert("ERROR: All fields must be filled.");
    }
});

function UserSignUp(credentials) {
    let servlet = "RegistrationOfUser";
    fetch(baseURL, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
        body: credentials
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Failed to fetch data");
            }
            return response.json();
        })
        .then((id) => {
            console.log(id);
            if (id === -1) {
                alert("ERROR: Invalid username.");
            } else if (id === -2) {
                alert("ERROR: Invalid password.");
            } else {
                Redirect(id);
            }
        })
        .catch(function (error) {
            console.log("request failed", error);
        });
}

function Redirect(id) {
    localStorage.setItem("userid", id);
    window.location.href = "search.html";
}
