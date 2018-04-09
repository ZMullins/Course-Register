package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import authenticationServer.AuthenticationToken;
import instructorTransactions.addMark;
import offerings.CourseOffering;
import registrar.ModelRegister;
import studentTransactions.Enroll;
import system.system;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;
public class application {
	private static AuthenticationToken token;
	private static system server;
	private static Scanner scanner = new Scanner( System.in );
	public static void main(String[] args) {
			server = new system();
		start();
			
	}
	private static void start() {
		while (true) {
		do {
			System.out.print("Please login.\nID: ");
			String ID = scanner.nextLine();
			System.out.print("Password: ");
			String password = scanner.nextLine();
			token = server.loginAttempt(ID, password);
			if (token == null) {
				System.out.println("Invalid ID or password. Please try again.");
			}
				} while (token == null);
	   
		if (token.getUserType().equals("admin")) {
			adminPanel();
		}
		else if (!server.getState()) {
			System.out.println("The System is stopped. You are unable to login until the Administrator starts it.");
			start();
		}
		else {
			if (token.getUserType().equals("student"))
					studentPanel();
			else if (token.getUserType().equals("instructor"))
					instructorPanel();
		}
		System.out.println("You are now logged out.");
		 token = null; 
		}
	}
	
	private static void studentPanel() {
		System.out.print("Welcome, " + token.getTokenID() + " (student)");
		
		
		System.out.println("Some commands: \"enroll\" enroll in a course. \"notification\" adjust notification settings. \"record\" print a record for a course you are attending");
		String cmd = "";
do {
		 do {
			 System.out.print("Enter a cmd: ");
				cmd = scanner.next();
		}while (!cmd.equals("enroll")&&!cmd.equals("notification")&&!cmd.equals("record"))	;
		 if (cmd.equals("enroll")) {
			 enroll(token);
		 }
		 else if (cmd.equals("notification")) {}
		 
		 else if (cmd.equals("record")) {
			 System.out.print("What is the filename (including extension)?: ");
			 cmd = scanner.next();
			 try {
				server.readCourseFile(cmd);
			
			} catch (IOException e) {
				System.out.println("Error reading file. Ensure file name is spelt correctly.");
			}
	}} while (!cmd.equals("logout"));
	}
		
