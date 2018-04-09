package instructorTransactions;

import authenticationServer.AuthenticationToken;
import transaction.Transaction;

public class addMark extends Transaction{
	static {
	type = "AddMark";}
	String courseID;
	double mark;
	String studentID;
	String assignmentorexam;
	public addMark(String courseID, double mark, String studentID, String assignmentorexam, AuthenticationToken token) {
		this.courseID = courseID;
		this.mark = mark;
		this.studentID = studentID;
		this.assignmentorexam = assignmentorexam;
		setToken(token);
	}
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public double getMark() {
		return mark;
	}
	public void setMark(double mark) {
		this.mark = mark;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getAssignmentorexam() {
		return assignmentorexam;
	}
	public void setAssignmentorexam(String assignmentorexam) {
		this.assignmentorexam = assignmentorexam;
	}

	
}
