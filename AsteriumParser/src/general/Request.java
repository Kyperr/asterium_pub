package general;

import java.util.Map;

public class Request extends Message{
	
	private String authToken;
	
	public Request(final String actionName, final Map<String, Object> args, final String authToken) {
		super(true, actionName, args);
		setAuthToken(authToken);
	}
	
	public void setAuthToken(final String authToken) {
		this.authToken = authToken;
	}
	
	public String getAuthToken() {
		return this.authToken;
	}
	
}
