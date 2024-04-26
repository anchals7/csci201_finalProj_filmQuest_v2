package CSCI201_FinalProject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
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
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/csci201_final_proj?user=root&password=root");
			st = conn.createStatement();			
			
			String movieTitle = request.getParameter("movieTitle");
			rs = st.executeQuery("SELECT * FROM Movies WHERE Title LIKE '%" + movieTitle + "%'" );
			//printResultSet(rs);
//			rs.next();
//			String mt = String.format("%-20s", rs.getString(2));
//			printWriter.print(mt);
//			printWriter.flush();
//			String output1 = stringWriter.toString();
//			System.out.println(output1);
			boolean flag = false;
			List<Movie> l1 = new ArrayList<Movie>();
			
			while (rs.next()) {
				
				flag = true;
				Movie m1 = new Movie();
				
				m1.setMovieID((rs.getInt("MovieID")));
				m1.setTitle(rs.getString("Title"));
				m1.setSynopsis(rs.getString("Synopsis"));
				m1.setRating(rs.getFloat("Rating"));
				m1.setGenre(rs.getString("Genre"));
				
				l1.add(m1);
			}
			PrintWriter out = response.getWriter();
			
			if(!flag) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				String error = "INVALID MOVIE";
				out.println(gson.toJson(error));	
			}
			else {
				response.setStatus(HttpServletResponse.SC_OK);
				MovieCollection movColl = new MovieCollection();
				movColl.setData(l1);
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
	
	public static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Print column headers
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("%-20s", metaData.getColumnName(i));
        }
        System.out.println();

        // Print rows
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", resultSet.getString(i));
            }
            System.out.println();
        }
    }
}