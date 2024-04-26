package CSCI201_FinalProject;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.*;
import java.sql.ResultSet;

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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/finalproject?user=root&password=root");
			st = conn.createStatement();
			st2 = conn.createStatement();
			
			String userName = request.getParameter("username");
			System.out.println("username: " + userName);
			String passWord = request.getParameter("password");
			System.out.println("password: " + passWord);
			String email = request.getParameter("email");
			System.out.println("email: " + email);
			String displayName = request.getParameter("name");
			System.out.println("name: " + displayName);
			
			rs = st.executeQuery("SELECT * FROM Users WHERE Username = '" + userName + "'");
			rs2 = st2.executeQuery("SELECT * FROM Users WHERE email = '" + email + "'");
			PrintWriter out = response.getWriter();
			
			// This email is already registered
			if(rs2.next()) {
				out.println(-1);
			}
			// This username is already registered
			else if (rs.next()) {
				out.println(-2);
			}
			// successful registration
			else {
				st.executeUpdate("INSERT INTO Users(Username, Password, Email, DisplayName) VALUES ('" + userName + "', '" + passWord + "', '" + email + "','" + displayName + "');");
				
				st3 = conn.createStatement();
				rs3 = st3.executeQuery("SELECT * FROM Users WHERE Username = '" + userName + "'");
				rs3.next();

				int userID = rs3.getInt("user_id");
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
				if (rs3 != null) {
					rs3.close();
				}
			} 
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}	
	}
}