package CSCI201_FinalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.*;
import java.sql.ResultSet;

@WebServlet("/UpdateRating")
public class UpdateRatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/finalproject?user=root&password=root");
			st = conn.createStatement();
			st2 = conn.createStatement();
			
			int movieID = Integer.parseInt(request.getParameter("movieID"));
			int numReviews = Integer.parseInt(request.getParameter("numReviews"));
			float addRating = Float.parseFloat(request.getParameter("newRating"));
			
			rs = st.executeQuery("SELECT * FROM Movies WHERE MovieID = " + movieID);
			rs.next();
			
			float movieRating = rs.getFloat("Rating");
			movieRating *= numReviews;
			movieRating += addRating;
			movieRating = movieRating / (numReviews + 1);
			st2.executeUpdate("UPDATE Movies SET Rating = " + movieRating + " WHERE MovieID = " + movieID + " ;");
			
			PrintWriter out = response.getWriter();
			out.println("success");
			out.flush();
			out.close();
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (st != null) {
					st.close();
				}
				if (st2 != null) {
					st2.close();
				}
				if (rs != null) {
					rs.close();
				}
			} 
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}	
	}
}