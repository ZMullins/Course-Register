package databaseServer;
//Database server handles all transaction requests
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import customDatatypes.Marks;
import instructorTransactions.CalculateMark;
import instructorTransactions.PrintRecord;
import instructorTransactions.addMark;
import offerings.CourseOffering;
import offerings.ICourseOffering;
import offerings.OfferingFactory;
import registrar.ModelRegister;
import studentTransactions.Enroll;
import studentTransactions.SelectNotification;
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
//Add a mark	
	public void addMark(addMark transaction) {
		transaction.getStudent().addToCourseMarks(transaction.getAssignmentorexam(), transaction.getMark(), transaction.getCourse());
		System.out.println("The mark was successfully added. " + transaction.getStudent().getName() + " was notified by " + transaction.getStudent().getNotificationType());
}
	//change a mark
	public void modifyMark(addMark transaction) {
		CourseOffering course = ModelRegister.getInstance().getRegisteredCourse(transaction.getCourse().getCourseID());
		List<StudentModel> students = course.getStudentsEnrolled();
		int i =0;
		while (!students.get(i).getID().equals( transaction.getStudent().getID())) { 
				i++;
		}
		students.get(i).getPerCourseMarks().get(course).initializeIterator();
		while (students.get(i).getPerCourseMarks().get(course).hasNext()) {
			Entry<String, Double> entry = students.get(i).getPerCourseMarks().get(course).getNextEntry();
			if (entry.getKey().equals(transaction.getAssignmentorexam())) {
				entry.setValue(transaction.getMark());
			}
			students.get(i).getPerCourseMarks().get(course).next();
		}
		students.get(i).getPerCourseMarks().get(course).addToEvalStrategy(transaction.getAssignmentorexam(), transaction.getMark());
		System.out.println("The mark was successfully changed. " + transaction.getStudent().getName() + " was notified by " + transaction.getStudent().getNotificationType());
	}
	//enroll student in a course
	public void enroll(Enroll transaction) {
		CourseOffering course = ModelRegister.getInstance().getRegisteredCourse(transaction.getCourse().getCourseID());
		List<StudentModel> students = course.getStudentsAllowedToEnroll();
		int i =0;
		while (!students.get(i).getID().equals( transaction.getStudent().getID())) { 
			i++;
			}
		if (students.get(i).getCoursesEnrolled() == null)
			students.get(i).setCoursesEnrolled(new ArrayList<ICourseOffering>());
		students.get(i).getCoursesEnrolled().add(course);
		Map<ICourseOffering,Marks> perCourseMarks = new HashMap<ICourseOffering,Marks>();
		perCourseMarks.put(course, new Marks());
		students.get(i).setPerCourseMarks(perCourseMarks);
course.getStudentsEnrolled().add(students.get(i));

		System.out.println("You have successfully enrolled in " +course.getCourseName());
	}
	//Calculate a mark
	public void calculateMark(CalculateMark transaction) {
	Double mark = transaction.getCourse().calculateFinalGrade(transaction.getStudent().getID());
	if (mark == -1) {
		System.out.println("Not all the marks are in, final mark cannot be calculated");
		return;
	}
	System.out.println("The final mark of " + transaction.getStudent().getName() +" " + transaction.getStudent().getSurname() + " in " +transaction.getCourse().getCourseName() + " is " + mark);
	}
	
	//Select notification preference for a student
	public void selectNotification(SelectNotification transaction) {
	transaction.getStudent().setNotificationType(transaction.getNotificationType());
	System.out.println("Notification Type Changed Successfully");
	}
//Print a single students record in a course
	public void printRecordS(PrintRecord transaction) {
	transaction.getCourse().showRecord(transaction.getToken().getTokenID());
	}
	//Print all students records in a course
	public void printRecord(PrintRecord transaction) {
		CourseOffering course = transaction.getCourse();
			System.out.println("ID : " + course.getCourseID() + "\nCourse name : " + course.getCourseName() + "\nSemester : " + 
			course.getSemester());
			System.out.println("Students in class\n");
			for(StudentModel student : course.getStudentsEnrolled()){
				System.out.println("Student name : " + student.getName() + "\nStudent surname : " + student.getSurname() + 
						"\nStudent ID : " + student.getID() + "\nStudent EvaluationType : " + 
						student.getEvaluationEntities().get(course) + "\nCurrent Marks:");
				transaction.getCourse().showRecord(student.getID());
				System.out.println("");
			}
		}
	}
