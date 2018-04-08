package transaction;

import authenticationServer.AuthenticationToken;

public abstract class Transaction {
	AuthenticationToken token;
	String type;
	public AuthenticationToken getToken() {
		return token;
	}
	public void setToken(AuthenticationToken token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
