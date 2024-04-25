

  function handleSearchSubmit(event) {
    event.preventDefault();
    
    const movieName = document.getElementsByClassName('search-input')[0].value;
    let parameters = new URLSearchParams();
    parameters.append("movieTitle", movieName);
   
    const url = '/GetMovie?' + parameters.toString();
    
    fetch(url, {
		method: 'GET',
		headers:{
			'Content-Type': 'application/json'
		}
	})
	.then((response) => {
        if (!response.ok) {
            throw new Error("Couldn't fetch search movies");
        }
        return response.json();
    })
    .then(data =>{
		let movieList = data.data;
		const resultGrid = document.querySelector('.resultsGrid');
		resultGrid.innerHTML = '';
		if (movieList.length === 0){
			resultGrid.innerHTML = '<h2 class="noResult">No Results Found</h2>';
		}
		else{
			displayMovieTitles(movieList, resultGrid);
		}
		
	})
	.catch( (error) =>{
            console.log("request failed", error);
            alert("Fetching Movie Search Failed");
        });
    
    console.log('Search form submitted');
  }
  
  
  function displayMovieTitles(movies, resultGrid){
		for(let i = 0; i < Math.min(movies.length, 3); i++){
			let movie = movies[i];
			let newCard = document.getElementById('titleColTemplate').cloneNode(true);
			newCard.style.display = 'flex';
			newCard.id = '';
			newCard.querySelector('.resultTitle').textContent=movie.title;
			resultGrid.appendChild(newCard);
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

