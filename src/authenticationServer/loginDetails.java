package authenticationServer;

public class loginDetails {
private String password;
private String userType;

public loginDetails() {
}
public AuthenticationToken loginAttempt(String password) {
	if (this.password == password) {
		return new AuthenticationToken(userType, userType.length(), userType.length());
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
