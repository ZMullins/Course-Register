package registrar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import offerings.CourseOffering;
import systemUsers.SystemUserModel;

public class ModelRegister {
//	Map maintaining copies of existing SystemUserModel objects mapped using their Unique IDs
	private Map<String, SystemUserModel> modelRegister = new HashMap<String, SystemUserModel>();
//	Map maintaining copies of existing CourseOffering objects mapped using their Unique IDs
	private Map<String, CourseOffering> courseRegister = new HashMap<String, CourseOffering>();
	
//	this is a classic implementation of the singleton design pattern
	private static ModelRegister instance;
	private ModelRegister(){
	}
	
	public static ModelRegister getInstance(){
		if(instance == null)
			instance = new ModelRegister();
		return instance;
	}
//	the method names should be selfExplanatory
	public boolean checkIfUserHasAlreadyBeenCreated(String ID){
		return modelRegister.containsKey(ID);
	}
	
	public void registerUser(String ID, SystemUserModel user){
		modelRegister.put(ID, user);
	}
	
	public SystemUserModel getRegisteredUser(String ID){
		return modelRegister.get(ID);
	}
	
	public boolean checkIfCourseHasAlreadyBeenCreated(String ID){
		return courseRegister.containsKey(ID);
	}
	
	public void registerCourse(String ID, CourseOffering course){
		courseRegister.put(ID, course);
	}
	
	public CourseOffering getRegisteredCourse(String ID){
		return courseRegister.get(ID);
	}
	
	public List<CourseOffering> getAllCourses(){
		List<CourseOffering> courses = new ArrayList<CourseOffering>();
		courses.addAll(courseRegister.values());
		return courses;
	}

}
