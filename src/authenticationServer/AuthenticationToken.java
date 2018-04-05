package authenticationServer;

public class AuthenticationToken {

	private String userType;
	private Integer tokenID;
	private Integer SessionID;

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getTokenID() {
		return tokenID;
	}
	public void setTokenID(Integer tokenID) {
		this.tokenID = tokenID;
	}
	public Integer getSessionID() {
		return SessionID;
	}
	public void setSessionID(Integer sessionID) {
		SessionID = sessionID;
	}
	
}
