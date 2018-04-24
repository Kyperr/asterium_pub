package message;

import org.json.JSONObject;

import actiondata.ActionData;

public abstract class Message {
	
	public enum MessageType {
		REQUEST("request"),
		RESPONSE("response");
		
		private String jsonTag;
		
		MessageType(String jsonTag) {
			this.jsonTag = jsonTag;
		}
		
		public String getJSONTag() {
			return this.jsonTag;
		}
		
	}
	
	/* Constants for all messages */
	public static final String ACTION_NAME = "action_name";
	
	protected final MessageType messageType;
	
	private final ActionData actionData;	
	
	protected Message(MessageType messageType, final ActionData actionData) {
		this.messageType = messageType;
		this.actionData = actionData;
	}
	
	public ActionData getActionData() {
		return this.actionData;
	}
	
	public final JSONObject jsonify() {
		JSONObject thisJO = new JSONObject();
		
		JSONObject inner = new JSONObject();
				
		//Add action name
		inner.put(ACTION_NAME, this.actionData.getName());

		//Add action data
		inner.put(this.actionData.getName(), this.actionData.jsonify());
		
		thisJO.put(this.messageType.getJSONTag(), inner);
		
		return thisJO;
	}
	
	protected abstract void addToJSONify(JSONObject jsonObject);
	
}
