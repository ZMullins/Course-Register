package offerings;

import java.util.List;
import java.util.Map;

import customDatatypes.EvaluationTypes;
import customDatatypes.Weights;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;

public interface ICourseOffering {
	
	List<StudentModel> getStudentsAllowedToEnroll();

	void setStudentsAllowedToEnroll(List<StudentModel> studentsAllowedToEnroll);

	List<StudentModel> getStudentsEnrolled();

	void setStudentsEnrolled(List<StudentModel> studentsEnrolled);

	Map<EvaluationTypes, Weights> getEvaluationStrategies();

	void setEvaluationStrategies(Map<EvaluationTypes, Weights> evaluationStrategies);

	String getCourseName();

	void setCourseName(String courseName);

	String getCourseID();

	void setCourseID(String courseID);

	Integer getSemester();

	void setSemester(Integer semester);

	List<InstructorModel> getInstructor();

	void setInstructor(List<InstructorModel> instructor);

	void addInstructor(InstructorModel instructor);

	void removeInstructor(InstructorModel instructor);
	
//	String getCourseName();
//	String getCourseID();
//	Integer getSemester();
//	List<InstructorModel> getInstructor();
//	void setCourseName(String name);
//	void setCourseID(String ID);
//	void setSemester(Integer semester);
//	void setInstructor(List<InstructorModel> instructor);
//	void addInstructor(InstructorModel instructor);
//	void removeInstructor(InstructorModel instructor);
	
}
