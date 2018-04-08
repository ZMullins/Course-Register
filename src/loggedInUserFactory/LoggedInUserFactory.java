package loggedInUserFactory;

import authenticatedUsers.LoggedInAdmin;
import authenticatedUsers.LoggedInAuthenticatedUser;
import authenticatedUsers.LoggedInInstructor;
import authenticatedUsers.LoggedInStudent;
import authenticationServer.AuthenticationToken;
import registrar.ModelRegister;
import systemUsers.SystemUserModel;

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
		SystemUserModel getInfo = ModelRegister.getInstance().getRegisteredUser(authenticationToken.getTokenID());
		LoggedInStudent result  = new LoggedInStudent();
		result.setID(getInfo.getID());
		result.setName(getInfo.getName());
		result.setSurname(getInfo.getSurname());
		result.setAuthenticationToken(authenticationToken);
		return result;
	}
	
	public LoggedInAdmin createLoggedInAdmin(AuthenticationToken authenticationToken){
		SystemUserModel getInfo = ModelRegister.getInstance().getRegisteredUser(authenticationToken.getTokenID());
		LoggedInAdmin result  = new LoggedInAdmin();
		result.setID(getInfo.getID());
		result.setName(getInfo.getName());
		result.setSurname(getInfo.getSurname());
		result.setAuthenticationToken(authenticationToken);
		return result;
	}
	
	public LoggedInInstructor createLoggedInInstructor(AuthenticationToken authenticationToken){
		SystemUserModel getInfo = ModelRegister.getInstance().getRegisteredUser(authenticationToken.getTokenID());
		LoggedInInstructor result  = new LoggedInInstructor();
		result.setID(getInfo.getID());
		result.setName(getInfo.getName());
		result.setSurname(getInfo.getSurname());
		result.setAuthenticationToken(authenticationToken);
		return result;
	}
}
