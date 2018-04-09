package studentTransactions;

import authenticationServer.AuthenticationToken;
import offerings.CourseOffering;
import systemUsers.StudentModel;
import transaction.Transaction;

public class Enroll extends Transaction{
	static {
	type = "Enroll";}
	public Enroll(CourseOffering course, StudentModel student, AuthenticationToken token) {
		this.course = course;
		this.student = student;
		setToken(token);
	}
	CourseOffering course;
	StudentModel student;
	public CourseOffering getCourse() {
		return course;
	}
	public void setCourse(CourseOffering course) {
		this.course = course;
	}
	public StudentModel getStudent() {
		return student;
	}
	public void setStudent(StudentModel student) {
		this.student = student;
	}
}
