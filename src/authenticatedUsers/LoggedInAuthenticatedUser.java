package authenticatedUsers;

import authenticationServer.AuthenticationToken;
import systemUsers.SystemUser;

public interface LoggedInAuthenticatedUser extends SystemUser {

	void setAuthenticationToken(AuthenticationToken authenticationToken);
	AuthenticationToken getAuthenticationToken();
	
}
