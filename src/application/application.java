package application;

import java.io.IOException;
import java.util.Scanner;

import authenticationServer.AuthenticationToken;
import registrar.ModelRegister;
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
		while (true) {
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
		else if (!server.getState()) {
			System.out.println("The System is stopped. You are unable to login until the Administrator starts it.");
			start();
		}
		else {
			if (token.getUserType().equals("student"))
					studentPanel();
			else if (token.getUserType().equals("instructor"))
					instructorPanel();
		}
		System.out.println("You are now logged out.");
		 token = null; 
		}
	}
	
	private static void studentPanel() {
		System.out.print("Welcome, " + token.getTokenID() + " (student)");
		
		
		System.out.println("Some commands: \"enroll\" enroll in a course. \"notification\" adjust notification settings. \"record\" print a record for a course you are attending");
		String cmd = "";
do {
		 do {
			 System.out.print("Enter a cmd: ");
				cmd = scanner.next();
		}while (!cmd.equals("enroll")&&!cmd.equals("notification")&&!cmd.equals("record"))	;
		 if (cmd.equals("enroll")) {}
		 else if (cmd.equals("notification")) {}
		 
		 else if (cmd.equals("record")) {
			 System.out.print("What is the filename (including extension)?: ");
			 cmd = scanner.next();
			 try {
				server.readCourseFile(cmd);
			
			} catch (IOException e) {
				System.out.println("Error reading file. Ensure file name is spelt correctly.");
			}
	}} while (!cmd.equals("logout"));
	}
		
	
	
	private static void instructorPanel() {
	
System.out.print("Welcome, " + token.getTokenID() + " (student)");
		
		
		System.out.println("Some commands: \"add\" Add mark for a student in one of your courses. \"modify\" Adjust a student's mark. \"calculate\" Calculate a student's mark. \"print\" Print a class record for one of your courses.");
		String cmd = "";
do {
		 do {
			 System.out.print("Enter a cmd: ");
				cmd = scanner.next();
		}while (!cmd.equals("add")&&!cmd.equals("modify")&&!cmd.equals("calculate")&&!cmd.equals("print"))	;
		 if (cmd.equals("add")) {
			addMark(token);
		 }
		 else if (cmd.equals("modify")) {}
		 else if (cmd.equals("calculate")) {}
		 else if (cmd.equals("print")) {
			 System.out.print("What is the filename (including extension)?: ");
			 cmd = scanner.next();
			 try {
				server.readCourseFile(cmd);
			
			} catch (IOException e) {
				System.out.println("Error reading file. Ensure file name is spelt correctly.");
			}
	}} while (!cmd.equals("logout"));
	}
	public class InstructorOperations {
		public void addMark(AuthenticationToken token) {
			
			System.out.print("Which of the following courses is this for?: ");
			System.out.print("");
			ModelRegister.getInstance().getRegisteredUser(token.getTokenID()).get
			String ID = scanner.nextLine();
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
			 System.out.print("Enter a cmd: ");
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
	}
}

