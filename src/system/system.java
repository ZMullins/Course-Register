package system;

import java.io.IOException;

import authenticationServer.AuthenticationToken;
import authenticationServer.authenticationServer;

public class system {
	private boolean stateOn;
	private static authenticationServer authServer;
	public system() {
		System.out.println("Welcome to the System. The system starts in an 'off' state. Any logged-in admin can start it through the application.");
		try {
			authServer = new authenticationServer();
		} catch (IOException e) {
			System.out.println("Issue starting the authServer");
		}
	}
	public boolean getState() {
		return stateOn;
	}
	private void setState(boolean stateOn) {
		this.stateOn =stateOn;
	}
	public void start(AuthenticationToken authToken) {
		if (authToken.getUserType().equals("admin")) {
			setState(true);
			
		}
		
	}
	public void stop(AuthenticationToken authToken) {
		if (authToken.getUserType().equals("admin")) {
			setState(false);
		}
	}
	public AuthenticationToken loginAttempt(String ID, String password) {
		return authServer.loginAttempt(ID, password);
	}
}
