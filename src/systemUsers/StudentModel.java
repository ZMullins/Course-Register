package systemUsers;

import java.util.List;
import java.util.Map;

import customDatatypes.EvaluationTypes;
import customDatatypes.Marks;
import customDatatypes.NotificationTypes;
import offerings.ICourseOffering;

public class StudentModel implements IStudentModel{
	
	private String name;
	private String surname;
	private String ID;
	private List<ICourseOffering> coursesAllowed;
	private List<ICourseOffering> coursesEnrolled;
	private Map<ICourseOffering, EvaluationTypes> evaluationEntities;
//	check the comments at the Marks Class this map should contain as many pairs of <CourseOffering, Marks> as course that 
//	the student has enrolled in.
	private Map<ICourseOffering, Marks> perCourseMarks;
	private NotificationTypes notificationType;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
	
	public List<ICourseOffering> getCoursesAllowed() {
		return coursesAllowed;
	}
	
	public void setCoursesAllowed(List<ICourseOffering> coursesAllowed) {
		this.coursesAllowed = coursesAllowed;
	}
	
	public List<ICourseOffering> getCoursesEnrolled() {
		return coursesEnrolled;
	}
	
	public void setCoursesEnrolled(List<ICourseOffering> coursesEnrolled) {
		this.coursesEnrolled = coursesEnrolled;
	}
	
	public Map<ICourseOffering, EvaluationTypes> getEvaluationEntities() {
		return evaluationEntities;
	}
	
	public void setEvaluationEntities(Map<ICourseOffering, EvaluationTypes> evaluationEntities) {
		this.evaluationEntities = evaluationEntities;
	}
	
	public Map<ICourseOffering, Marks> getPerCourseMarks() {
		return perCourseMarks;
	}
	
	public void setPerCourseMarks(Map<ICourseOffering, Marks> perCourseMarks) {
		this.perCourseMarks = perCourseMarks;
	}
	
	public NotificationTypes getNotificationType() {
		return notificationType;
	}
	
	public void setNotificationType(NotificationTypes notificationType) {
		this.notificationType = notificationType;
	}
	
	
	
}
