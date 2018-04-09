package instructorTransactions;

import authenticationServer.AuthenticationToken;
import offerings.CourseOffering;
import systemUsers.StudentModel;
import transaction.Transaction;

public class CalculateMark extends Transaction {
	static {
		type = "Calculate";}
	public CalculateMark(CourseOffering course, StudentModel student, AuthenticationToken token) {
		this.course=course;
		this.student=student;
		setToken(token);
	}
		CourseOffering course;
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
		StudentModel student;
}
