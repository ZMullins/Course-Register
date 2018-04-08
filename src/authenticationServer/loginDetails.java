package authenticationServer;

public class loginDetails {
private String password;
private String userType;

public loginDetails() {
}
public AuthenticationToken loginAttempt(String ID, String password) {
	if (this.password.equals(password)) {
		return new AuthenticationToken(userType, ID, userType.length());
	}
	return null;
}
public String getUserType() {
	return userType;
}
public void setUserType(String userType) {
	this.userType = userType;
}
public void setPassword(String password) {
	this.password = password;
}
}
