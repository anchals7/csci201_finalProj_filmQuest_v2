function handleSearchSubmit(event) {
    event.preventDefault();

    let resultsContainer = document.getElementById("resultsContainer");
    resultsContainer.style.display = "flex";
    let spacer = document.getElementById("spacer");
    spacer.style.display = "none";
    resultsContainer.innerHTML = `<h2 class="searchResults">Search Results</h2>
								    <div class="resultsGrid">
								      <div class="resultsRow" id = "labomba">
								       <div class="resultCol" id="titleColTemplate">
								          <button class="resultItem" >
								            <div class="resultTitle">
								              Title
								            </div>
								          </button>
								        </div>
								      </div>
								    </div>`;

    const movieName = document.getElementsByClassName("search-input")[0].value;
    let parameters = new URLSearchParams();
    parameters.append("movieTitle", movieName);
    const url = "/CSCI201_FinalProj_v2/GetMovie?" + parameters.toString();

    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then((response) => {
            if (!response.ok) {
                const resultGrid = document.querySelector(".resultsGrid");
                resultGrid.innerHTML =
                    '<h2 class="noResult" style="font-size:2em; color:white;">No Results Found</h2>';
            }
            return response.json();
        })
        .then((data) => {
            let movieList = data.data;
            const resultGrid = document.querySelector(".resultsGrid");
            const resultRow = document.querySelector(".resultsRow");
            if (movieList.length === 0) {
                resultGrid.innerHTML =
                    '<h2 class="noResult">No Results Found</h2>';
            } else {
                displayMovieTitles(movieList, resultRow);
            }
        })
        .catch((error) => {
            console.log("request failed", error);
        });

    console.log("Search form submitted");
}

function displayMovieTitles(movies, resultRow) {
    for (let i = 0; i < Math.min(movies.length, 3); i++) {
        let movie = movies[i];
        console.log(document.getElementById("titleColTemplate")); // This should not log `null` if the element exists and is loaded
        let newCard = document
            .getElementById("titleColTemplate")
            .cloneNode(true);
        //newCard.style.display = "flex";
        newCard.id = "";

        newCard.querySelector(".resultTitle").textContent = movie.title;
        resultRow.appendChild(newCard);

        newCard.querySelector(".resultItem").addEventListener("click", () => {
            displayAdditionalInfo(movie);
        });
    }
}

function displayAdditionalInfo(movie) {
    let additionalInfoSection = document.getElementById("resultsContainer");
    additionalInfoSection.style.backgroundColor = '#7e6791';
    additionalInfoSection.style.fontFamily = 'Arial';
    additionalInfoSection.style.color = 'white';

	
    
    if (
        additionalInfoSection.style.display === "block" &&
        //additionalInfoSection.dataset.title === movie.title
        additionalInfoSection.dataset.movieId === movie.movieID
    ) {
        additionalInfoSection.style.display = "none";
        //additionalInfoSection.dataset.title = ""; // Clear the dataset attribute
        additionalInfoSection.dataset.movieId = "";
    } else {
        if(sessionStorage.getItem("userid")){
			console.log(movie.movieID);
			additionalInfoSection.innerHTML = `<h2>${movie.title}</h2>
                                       <p>Synopsis: ${movie.synopsis}</p>
                                       <p>Rating: ${movie.rating}</p>
                                       <p>Genre: ${movie.genre}</p>
                                       <div id="container">
	                                       <form class="reviewForm">
	                                       		<input type="hidden" id="movieID" name="movieID" value="">
	                                       		<label for="ratingNum">Your Rating (Out of 5): </label><br>
						                		<input type="text" class="review-input" id="ratingNum" aria-label="Enter Number Rating out of 5" required/><br>
						                		<label for="userReview">Your Review: </label><br>
						                		<input type="text" class="review-input" id="userReview" placeholder="What do you think of this movie? Share your thoughts!" aria-label="What do you think of this movie? Share your thoughts!" required/><br>
						                   		<input type="submit" value="Submit">
						                   </form>
					                   </div>`;
			document.getElementById("container").style.backgroundColor = '#E6F4F1';
			document.getElementById("container").style.padding = '20px';
			document.getElementById("container").style.color = '#00A8D6';
			document.getElementById("container").querySelector("#ratingNum").style.width = '30px';
			document.getElementById("container").querySelector("#ratingNum").style.padding = '12px';
			document.getElementById("container").querySelector("#ratingNum").style.margin = '7px';
			document.getElementById("container").querySelector("#userReview").style.width = '430px';
			document.getElementById("container").querySelector("#userReview").style.height = '115px';
			document.getElementById("container").querySelector("#userReview").style.padding = '12px';
			document.getElementById("container").querySelector("#userReview").style.margin = '7px';
			const reviewForm = document.querySelector('.reviewForm');
        	reviewForm.addEventListener('submit', submitReview);
		} else {
			additionalInfoSection.innerHTML = `<h2>${movie.title}</h2>
                                       <p>Synopsis: ${movie.synopsis}</p>
                                       <p>Rating: ${movie.rating}</p>
                                       <p>Genre: ${movie.genre}</p>`;
		}
		additionalInfoSection.style.display = "block";
        //additionalInfoSection.dataset.title = movie.title;
        additionalInfoSection.dataset.movieId = movie.movieID.toString();
        console.log("movieID_dataset: "+ additionalInfoSection.dataset.movieId);
    }
}



