package systemUserModelFactories;

import java.io.BufferedReader;

import offerings.ICourseOffering;
import systemUsers.AdminModel;

public class AdminModelFactory implements SystemUserModelFactory {

	public AdminModel createSystemUserModel(BufferedReader br, ICourseOffering course) {
		// TODO Auto-generated method stub
		return null;
	}

//----------------------------------------------
	public static void main(String[] args){
//		AdminModelFactory factory = new AdminModelFactory();
//		AdminModel am = factory.createSystemUserModel(null, null);
//		am.getID();
	}

}

