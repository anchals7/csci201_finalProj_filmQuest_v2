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

@WebServlet("/GetUserInfo")
public class GetUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/finalproject?user=root&password=root");
			st = conn.createStatement();			
			
			int id = Integer.parseInt(request.getParameter("userID"));
			System.out.println(id);
			rs = st.executeQuery("SELECT * FROM Users WHERE UserID = '" + id + "'");
			if(rs.next()) {
				System.out.println(rs.getString("DisplayName"));
				UserDatum ud1 = new UserDatum();
				ud1.setName(rs.getString("DisplayName"));
				ud1.setUsername(rs.getString("UserName"));
				ud1.setUserID(rs.getInt("UserID"));
				UserCollection uc1 = new UserCollection();
				List<UserDatum> l1 = new ArrayList<UserDatum>();
				l1.add(ud1);
				uc1.setL1(l1);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String optr = gson.toJson(uc1);
				System.out.println(optr);
				PrintWriter out = response.getWriter();
				out.println(optr);
				out.flush();
				out.close();
			}
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