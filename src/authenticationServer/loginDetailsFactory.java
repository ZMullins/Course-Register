package authenticationServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
public class loginDetailsFactory {
	public HashMap<String, loginDetails> getLoginDetails(BufferedReader br) {
		// TODO Auto-generated method stub
		HashMap<String, loginDetails> logins = new HashMap<String, loginDetails>();
		try{
		String line = null;
		while ((line = br.readLine()) != null) {
		loginDetails login = new loginDetails();
			String iD = line.split("\t")[0];
			login.setPassword(line.split("\t")[1]);
			login.setUserType(line.split("\t")[2]);
			logins.put(iD, login);
		}
		return logins;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage() + "exception thrown at CourseOfferingCreation"); 
			return null;
		}
	}
}
