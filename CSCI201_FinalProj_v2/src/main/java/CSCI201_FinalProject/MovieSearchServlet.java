package CSCI201_FinalProject;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/search")
public class MovieSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private static final String JDBC_URL = "jdbc:mysql://localhost/csci201_final_proj";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extract the search query parameter (movie title) from the request URL
        String title = request.getParameter("title");

        // Set the content type of the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Establish a connection to the MySQL database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Prepare a SQL statement to search for movies based on title
            String sql = "SELECT * FROM movies WHERE title LIKE ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set the search query parameter
                statement.setString(1, "%" + title + "%");

                // Execute the query
                ResultSet resultSet = statement.executeQuery();

                // Generate HTML response with search results
                out.println("<html><body>");
                out.println("<h2>Search Results:</h2>");
                while (resultSet.next()) {
                    String movieTitle = resultSet.getString("title");
                    out.println("<p>" + movieTitle + "</p>");
                }
                out.println("</body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error occurred while processing the search request.</p>");
        }
    }
}