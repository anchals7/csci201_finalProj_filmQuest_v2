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

@WebServlet("/GetReviewsOfUser")
public class GetReviewsOfUserServlet extends HttpServlet {
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
			
			int id = Integer.parseInt(request.getParameter("INSERT USER ID PARAMETER"));
			rs = st.executeQuery("SELECT * FROM Reviews WHERE UserID = '" + id + "'");
			List<Review> l1 = new ArrayList<Review>();
			boolean flag = false;
			
			while(rs.next()) {
				
				flag = true;
				Review r1 = new Review();
				
				r1.setUserID(rs.getInt("ReviewID"));
				r1.setUserID(rs.getInt("UserID"));
				r1.setMovieID(rs.getInt("MovieID"));
				r1.setContent(rs.getString("Content"));
				r1.setDate(rs.getDate("Date"));
				l1.add(r1);	
			}
			
			PrintWriter out = response.getWriter();
			
			if(!flag) {
				
				out.println("INVALID USER");
			}
			else {
				
				ReviewCollection revColl = new ReviewCollection();
				revColl.setData(l1);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String optr = gson.toJson(revColl);
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