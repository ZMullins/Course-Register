package system;
//The main system, handles all requests and passes them onto other servers
import java.io.IOException;

import authenticationServer.AuthenticationToken;
import authenticationServer.authenticationServer;
import databaseServer.databaseServer;
import instructorTransactions.CalculateMark;
import instructorTransactions.PrintRecord;
import instructorTransactions.addMark;
import studentTransactions.Enroll;
import studentTransactions.SelectNotification;
import transaction.Transaction;
public class system {
	private boolean stateOn;
	private static authenticationServer authServer;
	private static databaseServer dataServer;
	public system() {
		System.out.println("Welcome to the System. The system starts in an 'off' state. Any logged-in admin can start it through the application.");
		dataServer = new databaseServer();
		try {
			authServer = new authenticationServer();
		} catch (IOException e) {
			System.out.println("Issue starting the authServer");
		}
		
	}
	public boolean getState() {
		return stateOn;
	}
	public void getStatus() {
		if (stateOn) {
			System.out.println("System is running.");
		}
		else {
			System.out.println("System is stopped.");
		}
	}
	private void setState(boolean stateOn) {
		this.stateOn =stateOn;
	}
	//start the server
	public void start(AuthenticationToken authToken) {
		if (authToken.getUserType().equals("admin")) {
			setState(true);
			System.out.println("System started.");
		}
		
	}
	//stop the server
	public void stop(AuthenticationToken authToken) {
		if (authToken.getUserType().equals("admin")) {
			setState(false);
			System.out.println("System stopped.");
		}
	}
	//pass login attempt to authServer
	public AuthenticationToken loginAttempt(String ID, String password) {
		return authServer.loginAttempt(ID, password);
	}
	
	public void readCourseFile(String fileName) throws IOException {
		dataServer.readCourseFile(fileName);
	}
	
	//Below are all the operations that go to databaseServer. They are checked here first to ensure the server is on and the user has the right token for the action
	public void modifyMark(addMark transaction) {
		if (canPerformTransaction(transaction)) {
			dataServer.modifyMark(transaction);
		}
	}
	public void selectNotification(SelectNotification transaction) {
		if (canPerformTransaction(transaction)) {
			dataServer.selectNotification(transaction);
		}
	}
	
	public void addMark(addMark transaction) {
		if (canPerformTransaction(transaction)) {
			dataServer.addMark(transaction);
		}
	}
	public void calculateMark(CalculateMark transaction) {
		if (canPerformTransaction(transaction)) {
			dataServer.calculateMark(transaction);
		}
	}
	public void printRecord(PrintRecord transaction) {
		if (canPerformTransaction(transaction)) {
			dataServer.printRecord(transaction);
		}
	}
	public void printRecordS(PrintRecord transaction) {
		if (canPerformTransaction(transaction)) {
			dataServer.printRecordS(transaction);
		}
	}
	public void enroll(Enroll transaction) {
		if (canPerformTransaction(transaction)) {
			dataServer.enroll(transaction);
		}
	}
	//Checks if a user is allowed to perform a certain transaction
	public boolean canPerformTransaction(Transaction transaction) {
		if (!stateOn) {
			System.out.println("System is currently stopped. Unable to perform operation.\n");
		}
		else if (transaction.getToken().getUserType().equals("instructor")) {
			if (transaction.getType().equals("AddMark") ||transaction.getType().equals("Calculate") ||transaction.getType().equals("Print") )
				return true;
		}
		else if (transaction.getToken().getUserType().equals("student")) {
			if (transaction.getType().equals("Enroll") || transaction.getType().equals("SelectNotification") ||transaction.getType().equals("Print") )
				return true;
		}
		return false;
}
}
