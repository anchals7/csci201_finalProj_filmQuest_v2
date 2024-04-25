
/*function handleFormSubmit(event) {
    event.preventDefault();

    // Get form data
    const formData = new FormData(document.getElementById('myForm'));
    const movieName = document.getElementById('myForm');
    const movieName2 = document.getElementById('myForm').value;
    
    console.log(formData);
    console.log(movieName);
    console.log(movieName2);

    // Send AJAX request
    fetch('/MovieSearchServlet', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to submit form');
        }
        return response.text(); // Return response text
    })
    .then(data => {
        // Update specific portion of the page with response data
        document.getElementById('response').innerText = data;
    })
    .catch(error => {
        console.error('Error:', error);
    });
}*/

// Attach event listener to the form
//document.getElementById('myForm').addEventListener('submit', handleFormSubmit);


/*

  function handleSearchSubmit(event) {
    event.preventDefault();
    
    const movieName = document.getElementsByClassName('.search-input').value;
    let parameters = new URLSearchParams();
    parameters.append("Title", movieName);
   
    const url = '/MovieSearchServlet?' + parameters.toString();
    
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
			displayMovieTitles(data.data);
		}
		
	})
	.catch( (error) =>{
            console.log("request failed", error);
            alert("Fetching Movie Search Failed");
        });
    
    console.log('Search form submitted');
  }*/
  
  function loadData() {
			//var formData = new FormData(document.getElementById("myform"));
			var xhttp = new XMLHttpRequest();
			xhttp.open("POST", "MovieSearchServlet", true);
			xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xhttp.onload = function () {
				document.getElementById("response").innerHTML = this.responseText;
				console.log(this.responseText);
			}
			xhttp.send("field=title&title=" + document.myForm.title.value);
		}
  
  /*function handleSearchSubmit(event) {
    event.preventDefault();
    
    const movieName = document.querySelector('.search-input').value;
    console.log(movieName);
    const url = '/MovieSearchServlet?title=' + encodeURIComponent(movieName);
    
    fetch(url)
    .then(response => {
        if (!response.ok) {
            throw new Error("Couldn't fetch search movies");
        }
        return response.text(); // Assuming the response is text/html
    })
    .then(html => {
        const resultGrid = document.querySelector('.resultsGrid');
        resultGrid.innerHTML = html; // Update the DOM with search results
    })
    .catch(error => {
        console.error("Request failed:", error);
        alert("Fetching Movie Search Failed");
    });
}*/
  
  
  /*function displayMovieTitles(movies){
		for(let i = 0; i < Math.min(movies.length, 3); i++){
			let movie = movies[i];
			let newCard = document.getElementById('titleColTemplate').cloneNode(true);
			newCard.style.display = 'flex';
			newCard.id = '';
			newCard.querySelector('.resultTitle').textContent=movie.title;
			resultsGrid.appendChild(newCard);
		}
	
  }
  */
  
  

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

