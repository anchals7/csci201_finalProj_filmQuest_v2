package CSCI201_FinalProject;

import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MovieSearchServlet")
public class MovieSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:mysql://localhost/csci201_final_proj?user=root&password=root";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Extract the search query parameter (movie title) from the request URL
        //String title = request.getParameter("title");
    	
        String name = request.getParameter("title");
        
        
    	//String title = "Wonka";
        // Set the content type of the response
        //response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        String formattedString = String.format("%-20s", "Hello, World!");
        printWriter.print(formattedString);
        printWriter.flush();
        String output = stringWriter.toString();
        System.out.println("Captured output: " + output);
//        if(name != null) {
//			out.println(name);
//		}
//        response.setContentType("text/plain");
//        PrintWriter out = response.getWriter();
//        out.println("This is a test message.");
        
        //out.flush();
        // Establish a connection to the MySQL database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/csci201_final_proj?user=root&password=root")) {
            // Prepare a SQL statement to search for movies based on title
            String sql = "SELECT * FROM movies WHERE Title LIKE ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set the search query parameter
                statement.setString(1, "%" + name + "%");
                //out.println("<p>1. Error occurred while processing the search request.</p>");
                // Execute the query
                ResultSet resultSet = statement.executeQuery();
                //System.out.printf("%-20s", resultSet.getString(2));
                //printResultSet(resultSet);
                //out.println("<p>2. Error occurred while processing the search request.</p>");
                // Generate HTML response with search results
                //out.println("<html><body>");
                //out.println("<h2>Search Results:</h2>");
                //String movieTitle = resultSet.getString("Title");
                //out.println(movieTitle);
                //System.out.println("movieTitle");
                
                while (resultSet.next()) {
                	//System.out.printf("%-20s", resultSet.getString(2));
                    String movieTitle = String.format("%-20s", resultSet.getString(2));
                    String movieSynopsis = String.format("%-20s", resultSet.getString(3));
                    String movieRating = String.format("%-20s", resultSet.getString(4));
                    String movieGenre = String.format("%-20s", resultSet.getString(5));
                    out.println("<p style=\"color:white; margin-left: 9px;\">Title: "+movieTitle+"</p>");
                    out.println("<p style=\"color:white; margin-left: 9px;\">Synopsis: "+movieSynopsis+"</p>");
                    out.println("<p style=\"color:white; margin-left: 9px;\">Rating: "+movieRating+" out of 5</p>");
                    out.println("<p style=\"color:white; margin-left: 9px;\">Genre: "+movieGenre+"</p>");
                    System.out.println(movieRating);
                }
                out.flush();
                //out.println("</body></html>");
//                StringBuilder htmlBuilder = new StringBuilder();
//                htmlBuilder.append("<html><body>");
//                htmlBuilder.append("<h2>Search Results:</h2>");
//                while (resultSet.next()) {
//                    String movieTitle = resultSet.getString("Title");
//                    htmlBuilder.append("<p>").append(movieTitle).append("</p>");
//               
//                }
//                htmlBuilder.append("</body></html>");
//
//                // Write the HTML response
//                out.println(htmlBuilder.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error occurred while processing the search request.</p>");
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