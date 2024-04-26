async function UserSignIn(){
	
	if(!document.getElementById("Login")){return;}
	
	
	let baseURL = window.location.origin + "/login.html/";
	
	var url = new URL("/CSCI201_FinalProj_v2/SignInOfUser", baseURL);
	var params = {
		username: document.Login.Username.value, field: "username",
		password: document.Login.Password.value, field: "password",
	}
 	url.search = new URLSearchParams(params).toString();
	fetch(url)
	.then(data => data.text())
	.then((text) => {
		
		if(text.substring(0, text.length - 2) == "-1"){
			alert("Invalid Sign in");
			document.getElementById("Login").reset();
		}
		else {
			localStorage.setItem("userid", text.substring(0, text.length - 2));
			console.log(text);
			alert("successful login!");
			document.getElementById("Login").reset();
		}
	}).catch(function (error) {
	console.log('request failed', error)
	});
}
