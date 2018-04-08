package authenticationServer;

 public class AuthenticationToken {

	private String userType;
	private String tokenID;
	private Integer SessionID;
	public AuthenticationToken(String userType, String tokenID, Integer SessionID) {
		this.userType = userType;
		this.tokenID = tokenID;
		this.SessionID = SessionID;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getTokenID() {
		return tokenID;
	}
	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}
	public Integer getSessionID() {
		return SessionID;
	}
	public void setSessionID(Integer sessionID) {
		SessionID = sessionID;
	}
	
}
