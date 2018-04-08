package databaseServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import offerings.CourseOffering;
import offerings.ICourseOffering;
import offerings.OfferingFactory;
import systemUsers.StudentModel;

public class databaseServer {
	public void readCourseFile(String fileName) throws IOException {
//	Create an instance of an OfferingFactory
	OfferingFactory factory = new OfferingFactory();
	BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
//	Use the factory to populate as many instances of courses as many files we've got.
	CourseOffering	course = factory.createCourseOffering(br);
	br.close();
//	CourseOffering course = ModelRegister.getInstance().getRegisteredCourse(courseOffering.getCourseID());
	System.out.println("\nFile was read successfully. Course below added to system: \n");
		System.out.println("ID : " + course.getCourseID() + "\nCourse name : " + course.getCourseName() + "\nSemester : " + 
		course.getSemester());
		System.out.println("Students allowed to enroll\n");
		for(StudentModel student : course.getStudentsAllowedToEnroll()){
			System.out.println("Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() + 
					"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " + 
					student.getEvaluationEntities().get(course) + "\n\n");
		}
		
		for(StudentModel student : course.getStudentsAllowedToEnroll()){
			for(ICourseOffering course2 : student.getCoursesAllowed())
			System.out.println(student.getName() + "\t\t -> " + course2.getCourseName());
		}
	
	}
	
}
