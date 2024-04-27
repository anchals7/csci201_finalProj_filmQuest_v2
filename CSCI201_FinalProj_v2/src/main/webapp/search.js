function handleSearchSubmit(event) {
    event.preventDefault();
    
    let resultsContainer = document.getElementById('resultsContainer');
    resultsContainer.style.display = "flex";
    let spacer = document.getElementById('spacer');
    spacer.style.display = "none";
    resultsContainer.innerHTML = `<h2 class="searchResults">Search Results</h2>
								    <div class="resultsGrid">
								      <div class="resultsRow">
								       <div class="resultCol" id="titleColTemplate">
								          <button class="resultItem" >
								            <div class="resultTitle">
								              Title
								            </div>
								          </button>
								        </div>
								      </div>
								    </div>`;
    
    const movieName = document.getElementsByClassName('search-input')[0].value;
    let parameters = new URLSearchParams();
    parameters.append("movieTitle", movieName);
    const url = '/CSCI201_FinalProj_v2/GetMovie?' + parameters.toString();
    
    
    
    fetch(url, {
		method: 'GET',
		headers:{
			'Content-Type': 'application/json'
		}
	})
	.then((response) => {
        if (!response.ok) {
            const resultGrid = document.querySelector('.resultsGrid');
            resultGrid.innerHTML = '<h2 class="noResult" style="font-size:2em; color:white;">No Results Found</h2>';
        }
        return response.json();
    })
    .then(data =>{
		let movieList = data.data;
		const resultGrid = document.querySelector('.resultsGrid');
		const resultRow = document.querySelector('.resultsRow');
		if (movieList.length === 0){
			resultGrid.innerHTML = '<h2 class="noResult">No Results Found</h2>';
		}
		else{
			displayMovieTitles(movieList, resultRow);
		}
		
	})
	.catch( (error) =>{
            console.log("request failed", error);
        });
    
    console.log('Search form submitted');
  }
  
  
 function displayMovieTitles(movies, resultRow){
		for(let i = 0; i < Math.min(movies.length, 3); i++){
			let movie = movies[i];
			console.log(document.getElementById('titleColTemplate'));  // This should not log `null` if the element exists and is loaded
			let newCard = document.getElementById('titleColTemplate').cloneNode(true);
			newCard.style.display = 'flex';
			newCard.id = '';
			
			newCard.querySelector('.resultTitle').textContent=movie.title;
			resultRow.appendChild(newCard);
			
			newCard.querySelector('.resultItem').addEventListener('click', () => {
            displayAdditionalInfo(movie);
        });
		}
	
  }
  

  function displayAdditionalInfo(movie) {
    let additionalInfoSection = document.getElementById('additionalInfo');
    if (additionalInfoSection.style.display === 'block' && additionalInfoSection.dataset.title === movie.title) {
        additionalInfoSection.style.display = 'none';
        additionalInfoSection.dataset.title = ''; // Clear the dataset attribute
    } else {
		additionalInfoSection.innerHTML = `<h2>${movie.title}</h2>
                                       <p>Synopsis: ${movie.synopsis}</p>
                                       <p>Rating: ${movie.rating}</p>
                                       <p>Genre: ${movie.genre}</p>`;
	    additionalInfoSection.style.display = 'block';
	    additionalInfoSection.dataset.title = movie.title;		
	}
 
}
  


function doNothing(){ alert("doing nothing");}  


async function moveToPortfolio(){
	
	if(!document.getElementById("mover")){return;}
	
	if (!localStorage.getItem("userid")){
		alert("not signed in");
		return;
	}
	else {
		
		
		let baseURL = window.location.origin + "/login.html/";
	
		var url = new URL("/CSCI201_FinalProj_v2/GetUserInfo", baseURL);
		var params = {
			userID: localStorage.getItem("userid"), field: "userID",
		}
		
		url.search = new URLSearchParams(params).toString();
		
		const response = await fetch(url);
		console.log(response);
		const jsonObj = await response.json();
		console.log(jsonObj);
		
		const optr = 
        '<div id="profile-user">' +
            '<img src="images/profile_img_placeholder.png" alt="Profile-Img-Placeholder">' +
            '<div id="profile-user-info">' +
                '<h2>' + jsonObj.data[0].name + '</h2>' +
                '<p>' + jsonObj.data[0].username +'</p>' +
            '</div>' +
        '</div>' +
	'  <div id="profile-menu">' +
        '<div id="active" class="profile-menu-item" style = "font-size: 25px; font-family: Arial; border-radius: 7px; background-color: #B39BC7;">' +
            'My Profile' +
        '</div>' +
    '</div>';
        
        document.getElementById("mover").innerHTML = optr;
	}
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