	private static void enroll(AuthenticationToken token) {
		String select;
		List<CourseOffering> courses = new ArrayList<CourseOffering>();
		List<CourseOffering> allCourses = ModelRegister.getInstance().getAllCourses();
		for (int i =0; i<allCourses.size(); i++) {
			List<StudentModel> allStudents = allCourses.get(i).getStudentsAllowedToEnroll();
			for (int j=0; j<allStudents.size();j++) {
			if (allCourses.get(i).getStudentsAllowedToEnroll().get(j).getID().equals(token.getTokenID())) {
				courses.add(allCourses.get(i));
			}
			}
		}
		if (courses.size()==0) {
			System.out.println("Sorry, you are unable to enroll in any courses");
			studentPanel();
		}
		System.out.print("Select which course you want to enroll in (by number): ");
		for (int i =0; i<courses.size();i++) {
			System.out.println((i+1)+ ". " + courses.get(i).getCourseID() +" - " +courses.get(i).getCourseName());
		}
		do {
			System.out.print("Make a selection: ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size());
		server.enroll(new Enroll(courses.get(Integer.parseInt(select)-1).getCourseID(),token.getTokenID(),token));
	}
	//TODO - Try-Catch inputs
	private static void instructorPanel() {
	
System.out.print("Welcome, " + token.getTokenID() + " (instructor).");
		
		
		System.out.println("Some commands: \"add\" Add mark for a student in one of your courses. \"modify\" Adjust a student's mark. \"calculate\" Calculate a student's mark. \"print\" Print a class record for one of your courses.");
		String cmd = "";
do {
		 do {
			 System.out.print("Enter a cmd: ");
				cmd = scanner.next();
		}while (!cmd.equals("add")&&!cmd.equals("modify")&&!cmd.equals("calculate")&&!cmd.equals("print"))	;
		 if (cmd.equals("add")) {
			addMark(token);
		 }
		 else if (cmd.equals("modify")) {
			 modifyMark(token);
		 }
		 else if (cmd.equals("calculate")) {}
		 else if (cmd.equals("print")) {
			 System.out.print("What is the filename (including extension)?: ");
			 cmd = scanner.next();
			 try {
				server.readCourseFile(cmd);
			
			} catch (IOException e) {
				System.out.println("Error reading file. Ensure file name is spelt correctly.");
			}
	}} while (!cmd.equals("logout"));
	}
		public static void modifyMark(AuthenticationToken token) {
			List<CourseOffering> courses = new ArrayList<CourseOffering>();
			System.out.println("Which of the following courses is this for (Type the number to select): ");
			List<CourseOffering> allCourses = ModelRegister.getInstance().getAllCourses();
			for (int i =0; i<allCourses.size(); i++) {
				List<InstructorModel> allInstructors = allCourses.get(i).getInstructor();
				for (int j =0; j<allInstructors.size();j++) {
					if (allCourses.get(i).getInstructor().get(j).getID().equals((token.getTokenID()))) {
						courses.add(allCourses.get(i));
						System.out.println(courses.size()+". "+courses.get(courses.size()-1).getCourseName());
					}
				}
			}
			String select;
			do {
			System.out.print("Make a selection: ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size());
			CourseOffering course = courses.get(Integer.parseInt(select)-1);
			if (course.getStudentsEnrolled().size() ==0) {
				System.out.println("There are no students enrolled in this course. Cannot modify marks.");
				instructorPanel();
			}
			System.out.println("You have selected " + course.getCourseName() + " Which student gets the mark? (Type the number to select): ");
			for (int i =0; i<course.getStudentsEnrolled().size();i++) {
				System.out.println((i+1)+". "+ course.getStudentsEnrolled().get(i).getName()+" "+course.getStudentsEnrolled().get(i).getSurname());
			}
			do {
				System.out.print("Please make a selection: ");
			select = scanner.next();
		} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > course.getStudentsEnrolled().size());

			StudentModel student = course.getStudentsEnrolled().get(Integer.parseInt(select)-1);
			System.out.println("You have selected" + student.getName() + ". What mark entity are you changing ?");
			List<String> assignments = new ArrayList<String>();
			student.getPerCourseMarks().get(course).initializeIterator();
			while (student.getPerCourseMarks().get(course).hasNext()) {
			//this may break
				student.getPerCourseMarks().get(course).next();
				assignments.add(student.getPerCourseMarks().get(course).getCurrentKey());
				System.out.println(assignments.size()+". " + assignments.get(assignments.size()-1));
			}
			do {
			System.out.println("Make a selection (by the number): ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > assignments.size());
			String assignmentorexam = assignments.get(Integer.parseInt(select)-1);
			System.out.println("What is the new mark?: ");
			select = scanner.next();
			server.modifyMark(new addMark(course.getCourseID(),Double.parseDouble(select),student.getID(),assignmentorexam,token));
		}

		public static void addMark(AuthenticationToken token) {
			List<CourseOffering> courses = new ArrayList<CourseOffering>();
			System.out.println("Which of the following courses is this for (Type the number to select): ");
			List<CourseOffering> allCourses = ModelRegister.getInstance().getAllCourses();
			for (int i =0; i<allCourses.size(); i++) {
				List<InstructorModel> allInstructors = allCourses.get(i).getInstructor();
				for (int j =0; j<allInstructors.size();j++) {
					if (allCourses.get(i).getInstructor().get(j).getID().equals((token.getTokenID()))) {
						courses.add(allCourses.get(i));
						System.out.println(courses.size()+". "+courses.get(courses.size()-1).getCourseName());
					}
				}
			}
			String select;
			do {
			System.out.print("Make a selection: ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size());
			CourseOffering course = courses.get(Integer.parseInt(select)-1);
			if (course.getStudentsEnrolled().size() ==0) {
				System.out.println("There are no students enrolled in this course. Cannot add marks.");
				instructorPanel();
			}
			System.out.println("You have selected " + course.getCourseName() + " Which student gets the mark? (Type the number to select): ");
			for (int i =0; i<course.getStudentsEnrolled().size();i++) {
				System.out.println((i+1)+". "+ course.getStudentsEnrolled().get(i).getName()+" "+course.getStudentsEnrolled().get(i).getSurname());
			}
			do {
				System.out.print("Please make a selection: ");
			select = scanner.next();
		} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > course.getStudentsEnrolled().size());

			StudentModel student = course.getStudentsEnrolled().get(Integer.parseInt(select)-1);
			System.out.println("You have selected" + student.getName() + ". What type of mark are you adding?");
			System.out.println("Enter name of Evaluation Type: ");
			String assignmentorexam = scanner.next();
			System.out.println("What is the mark?: ");
			select = scanner.next();
			server.addMark(new addMark(course.getCourseID(),Double.parseDouble(select),student.getID(),assignmentorexam,token));
			
		}

	
	private static void adminPanel() {
		System.out.print("Welcome, " + token.getTokenID() + " (admin) the system is currently ");
		if (server.getState()) {
			System.out.println("running. ");}
		else {
			System.out.println("stopped. ");
		}
		System.out.println("Some commands: \"start\" to start the system. \"stop\" to stop the system. \"load\" to load a course file. \"status\" to see state of system. \"logout\" to logout of this account.");
		String cmd = "";
do {
		 do {
			 System.out.print("Enter a cmd: ");
				cmd = scanner.next();
		}while (!cmd.equals("start")&&!cmd.equals("stop")&&!cmd.equals("load")&&!cmd.equals("logout")&&!cmd.equals("status"))	;
		 if (cmd.equals("start")) 
			 server.start(token);
		 else if (cmd.equals("stop"))
			 server.stop(token);
		 else if (cmd.equals("load")) {
			 System.out.print("What is the filename (including extension)?: ");
			 cmd = scanner.next();
			 try {
				server.readCourseFile(cmd);
			
			} catch (IOException e) {
				System.out.println("Error reading file. Ensure file name is spelt correctly.");
			}
		 }
		 else if (cmd.equals("status")) {
			 server.getStatus();
		 }
	} while (!cmd.equals("logout"));
	}
}

