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
import java.util.Date;

@WebServlet("/Review")
public class ReviewServlet extends HttpServlet {
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

            PrintWriter out = response.getWriter();

            //TODO verify parameter names
            //TODO change if the frontend only sends username and movie name. In that case, we need to query the database to get the user ID and movie ID
            String userID = request.getParameter("userID");
            String movieID = request.getParameter("movieID");
            String content = request.getParameter("content");
            //TODO verify that this is accepted to insert a date into SQL
            Date date = new Date();

            rs = st.executeQuery("SELECT * FROM Users WHERE UserID = " + userID + ";");

            if(rs.next()){
                //TODO flesh this out so that it signifies to the frontend that this is an unregistered user.
                //TODO also verify that the logic is correct. i.e. if we only reach this servlet if the user is already registered
                out.println("Invalid User ID");
            }else {
                st.executeUpdate("INSERT INTO Reviews(UserID, MovieID, Content) VALUES (" + userID + ", " + movieID + ", '" + content + "', " + date + ");");
                out.println("Review Submitted");
            }

            out.flush();
            out.close();

        }
        catch(SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }
}