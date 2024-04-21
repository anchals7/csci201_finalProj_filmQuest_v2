package CSCI201_FinalProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserA{
	
	private int UserID;
	
	private String email;
	
	private String password;
	
	HashMap<Integer, String> Reviews;
	
	List<Movie> MoviesWatched;
	
	
	public UserA(int uID, String e, String p) {
		this.UserID = uID;
		this.email = e;
		this.password = p;
		
		Reviews = new HashMap<Integer, String>();
		MoviesWatched = new ArrayList<Movie>();
	}
	

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void WriteReview(int mID, String r) {
		Reviews.put(mID, r);
	}
	
	public void WatchMovie(Movie m) {
		MoviesWatched.add(m);
	}
	
}


