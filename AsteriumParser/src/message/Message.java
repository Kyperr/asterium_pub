package message;

import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import actiondata.ActionData;

public class Message {
	
	/* Constants for all messages */
	public static final String ACTION_NAME = "action_name";
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";
	
	
	protected final String messageType;
	
	private final ActionData actionData;	
	
	public Message(final String messageType, final ActionData actionData) {
		this.messageType = messageType;
		this.actionData = actionData;
	}
	
	public JSONObject jsonify() {
		JSONObject thisJO = new JSONObject();
		
		JSONObject inner = new JSONObject();
				
		//Add action name
		inner.put(ACTION_NAME, this.actionData.getName());

		//Add action data
		inner.put(this.actionData.getName(), this.actionData.jsonify());
		
		thisJO.put(this.messageType, inner);
		
		return thisJO;
	}
	
}
