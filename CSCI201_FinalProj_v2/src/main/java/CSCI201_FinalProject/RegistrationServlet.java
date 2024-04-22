package CSCI201_FinalProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegistrationOfUser")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		
		Connection conn = null;
		Statement st = null;
		Statement st2 = null;
		Statement st3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/INSERT_NAME_OF_SCHEMA?user=USERNAME&password=PASSWORD");
			st = conn.createStatement();
			st2 = conn.createStatement();
			
			String userName = request.getParameter("username");
			String passWord = request.getParameter("password");
			String email = request.getParameter("email");
			String displayName = request.getParameter("name");
			
			rs = st.executeQuery("SELECT * FROM Users WHERE Username = '" + userName + "'");
			rs2 = st2.executeQuery("SELECT * FROM Users WHERE email = '" + email + "'");
			PrintWriter out = response.getWriter();
			
			if(rs2.next()) {
				out.println("This email is taken already");
			}
			else if (rs.next()) {
				out.println("This username is taken already");
			}
			else {
				st.executeUpdate("INSERT INTO Users(Username, Password, Email, DisplayName) VALUES ('" + userName + "', '" + passWord + "', '" + email + "'," + displayName + ");");
				
				st3 = conn.createStatement();
				rs3 = st3.executeQuery("SELECT * FROM Users WHERE Username = '" + userName + "'");
				rs3.next();

				int userID = rs.getInt("user_id");
				out.println(userID);
			}
			
			out.flush();
			out.close();
			//basic registration servlet
			
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if(rs2 != null) {
					rs2.close();
				}
				if (st != null) {
					st.close();
				}
				if(st2 != null) {
					st2.close();
				}
				if (conn != null) {
					conn.close();
				}
			} 
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}	
	}
}