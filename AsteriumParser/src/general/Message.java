package general;

import java.util.Map;

public abstract class Message {
	protected Boolean isRequest;
	protected String actionName;
	protected Map<String, Object> argsMap;
	
	protected Message(final Boolean isRequest, final String actionName, final Map<String, Object> argsList){
		setIsRequest(isRequest);
		setActionName(actionName);
		setArgs(argsList);
	}
	
	public Boolean isRequest() {
		return this.isRequest;
	}
	
	public String getActionName() {
		return this.actionName;
	}
	
	public Map<String, Object> getArgs() {
		return this.argsMap;
	}
	
	public void setIsRequest(final Boolean isRequest) {
		this.isRequest = isRequest;
	}
	
	public void setActionName(final String actionName) {
		this.actionName = actionName;
	}
	
	public void setArgs(final Map<String, Object> argsList) {
		this.argsMap = argsList;
	}
	
}
