package offerings;

import java.util.List;
import java.util.Map;

import customDatatypes.EvaluationTypes;
import customDatatypes.Marks;
import customDatatypes.Weights;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;

public class CourseOffering implements ICourseOffering{
	
	String courseName;
	String courseID;
	Integer semester;
	List<InstructorModel> instructor;
	List<StudentModel> studentsAllowedToEnroll;
	List<StudentModel> studentsEnrolled;
//	check the comments at the Weights Class this map should contain at most 4 <key, value> pairs (ergo: <FA,value> <FC, value>
//	<PA, value> <PC, value>)
	Map<EvaluationTypes, Weights> evaluationStrategies;
	
	public List<StudentModel> getStudentsAllowedToEnroll() {
		return studentsAllowedToEnroll;
	}

	public void setStudentsAllowedToEnroll(List<StudentModel> studentsAllowedToEnroll) {
		this.studentsAllowedToEnroll = studentsAllowedToEnroll;
	}

	public List<StudentModel> getStudentsEnrolled() {
		return studentsEnrolled;
	}

	public void setStudentsEnrolled(List<StudentModel> studentsEnrolled) {
		this.studentsEnrolled = studentsEnrolled;
	}

	public Map<EvaluationTypes, Weights> getEvaluationStrategies() {
		return evaluationStrategies;
	}

	public void setEvaluationStrategies(Map<EvaluationTypes, Weights> evaluationStrategies) {
		this.evaluationStrategies = evaluationStrategies;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getCourseID() {
		return courseID;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public Integer getSemester() {
		return semester;
	}
	
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	
	public List<InstructorModel> getInstructor() {
		return instructor;
	}
	
	public void setInstructor(List<InstructorModel> instructor) {
		this.instructor = instructor;
	}
	
	public void addInstructor(InstructorModel instructor){
		this.instructor.add(instructor);
	}
	
	public void removeInstructor(InstructorModel instructor){
		this.instructor.remove(instructor);
	}
	
//	Calculates the Final Grades using the Weights and Marks utility classes see the comments in 
//	these classes if unsure of how this works
	public void calculateFinalGrades(){
		Double finalGrade; 
		for(StudentModel studentModel : studentsEnrolled){
			finalGrade = 0D;
			Weights weights = evaluationStrategies.get(studentModel.getEvaluationEntities().get(this));
			Marks marks  = studentModel.getPerCourseMarks().get(this);
			weights.initializeIterator();
			while(weights.hasNext()){
				weights.next();
				finalGrade += weights.getCurrentValue() * marks.getValueWithKey(weights.getCurrentKey());
			}
		}
	}

//	Calculates the Final Grades using the Weights and Marks utility classes see the comments in 
//	these classes if unsure of how this works
	public void calculateFinalGrade(String ID){
		StudentModel target = null;
		Double finalGrade;
		for(StudentModel studentModel : studentsEnrolled)
			if (studentModel.getID().equals(ID)) 
				target = studentModel;
		finalGrade = 0D;
		Weights weights = evaluationStrategies.get(target.getEvaluationEntities().get(this));
		Marks marks  = target.getPerCourseMarks().get(this);
		weights.initializeIterator();
		while(weights.hasNext()){
			weights.next();
			finalGrade += weights.getCurrentValue() * marks.getValueWithKey(weights.getCurrentKey());
		}
	}
	
}
