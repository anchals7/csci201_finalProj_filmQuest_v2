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

@WebServlet("/SignInOfUser")
public class SignInServlet extends HttpServlet {
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
			
			String userName = request.getParameter("username");
			String passWord = request.getParameter("password");
			
			rs = st.executeQuery("SELECT * FROM Users WHERE Username = '" + userName + "' AND Password = '" + passWord + "';");
			PrintWriter out = response.getWriter();
			
			if(!rs.next()) {
//				out.println("Invalid username or password");
				out.println(-1);
			}
			else {
				
				st2 = conn.createStatement();
				rs2 = st2.executeQuery("SELECT * FROM Users WHERE Username = '" + userName + "'");
				rs2.next();

				int userID = rs2.getInt("UserID");
				out.println(userID);
			}
			
			out.flush();
			out.close();
			
			/* NOTE - This is a simple validation sign in which does not return any information. If Front-end requires more data,
			 this must be changed so that additional sql queries retrieve the information, then parse it to JSON, then send it 
			 back to Front-End javascript. For now, however, it is unspecified what information (if any) this servlet must retrieve.
			 */
			
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