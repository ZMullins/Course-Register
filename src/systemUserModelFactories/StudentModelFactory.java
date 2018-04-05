package systemUserModelFactories;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import customDatatypes.EvaluationTypes;
import offerings.ICourseOffering;
import registrar.ModelRegister;
import systemUsers.StudentModel;

public class StudentModelFactory implements SystemUserModelFactory {

	public StudentModel createSystemUserModel(BufferedReader br, ICourseOffering course) {
		// TODO Auto-generated method stub
		StudentModel newStudent;
		try{
		String line = br.readLine();
		if(!ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])){
//			Consume a line and populate the available fields as well as initialize all fields that need initialization
//			notice that we are using ModelRegister which is used to keep track of previously created instances with specific IDs
			newStudent = new StudentModel();
			newStudent.setName(line.split("\t")[0]);
			newStudent.setSurname(line.split("\t")[1]);
			newStudent.setID(line.split("\t")[2]);
			List<ICourseOffering> toInput = new ArrayList<ICourseOffering>();
			newStudent.setCoursesAllowed(toInput);
			Map<ICourseOffering, EvaluationTypes> toInput1 = new HashMap<ICourseOffering, EvaluationTypes>();
			newStudent.setEvaluationEntities(toInput1);
			ModelRegister.getInstance().registerUser(newStudent.getID(), newStudent);
		} 	
			newStudent = (StudentModel) ModelRegister.getInstance().getRegisteredUser(line.split("\t")[2]);
			(newStudent.getCoursesAllowed()).add(course);
			newStudent.getEvaluationEntities().put(course, EvaluationTypes.fromString(line.split("\t")[3]));
//			for debugging purposes
//			System.out.println("Name : " + newStudent.getName() + "\nSurname : " + newStudent.getSurname() + "\nID : " + 
//			newStudent.getID() + "\n");
			
			return newStudent;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage() + "exception thrown at StudentModelCreation"); 
			return null;
		}
	}
}
