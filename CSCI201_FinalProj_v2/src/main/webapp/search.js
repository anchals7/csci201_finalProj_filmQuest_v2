

  function handleSearchSubmit(event) {
    event.preventDefault();
    
    const movieName = document.getElementsByClassName('search-input');
    const url = '/GetMovie'
    
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
		
	})
	.catch( (error) =>{
            console.log("request failed", error);
        });
    
    console.log('Search form submitted');
  }

  function handleResultClick(event) {
    // Handle result item click
    console.log('Result item clicked');
  }

  function handleViewMoreClick() {
    // Handle view more button click
    console.log('View more button clicked');
  }