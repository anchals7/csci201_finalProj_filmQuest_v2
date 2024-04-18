package CSCI201_FinalProject;

import java.util.HashMap;

public class Movie {
	
	private int MovieID;
	
	private String title;
	
	private double rating;
	
	private int views;
	
	private String summary;
	
	HashMap<Integer, String> Reviews;
	
	public Movie(String t, double r, String s, int v) {
		this.title = t;
		this.rating = r;
		this.summary = s;
		this.views = v;
		this.Reviews = new HashMap<Integer, String>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
	
	public void AddReview(Integer userID, String review) {
		Reviews.put(userID, review);
	}
	
	public void Rate(int r) {
		views++;
		rating = ((rating * views) + r) / views;
	}

	public int getMovieID() {
		return MovieID;
	}

	public void setMovieID(int movieID) {
		MovieID = movieID;
	}	
	
}
