//Application is the main program and how the server is accessed
package application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import authenticationServer.AuthenticationToken;
import customDatatypes.NotificationTypes;
import customDatatypes.Weights;
import instructorTransactions.CalculateMark;
import instructorTransactions.PrintRecord;
import instructorTransactions.addMark;
import offerings.CourseOffering;
import registrar.ModelRegister;
import studentTransactions.Enroll;
import studentTransactions.SelectNotification;
import system.system;
import systemUsers.InstructorModel;
import systemUsers.StudentModel;
public class application {
	private static AuthenticationToken token;
	//Server is the system itself, and connected to databaseServer and authServer.
	private static system server;
	private static Scanner scanner = new Scanner( System.in );
	public static void main(String[] args) {
		//Create server and then start the main loop
		server = new system();
		start();
			
	}
	//Main loop
	private static void start() {
		//Always running
		while (true) {
		do {
			//Asks user to login and performs login attempt
			System.out.println("Please login.");
			System.out.print("ID: ");
			String ID = scanner.next();
			System.out.print("Password: ");
			String password = scanner.next();
			token = server.loginAttempt(ID, password);
			//if login is unsucessful no token generated
			if (token == null) {
				System.out.println("Invalid ID or password. Please try again.\n");
			}
				} while (token == null);
	   //Once logged in it checks the user type and goes to the appropriate panel
		if (token.getUserType().equals("admin")) {
			adminPanel();
		}
		//If server isn't running, only admin can login
		else if (!server.getState()) {
			System.out.println("The System is stopped. You are unable to login until the Administrator starts it.\n");
			return;
		}
		else {
			if (token.getUserType().equals("student"))
					studentPanel();
			else if (token.getUserType().equals("instructor"))
					instructorPanel();
		}
		//After the panel is closed user is logged out and token is cleared
		System.out.println("You are now logged out.\n");
		 token = null; 
		}
	}

	private static void studentPanel() {
		System.out.println("\nWelcome, " + token.getTokenID() + " (student)\n");
		//Finds out whether the student has confirmed their notification preference
		StudentModel student = null;
		List<CourseOffering> allCourses = ModelRegister.getInstance().getAllCourses();
		for (int i =0; i<allCourses.size(); i++) {
			List<StudentModel> allStudents = allCourses.get(i).getStudentsAllowedToEnroll();
			for (int j=0; j<allStudents.size();j++) {
			if (allCourses.get(i).getStudentsAllowedToEnroll().get(j).getID().equals(token.getTokenID())) {
				student = allCourses.get(i).getStudentsAllowedToEnroll().get(j);
				break;
			}
			}
		}
		//If they haven't decided their notification preferences it makes them choose
		while (student.getNotificationType() == null) {
			System.out.println("You haven't set a notification type. You will now be asked to do so");
			notification(token);
		}
		
		System.out.println("Some commands: \"enroll\" enroll in a course. \"notification\" adjust notification settings. \"record\" print a record for a course you are attending");
		String cmd = "";
		//Asks for their command
do {
		 do {
			 System.out.print("\nEnter a cmd: ");
				cmd = scanner.next();
		}while (!cmd.equals("enroll")&&!cmd.equals("notification")&&!cmd.equals("record")&&!cmd.equals("logout"))	;
		//Performs the relevant action
		 if (cmd.equals("enroll")) {
			 enroll(token);
		 }
		 else if (cmd.equals("notification")) {
			 notification(token);
		 }
		 
		 else if (cmd.equals("record")) {
			 printRecordS(token);
	}} while (!cmd.equals("logout"));
	}
	
