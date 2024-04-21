package CSCI201_FinalProject;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread {
	private PrintWriter pw;
	private BufferedReader br;
	private Server server;
	private Socket socket;
	private UserG user_g;
	private UserA user_auth;
	
	public void authenticate() {
		if(true) {  ///add conditions for authentication
			//this.user_auth = user_g; ==> something like that to update thread that 
			                             //the user has an account
		}
	}
	
	public void sendMessage(String message) {
		//if we use the print writer then return type can stay void;
		//otherwise, change to String
	}
	
}
