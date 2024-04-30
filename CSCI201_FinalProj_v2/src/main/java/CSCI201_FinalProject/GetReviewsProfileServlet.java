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

@WebServlet("/GetReviewsForProfile")
public class GetReviewsProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/finalproject?user=root&password=root");
			st = conn.createStatement();
			st2 = conn.createStatement();
			
			int id = Integer.parseInt(request.getParameter("userID"));
			rs = st.executeQuery("SELECT * FROM Reviews WHERE UserID = '" + id + "'");
			List<UserReviewDatum> l1 = new ArrayList<UserReviewDatum>();
			
			while(rs.next()) {
				
				UserReviewDatum r1 = new UserReviewDatum();
				
				rs2 = st2.executeQuery("SELECT * FROM Movies WHERE MovieID = " + rs.getInt("MovieID"));
				rs2.next();
				
				r1.setMovieName(rs2.getString("Title"));
				r1.setReviewContent(rs.getString("Content"));
				r1.setDate(rs.getDate("Date"));
				l1.add(r1);	
			}
			
			PrintWriter out = response.getWriter();

			UserReviewsCollection revColl = new UserReviewsCollection();
			revColl.setData(l1);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String optr = gson.toJson(revColl);
			out.println(optr);
			
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
				if(st2 != null) {
					st2.close();
				}
				if (rs != null) {
					rs.close();
				}
				if(rs2 != null) {
					rs2.close();
				}
			} 
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}	
	}
}