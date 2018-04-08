package authenticationServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class authenticationServer {
HashMap<String,loginDetails> users;
authenticationServer() throws IOException {
	loginDetailsFactory factory = new loginDetailsFactory();
	BufferedReader br = new BufferedReader(new FileReader(new File("users.txt")));
//	Use the factory to populate as many instances of courses as many files we've got.
	users=factory.getLoginDetails(br);
	br.close();
}

public AuthenticationToken loginAttempt(String ID, String password) {
	return users.get(ID).loginAttempt(password);
}
}
