package studentTransactions;

import authenticationServer.AuthenticationToken;
import transaction.Transaction;

public class Enroll extends Transaction{
	static {
	type = "Enroll";}
	public Enroll(String courseID, String studentID, AuthenticationToken token) {
		this.courseID = courseID;
		this.studentID = studentID;
		setToken(token);
	}
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	String courseID;
	String studentID;
}
