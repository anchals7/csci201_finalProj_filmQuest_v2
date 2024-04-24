package CSCI201_FinalProject;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.ResultSet;

@WebServlet("/GetMovie")
public class GetMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/INSERT_NAME_OF_SCHEMA?user=USERNAME&password=PASSWORD");
			st = conn.createStatement();			
			
			String movieTitle = request.getParameter("INSERT_MOVIE_TITLE_PARAM_NAME");
			rs = st.executeQuery("SELECT * FROM Movies WHERE Title = '" + movieTitle + "'" );
			boolean flag = false;
			List<Movie> l1 = new ArrayList<Movie>();
			
			while (rs.next()) {
				
				flag = true;
				Movie m1 = new Movie();
				
				m1.setMovieID(rs.getInt("MovieID"));
				m1.setTitle(rs.getString("Title"));
				m1.setSynopsis(rs.getString("Synopsis"));
				m1.setRating(rs.getFloat("Rating"));
				m1.setGenre(rs.getString("Genre"));
				
				l1.add(m1);
			}
			PrintWriter out = response.getWriter();
			
			if(!flag) {
				
				out.println("INVALID MOVIE");	
			}
			else {
				MovieCollection movColl = new MovieCollection();
				movColl.setData(l1);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String optr = gson.toJson(movColl);
				
				out.println(optr);
			}
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