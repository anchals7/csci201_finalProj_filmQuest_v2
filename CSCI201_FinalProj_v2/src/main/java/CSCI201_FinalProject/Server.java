package CSCI201_FinalProject;

import java.net.ServerSocket;
import java.util.List;
import java.sql.*;


public class Server {
	private ServerSocket serverSocket;
	private List<ServerThread> serverThreads;
	private static Connection connection = null;
	
	//add connections for data base
	public static void main(String [] args) {
		//connect with sql database using jdbm
		Statement st = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/csci201_final_proj?user=root&password=root");
			st = connection.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Connection not established");
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Error closing connection");
		}
	}
	
	public void getDatabase() {
		
	}

}
