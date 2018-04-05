package systemUserModelFactories;

import java.io.BufferedReader;

import offerings.ICourseOffering;
import systemUsers.SystemUserModel;

public interface SystemUserModelFactory {
	SystemUserModel createSystemUserModel(BufferedReader br, ICourseOffering course);
}
