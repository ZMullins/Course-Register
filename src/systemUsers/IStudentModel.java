package systemUsers;

import java.util.List;
import java.util.Map;

import customDatatypes.EvaluationTypes;
import customDatatypes.Marks;
import customDatatypes.NotificationTypes;
import offerings.ICourseOffering;

public interface IStudentModel extends SystemUserModel {

	void setCoursesAllowed(List<ICourseOffering> allowedCourses);
	void setCoursesEnrolled(List<ICourseOffering> enrolledCourses);
	void setEvaluationEntities(Map<ICourseOffering, EvaluationTypes> evaluationEntities);
	void setPerCourseMarks(Map<ICourseOffering, Marks> perCourseMarks);
	void setNotificationType(NotificationTypes notificationType);
	
	List<ICourseOffering> getCoursesAllowed();
	List<ICourseOffering> getCoursesEnrolled();
	Map<ICourseOffering, EvaluationTypes> getEvaluationEntities();
	Map<ICourseOffering, Marks> getPerCourseMarks();
	NotificationTypes getNotificationType();
	
	
}