function submitReview(event) {
    event.preventDefault();
    

    const movieID = document.getElementById('resultsContainer').dataset.movieId;
    const ratingNum = document.getElementById('ratingNum').value;
    const userReview = document.getElementById('userReview').value;
    const userID = sessionStorage.getItem('userid'); // Get the user ID from sessionStorage

	const formData = new FormData();
	formData.append('userID', userID);
	formData.append('movieID', movieID);
	formData.append('userReview', userReview);
	console.log("userID: " + userID + " movieID: " + movieID + " userReview: " + userReview);
	
	
    const url = `/CSCI201_FinalProj_v2/Review?userID=${userID}&movieID=${movieID}&userReview=${userReview}`;
    const params = {
        userID: userID,
        movieID: movieID,
        ratingNum: ratingNum,
        userReview: userReview
    };

    fetch(url, {
        method: 'GET'
    })
    .then(response => response.text())
    .then(data => {
        console.log(data); // Log the response from the server
        window.location.href = "search.html";
        // You can handle the response as needed, e.g., display a success message to the user
    })
    .catch(error => {
        console.error('Error:', error);
        // Handle any errors that occur during the fetch request
    });
}

function doNothing() {
    alert("doing nothing");
}

async function moveToPortfolio() {
    if (!document.getElementById("mover")) {
        return;
    }

    if (!sessionStorage.getItem("userid")) {
        alert("not signed in");
        return;
    } else {
        let baseURL = window.location.origin + "/login.html/";

        var url = new URL("/CSCI201_FinalProj_v2/GetUserInfo", baseURL);
        var params = {
            userID: sessionStorage.getItem("userid"),
            field: "userID"
        };

        url.search = new URLSearchParams(params).toString();

        const response = await fetch(url);
        console.log(response);
        const jsonObj = await response.json();
        console.log(jsonObj);

        var name = jsonObj.data[0].name;
        var username = jsonObj.data[0].username;

        //const optr =
        //    '<div id="profile-user">' +
        //    '<img src="images/profile_img_placeholder.png" alt="Profile-Img-Placeholder">' +
        //    '<div id="profile-user-info">' +
        //    "<h2>" +
        //    jsonObj.data[0].name +
        //    "</h2>" +
        //    "<p>" +
        //    jsonObj.data[0].username +
        //    "</p>" +
        //    "</div>" +
        //    "</div>" +
        //    '  <div id="profile-menu">' +
        //    '<div id="active" class="profile-menu-item">' +
        //    "My Profile" +
        //    "</div>" +
        //    "</div>" +
        //    '<div id="my-reviews">' +
        //    "<h1>My Recently Reviewed</h1>" +
        //    "</div>";

        //const reviews = "";
        ShowReviews(name, username);
    }
}

async function ShowReviews(name, username) {
    if (!sessionStorage.getItem("userid")) {
        alert("not signed in");
        return;
    }
    let baseURL = window.location.origin + "/search.html/";
    var url = new URL("/CSCI201_FinalProj_v2/GetReviewsForProfile", baseURL);
    var params = {
        userID: sessionStorage.getItem("userid"),
        field: "userID"
    };

    url.search = new URLSearchParams(params).toString();

    const response = await fetch(url);
   // const rString = response.toString();
    console.log(response);
    
    /*
    if(rString.substring(0, response.length -2) == "NO REVIEWS"){
		return;
	}
	*/
    
    const jsonObj = await response.json();
    console.log(jsonObj);
    let reviews = `<div id="Reviews">`;

    for (var i = 0; i < jsonObj.data.length; ++i) {
        reviews += `<div id="portfolioReviewsId" class = "ReviewClass">
                    <p class="MovieID">${jsonObj.data[i].movieName}</p>
                    <p class="ReviewText">${jsonObj.data[i].reviewContent}</p>
                    <br />
                    <p class="Date">Date Reviewed: ${jsonObj.data[i].date}</p>
                    <br />
                </div>`;
    }
    reviews += `</div>`;
    console.log(reviews);
    const optr = `<div id="profile-user">
            <img src="images/profile_img_placeholder.png" alt="Profile-Img-Placeholder">
            <div id="profile-user-info">
            <h2>${name}</h2>
            <p>${username}</p>
            </div>
            </div>
            <div id="profile-menu">
            <div id="active" class="profile-menu-item">
            My Profile
            </div>
            </div>
            <div id="my-reviews">
            <h1>My Reviews </h1>
            ${reviews}
            </div>
            </div>`;
    console.log(optr);
    document.getElementById("mover").innerHTML = optr;
}

/*function handleResult(title) {
	 let movie = {
		INSERT_MOVIE_TITLE_PARAM_NAME: title
	};
	 const url = '/GetMovie'
    
    fetch(url, {
		method: 'GET',
		headers:{
			'Content-Type': 'application/json'
		},
		body: movie
	})
	.then((response) => {
        if (!response.ok) {
            throw new Error("Couldn't fetch search movies");
        }
        return response.json();
    })
    .then(data =>{
		let movieList = data.data;
		
	})
	.catch( (error) =>{
            console.log("request failed", error);
        });
    
	
    console.log('Result item clicked');
  }*/
