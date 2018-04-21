package message;

import java.util.Map;

public abstract class Message {
	
	/* Constants for all messages */
	public static final String ACTION_NAME = "action_name";
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";
	
	private Boolean isRequest; 
	private String actionName;
	private Map<String, Object> args;
	
	public Message(final Boolean isRequest, final String actionName, final Map<String, Object> args) {
		setIsRequest(isRequest);
		setActionName(actionName);
		setArgs(args);
	}
	
	public Boolean getIsRequest() {
		return this.isRequest;
	}
	
	public void setIsRequest(final Boolean isRequest) {
		this.isRequest = isRequest;
	}
	
	public String getActionName() {
		return this.actionName;
	}
	
	public void setActionName(final String actionName) {
		this.actionName = actionName;
	}
	
	public Map<String, Object> getArgs() {
		return this.args;
	}
	
	public void setArgs(final Map<String, Object> args) {
		this.args = args;
	}
}
