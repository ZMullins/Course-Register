package instructorTransactions;

import authenticationServer.AuthenticationToken;
import offerings.CourseOffering;
import transaction.Transaction;

public class PrintRecord extends Transaction {
	static {
		type = "Print";}
	public PrintRecord(CourseOffering course, AuthenticationToken token) {
		this.course=course;
		setToken(token);
	}
		CourseOffering course;
		public CourseOffering getCourse() {
			return course;
		}
		public void setCourse(CourseOffering course) {
			this.course = course;
		}
}
