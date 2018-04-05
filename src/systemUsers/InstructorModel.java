package systemUsers;

import java.util.List;

import offerings.ICourseOffering;

public class InstructorModel implements IInstructorModel {

	private String name;
	private String surname;
	private String ID;
	private List<ICourseOffering> isTutorOf;
	
	public InstructorModel(){
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
	
	public List<ICourseOffering> getIsTutorOf() {
		return isTutorOf;
	}
	
	public void setIsTutorOf(List<ICourseOffering> courses) {
		this.isTutorOf = courses;
	}
	
	
	
}
