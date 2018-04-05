package systemUsers;

import java.util.List;

import offerings.ICourseOffering;

public interface IInstructorModel extends SystemUserModel {
	
	void setIsTutorOf(List<ICourseOffering> courseInstructed);
	
	List<ICourseOffering> getIsTutorOf();
}
