package studentTransactions;

import authenticationServer.AuthenticationToken;
import customDatatypes.NotificationTypes;
import systemUsers.StudentModel;
import transaction.Transaction;

public class SelectNotification extends Transaction {
	static {
		type = "SelectNotification";
	}
	private NotificationTypes notificationType;
	public NotificationTypes getNotificationType() {
		return notificationType;
	}
	private StudentModel student;
	public StudentModel getStudent() {
		return student;
	}
	public void setStudent(StudentModel student) {
		this.student = student;
	}
	public void setNotificationType(NotificationTypes notificationType) {
		this.notificationType = notificationType;
	}
	public SelectNotification(NotificationTypes notificationType, StudentModel student, AuthenticationToken token) {
		this.notificationType=notificationType;
		this.student = student;
		setToken(token);
	}

}
