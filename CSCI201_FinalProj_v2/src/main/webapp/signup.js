
async function UserRegistration(){
	
	if(!document.getElementById("Signup")){return;}
	
	
	let baseURL = window.location.origin + "/signup.html/";
	
	var url = new URL("/CSCI201_FinalProj_v2/RegistrationOfUser", baseURL);
	var params = {
		username: document.Signup.Username.value, field: "username",
		email: document.Signup.Email.value, field: "email",
		password: document.Signup.Password.value, field: "password",
		name: document.Signup.Name.value, field: "name"
	}
 	url.search = new URLSearchParams(params).toString();
	fetch(url)
	.then(data => data.text())
	.then((text) => {
		
		if(text.substring(0, text.length - 2) == "-1" || text.substring(0, text.length - 2) == "-2"){
			alert("Invalid Sign Up");
			document.getElementById("Signup").reset();
		}
		else {
			localStorage.setItem("userid", text.substring(0, text.length - 2));
			window.location.href = "search.html";
		}
	}).catch(function (error) {
	console.log('request failed', error)
	});
}
