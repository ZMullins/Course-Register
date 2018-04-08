package application;

import java.io.IOException;
import java.util.Scanner;

import authenticationServer.AuthenticationToken;
import system.system;

public class application {
	private static AuthenticationToken token;
	private static system server;
	private static Scanner scanner = new Scanner( System.in );
	public static void main(String[] args) {
			server = new system();
		start();
			
	}
	private static void start() {
		do {
			System.out.print("Please login.\nID: ");
			String ID = scanner.nextLine();
			System.out.print("Password: ");
			String password = scanner.nextLine();
			token = server.loginAttempt(ID, password);
			if (token == null) {
				System.out.println("Invalid ID or password. Please try again.");
			}
				} while (token == null);
	   
		if (token.getUserType().equals("admin")) {
			adminPanel();
		}
	}
	private static void adminPanel() {
		System.out.print("Welcome, " + token.getTokenID() + " (admin) the system is currently ");
		if (server.getState()) {
			System.out.println("running. ");}
		else {
			System.out.println("stopped. ");
		}
		System.out.println("Some commands: \"start\" to start the system. \"stop\" to stop the system. \"load\" to load a course file. \"status\" to see state of system. \"logout\" to logout of this account.");
		String cmd = "";
do {
		 do {
			 System.out.print("Enter cmd: ");
				cmd = scanner.next();
		}while (!cmd.equals("start")&&!cmd.equals("stop")&&!cmd.equals("load")&&!cmd.equals("logout")&&!cmd.equals("status"))	;
		 if (cmd.equals("start")) 
			 server.start(token);
		 else if (cmd.equals("stop"))
			 server.stop(token);
		 else if (cmd.equals("load")) {
			 System.out.print("What is the filename (including extension)?: ");
			 cmd = scanner.next();
			 try {
				server.readCourseFile(cmd);
			
			} catch (IOException e) {
				System.out.println("Error reading file. Ensure file name is spelt correctly.");
			}
		 }
		 else if (cmd.equals("status")) {
			 server.getStatus();
		 }
	} while (!cmd.equals("logout"));
			 token = null;
			 start(); 
	}
}

