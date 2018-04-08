package application;

import java.util.Scanner;

import authenticationServer.AuthenticationToken;
import system.system;

public class application {
	private static AuthenticationToken token;
	private static system server;
	public static void main(String[] args) {
			server = new system();
			Scanner scanner = new Scanner( System.in );
			try {
				do {
			System.out.println("Please login.\n ID: ");
			String ID = scanner.nextLine();
			System.out.println("Password:");
			String password = scanner.nextLine();
			token = server.loginAttempt(ID, password);
			if (token == null) {
				System.out.println("Invalid ID or password. Please try again.");
			}
				} while (token == null);
	   } finally {
		   scanner.close();
	    }
		if (token.getUserType().equals("admin")) {
			adminPanel();
		}
	}
	public static void adminPanel() {
		System.out.println("Welcome, ");
	}

}
