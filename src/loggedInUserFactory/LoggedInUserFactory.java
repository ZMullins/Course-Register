package loggedInUserFactory;

import authenticatedUsers.LoggedInAdmin;
import authenticatedUsers.LoggedInAuthenticatedUser;
import authenticatedUsers.LoggedInInstructor;
import authenticatedUsers.LoggedInStudent;
import authenticationServer.AuthenticationToken;

public class LoggedInUserFactory {

	public LoggedInUserFactory(){
		
	}
	
	public LoggedInAuthenticatedUser createAuthenticatedUser(AuthenticationToken authenticationToken){
		switch(authenticationToken.getUserType()){
		case "Admin":
			return createLoggedInAdmin(authenticationToken);
		case "Student":
			return createLoggedInStudent(authenticationToken);
		case "Instructor":
			return createLoggedInInstructor(authenticationToken);
		default:
			return null;
		}
	}
	
	public LoggedInStudent createLoggedInStudent(AuthenticationToken authenticationToken){
//		TODO add object creation logic
		return new LoggedInStudent();
	}
	
	public LoggedInAdmin createLoggedInAdmin(AuthenticationToken authenticationToken){
//		TODO add object creation logic
		return new LoggedInAdmin();
	}
	
	public LoggedInInstructor createLoggedInInstructor(AuthenticationToken authenticationToken){
//		TODO add object creation logic 
		return new LoggedInInstructor();
	}
}
