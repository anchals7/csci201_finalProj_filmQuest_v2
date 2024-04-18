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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/INSERT_NAME_OF_SCHEMA?user=USERNAME&password=PASSWORD");
			st = conn.createStatement();
			st2 = conn.createStatement();
			
			String userName = request.getParameter("INSERT_USERNAME_PARAMETER_RETRIEVED_FROM_FRONT-END_JAVASCRIPT");
			String passWord = request.getParameter("INSERT_PASSWORD_PARAMETER_RETRIEVED_FROM_FRONT-END_JAVASCRIPT");
			
			rs = st.executeQuery("SELECT * FROM Users WHERE Username = '" + userName + "' AND Password = '" + passWord + "';");
			PrintWriter out = response.getWriter();
			
			if(!rs.next()) {
				out.println("Invalid username or password");
			}
			else {
				
				
				out.println("valid sign in");
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