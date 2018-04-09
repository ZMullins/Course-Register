//The basic transaction class all others inherit from
//All transactions require a token and a type of transaction (declared statically)

package transaction;

import authenticationServer.AuthenticationToken;

public abstract class Transaction {
	AuthenticationToken token;
	protected static String type;
	public AuthenticationToken getToken() {
		return token;
	}
	public void setToken(AuthenticationToken token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
}