	//Enrolls a student in a class
	private static void enroll(AuthenticationToken token) {
		String select;
		//Below finds out which courses the student is allowed to enroll in
		List<CourseOffering> courses = new ArrayList<CourseOffering>();
		List<CourseOffering> allCourses = ModelRegister.getInstance().getAllCourses();
		StudentModel student = null;
		for (int i =0; i<allCourses.size(); i++) {
			List<StudentModel> allStudents = allCourses.get(i).getStudentsAllowedToEnroll();
			for (int j=0; j<allStudents.size();j++) {
			if (allCourses.get(i).getStudentsAllowedToEnroll().get(j).getID().equals(token.getTokenID())) {
				courses.add(allCourses.get(i));
				student=allCourses.get(i).getStudentsAllowedToEnroll().get(j);
			}
			}
		}
		//If a student can't enroll in any class it takes them back to the panel
		if (courses.size()==0) {
			System.out.println("Sorry, you are unable to enroll in any courses");
			return;
		}
		//Otherwise they select their class they wish to enroll in
		System.out.println("Select which course you want to enroll in (by number): ");
		for (int i =0; i<courses.size();i++) {
			System.out.println((i+1)+ ". " + courses.get(i).getCourseID() +" - " +courses.get(i).getCourseName());
		}
		try {
		do {
			System.out.print("\nMake a selection: ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size()); }
		catch (NumberFormatException e) {
			System.out.println("That's not a number, invalid input. Returning to main menu");
			return;
		}
		//Enroll using an enroll transaction on the server
		server.enroll(new Enroll(courses.get(Integer.parseInt(select)-1),student,token));
	}

	//Allows user to change their notification preference
	private static void notification(AuthenticationToken token) {
		String select;
		int choice;
		//First the matching studentModel is found
		StudentModel student = null;
		List<CourseOffering> allCourses = ModelRegister.getInstance().getAllCourses();
		for (int i =0; i<allCourses.size(); i++) {
			List<StudentModel> allStudents = allCourses.get(i).getStudentsAllowedToEnroll();
			for (int j=0; j<allStudents.size();j++) {
			if (allCourses.get(i).getStudentsAllowedToEnroll().get(j).getID().equals(token.getTokenID())) {
				student = allCourses.get(i).getStudentsAllowedToEnroll().get(j);
				break;
			}
			}
		}
		//They are offered selections for notification preferences
		System.out.println("How would you like to be notified of course updates?:");
		System.out.println("1. E-mail");
		System.out.println("2. Cellphone");
		System.out.println("3. Snail Mail\n");
		//Uses an array of the NotificationTypes enum to select instead of if/else statements
		NotificationTypes[] types = {NotificationTypes.EMAIL,NotificationTypes.CELLPHONE,NotificationTypes.PIGEON_POST};
		try {
			do {
			System.out.print("\nMake a selection: ");
			select = scanner.next();
			choice = Integer.parseInt(select); 
			} while (choice != 1 && choice != 2 && choice!=3);
	} catch (NumberFormatException e) {
				System.out.println("That's not a number, invalid input. Returning to main menu");
				return;
			}
		server.selectNotification(new SelectNotification(types[choice-1],student,token));
	}
//The panel for instructors
	private static void instructorPanel() {


System.out.println("\nWelcome, " + token.getTokenID() + " (instructor).\n");
		
		
		System.out.println("Some commands: \"add\" Add mark for a student in one of your courses. \"modify\" Adjust a student's mark. \"calculate\" Calculate a student's mark. \"print\" Print a class record for one of your courses.");
		String cmd = "";
//Asks for their command
		do {
		 do {
			 System.out.print("\nEnter a cmd: ");
				cmd = scanner.next();
		}while (!cmd.equals("add")&&!cmd.equals("modify")&&!cmd.equals("calculate")&&!cmd.equals("print")&&!cmd.equals("logout"))	;
		 //Chooses appropriate function
		 if (cmd.equals("add")) {
			addMark(token);
		 }
		 else if (cmd.equals("modify")) {
			 modifyMark(token);
		 }
		 else if (cmd.equals("calculate")) {
			 calculateGrade(token);
		 }
		 else if (cmd.equals("print")) {
			 printRecordI(token);
	}} while (!cmd.equals("logout"));
	}
	//Calculates a students grade for an instructor	
	private static void calculateGrade(AuthenticationToken token) {
		//First finds the courses the instructor teaches and makes them select one
		
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
			if (courses.size()==0) {
				System.out.println("Sorry, you are not instructing any courses");
		return;	}
			String select;
			try { 
				do {
			//A selection is then made
			System.out.print("\nMake a selection: ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size());}
				catch (NumberFormatException e) {
					System.out.println("That's not a number, invalid input. Returning to main menu");
					return;
				}
			CourseOffering course = courses.get(Integer.parseInt(select)-1);
			if (course.getStudentsEnrolled().size() ==0) {
				System.out.println("There are no students enrolled in this course. Cannot add marks.");
				return;
			}
			//Makes them select the student
			System.out.println("You have selected " + course.getCourseName() + " Which student do you want to calculate mark for? (Type the number to select): ");
			for (int i =0; i<course.getStudentsEnrolled().size();i++) {
				System.out.println((i+1)+". "+ course.getStudentsEnrolled().get(i).getName()+" "+course.getStudentsEnrolled().get(i).getSurname());
			}
			try {
			do {
				System.out.print("Please make a selection: ");
			select = scanner.next();
		} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > course.getStudentsEnrolled().size());}
			catch (NumberFormatException e) {
				System.out.println("That's not a number, invalid input. Returning to main menu");
				return;
			}
			StudentModel student = course.getStudentsEnrolled().get(Integer.parseInt(select)-1);
		//Mark is then calculated
			server.calculateMark(new CalculateMark(course,student,token));
		}
	//Change an existing student's mark
private static void modifyMark(AuthenticationToken token) {
	//First the instructor has to select which course this is for
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
			if (courses.size()==0) {
				System.out.println("Sorry, you are not instructing any courses.");
		return;	}
			String select;
			try {
			do {
			System.out.print("\nMake a selection: ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size());}
			catch (NumberFormatException e) {
				System.out.println("That's not a number, invalid input. Returning to main menu");
				return;
			}
		
			CourseOffering course = courses.get(Integer.parseInt(select)-1);
			if (course.getStudentsEnrolled().size() ==0) {
				System.out.println("There are no students enrolled in this course. Cannot modify marks.");
				return;
			}
			//Then a student is selected
			System.out.println("You have selected " + course.getCourseName() + " Which student gets the mark? (Type the number to select): ");
			for (int i =0; i<course.getStudentsEnrolled().size();i++) {
				System.out.println((i+1)+". "+ course.getStudentsEnrolled().get(i).getName()+" "+course.getStudentsEnrolled().get(i).getSurname());
			}try {
			do {
				System.out.print("\nPlease make a selection: ");
			select = scanner.next();
		} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > course.getStudentsEnrolled().size());}
			catch (NumberFormatException e) {
				System.out.println("That's not a number, invalid input. Returning to main menu\n");
				return;
			}
			//Then the mark entity to be changed is selected
			StudentModel student = course.getStudentsEnrolled().get(Integer.parseInt(select)-1);
			System.out.println("You have selected " + student.getName() + ". What mark entity are you changing ?");
			List<String> assignments = new ArrayList<String>();
			student.getPerCourseMarks().get(course).initializeIterator();
			while (student.getPerCourseMarks().get(course).hasNext()) {
				student.getPerCourseMarks().get(course).next();
				assignments.add(student.getPerCourseMarks().get(course).getCurrentKey());
				System.out.println(assignments.size()+". " + assignments.get(assignments.size()-1));
			}try {
			do {
			System.out.print("Make a selection (by the number): ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > assignments.size());}
			catch (NumberFormatException e) {
				System.out.println("That's not a number, invalid input. Returning to main menu\n");
				return;
			}
			String assignmentorexam = assignments.get(Integer.parseInt(select)-1);
			//finally a new mark is decided
			System.out.print("What is the new mark?: ");
			double mark;
try {
	
	
	select = scanner.next();
	mark = Double.parseDouble(select);
}	
			catch (NumberFormatException e) {
				System.out.println("Invalid input (not a number), Now returning to main menu");
				return;
			}
			server.modifyMark(new addMark(course,mark,student,assignmentorexam,token));
		}

//Prints the record of a student in a class of their choice
private static void printRecordS(AuthenticationToken token) {
	List<CourseOffering> courses = new ArrayList<CourseOffering>();
	System.out.println("Which of the following courses is this for (Type the number to select): ");
	List<CourseOffering> allCourses = ModelRegister.getInstance().getAllCourses();
	for (int i =0; i<allCourses.size(); i++) {
		List<StudentModel> allStudents = allCourses.get(i).getStudentsEnrolled();
		for (int j =0; j<allStudents.size();j++) {
			if (allCourses.get(i).getStudentsEnrolled().get(j).getID().equals((token.getTokenID()))) {
				courses.add(allCourses.get(i));
				System.out.println(courses.size()+". "+courses.get(courses.size()-1).getCourseName());
			}
		}
	}
	if (courses.size()==0) {
		System.out.println("Sorry, you are not enrolled in any courses");
return;	}
	String select;
	try{ do {
	System.out.print("\nMake a selection: ");
	select = scanner.next();
	} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size());}
	catch (NumberFormatException e) {
		System.out.println("That's not a number, invalid input. Returning to main menu");
		return;
	}
	CourseOffering course = courses.get(Integer.parseInt(select)-1);		
	server.printRecordS(new PrintRecord(course,token));
}
private static void printRecordI(AuthenticationToken token) {
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
	if (courses.size()==0) {
		System.out.println("Sorry, you are unable to enroll in any courses");
		return;
	}
	String select;
	try {
	do {
	System.out.print("\nMake a selection: ");
	select = scanner.next();
	} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size());}
	catch (NumberFormatException e) {
		System.out.println("That's not a number, invalid input. Returning to main menu");
		return;
	}
	CourseOffering course = courses.get(Integer.parseInt(select)-1);
	server.printRecord(new PrintRecord(course,token));
}

//Instructor adds a mark to a student
private static void addMark(AuthenticationToken token) {
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
			if (courses.size()==0) {
				System.out.println("Sorry, you are not instructing any courses");
		return;	}
			String select;
			try {
			do {
			System.out.print("\nMake a selection: ");
			select = scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > courses.size());}
			catch (NumberFormatException e) {
				System.out.println("That's not a number, invalid input. Returning to main menu");
				return;
			}
			CourseOffering course = courses.get(Integer.parseInt(select)-1);
			if (course.getStudentsEnrolled().size() ==0) {
				System.out.println("There are no students enrolled in this course. Cannot add marks.");
				return;
			}
			System.out.println("You have selected " + course.getCourseName() + " Which student gets the mark? (Type the number to select): ");
			for (int i =0; i<course.getStudentsEnrolled().size();i++) {
				System.out.println((i+1)+". "+ course.getStudentsEnrolled().get(i).getName()+" "+course.getStudentsEnrolled().get(i).getSurname());
			} try {
			do {
				System.out.print("\nPlease make a selection: ");
			select = scanner.next();
		} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > course.getStudentsEnrolled().size());}
			catch (NumberFormatException e) {
				System.out.println("That's not a number, invalid input. Returning to main menu");
				return;
			}

			StudentModel student = course.getStudentsEnrolled().get(Integer.parseInt(select)-1);
			System.out.println("You have selected " + student.getName() + ". What type of mark are you adding?");
			
			Weights items = course.getListOfItems(student.getID());
			items.initializeIterator();
			int i =1;
			while(items.hasNext()){
				items.next();
				System.out.println(i+". "+items.getCurrentKey());
			i++;
			}
			try {
				do {
					System.out.print("\nPlease make a selection: ");
				select= scanner.next();
			} while (Integer.parseInt(select) < 1 || Integer.parseInt(select) > i-1);}
				catch (NumberFormatException e) {
					System.out.println("That's not a number, invalid input. Returning to main menu");
					return;
				}
	
			Weights weights = course.getListOfItems(student.getID());
			weights.initializeIterator();
			int count = 1;
			
			String assignmentorexam=null;
					while (count <= Integer.parseInt(select)) {
			
				weights.next();
				assignmentorexam=weights.getCurrentKey();
				count++;
			}
			
			System.out.print("What is the mark?: ");
			double mark;
			try {
			select = scanner.next();
			mark = Double.parseDouble(select);}
			catch (NumberFormatException e) {
				System.out.println("Invalid input (not a number), Now returning to main menu");
				return;
			}
			server.addMark(new addMark(course,mark,student,assignmentorexam,token));
			
		}

//The admin panel, able to start and stop the system and load class files
	private static void adminPanel() {
		System.out.print("\nWelcome, " + token.getTokenID() + " (admin) the system is currently ");
		if (server.getState()) {
			System.out.println("running.\n ");}
		else {
			System.out.println("stopped. \n");
		}
		System.out.println("Some commands: \"start\" to start the system. \"stop\" to stop the system. \"load\" to load a course file. \"status\" to see state of system. \"logout\" to logout of this account.");
		String cmd = "";
do {
		 do {
			 System.out.print("\nEnter a cmd: ");
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

