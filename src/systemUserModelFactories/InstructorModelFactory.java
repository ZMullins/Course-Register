package systemUserModelFactories;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import offerings.ICourseOffering;
import registrar.ModelRegister;
import systemUsers.InstructorModel;

public class InstructorModelFactory implements SystemUserModelFactory {

	public InstructorModel createSystemUserModel(BufferedReader br, ICourseOffering course) {
		// TODO Auto-generated method stub
		InstructorModel newInstructorModel = new InstructorModel();
		try{
		String line = br.readLine();
//		Consume a line and parse it to populate the fields available in an Instructor instance.
//		you may need to implement another such method having a different signature
		if(!ModelRegister.getInstance().checkIfUserHasAlreadyBeenCreated(line.split("\t")[2])){
			newInstructorModel.setName(line.split("\t")[0]);
			newInstructorModel.setSurname(line.split("\t")[1]);
			newInstructorModel.setID(line.split("\t")[2]);
			newInstructorModel.setIsTutorOf(new ArrayList<ICourseOffering>());
			ModelRegister.getInstance().registerUser(newInstructorModel.getID(), newInstructorModel);
		} 
		newInstructorModel = (InstructorModel) ModelRegister.getInstance().getRegisteredUser(line.split("\t")[2]);
		newInstructorModel.getIsTutorOf().add(course);
		return newInstructorModel;
		}catch(IOException ioe){
			System.out.println(ioe.getMessage());
			return null;
		}
	}

}
