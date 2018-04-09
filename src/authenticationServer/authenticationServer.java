//AuthenticationServer handles login requests
package authenticationServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class authenticationServer {
	//Stores a map of all users IDs and login details
HashMap<String,loginDetails> users;
public authenticationServer() throws IOException {
	loginDetailsFactory factory = new loginDetailsFactory();
	BufferedReader br = new BufferedReader(new FileReader(new File("users.txt")));
//Use the factory to gather login details
	users=factory.getLoginDetails(br);
	br.close();
}

//Login attempt, return token upon success or null upon failure
public AuthenticationToken loginAttempt(String ID, String password) {
	try {
		AuthenticationToken token = users.get(ID).loginAttempt(ID,password);
		return token;
	}
	catch (NullPointerException e) {
		return null;
	}
}
}
