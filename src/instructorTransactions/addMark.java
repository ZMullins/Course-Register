package instructorTransactions;

import authenticationServer.AuthenticationToken;
import offerings.CourseOffering;
import systemUsers.StudentModel;
import transaction.Transaction;

public class addMark extends Transaction{
	static {
	type = "AddMark";}
	CourseOffering course;
	double mark;
	StudentModel student;
	String assignmentorexam;
	public addMark(CourseOffering course, double mark, StudentModel student, String assignmentorexam, AuthenticationToken token) {
		this.course = course;
		this.mark = mark;
		this.student = student;
		this.assignmentorexam = assignmentorexam;
		setToken(token);
	}
	public CourseOffering getCourse() {
		return course;
	}
	public double getMark() {
		return mark;
	}
	public void setMark(double mark) {
		this.mark = mark;
	}
public String getAssignmentorexam() {
		return assignmentorexam;
	}
	public StudentModel getStudent() {
	return student;
}
public void setStudent(StudentModel student) {
	this.student = student;
}
public void setCourse(CourseOffering course) {
	this.course = course;
}
	public void setAssignmentorexam(String assignmentorexam) {
		this.assignmentorexam = assignmentorexam;
	}

	
}
