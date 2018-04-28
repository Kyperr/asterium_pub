package message;

import org.json.JSONObject;

import actiondata.ActionData;

/**
 * Abstract {@link Message} is a blueprint for messages sent to and from {@link Client}s and the server.
 */
public abstract class Message {
	
	/**
	 * {@link Message}s are categorized as either requests or responses.
	 */
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
	
	/* 
	 * Constants for all messages 
	 */
	public static final String ACTION_NAME = "action_name";
	
	/*
	 * Request or response.
	 */
	protected final MessageType messageType;
	
	/*
	 * The data associated with the Message. Used for specifying JSON for individual actions.
	 */
	private final ActionData actionData;	
	
	/**
	 * Creates and returns a new {@link Message} with given type and data.
	 * 
	 * @param messageType Request or response {@link Message} type.
	 * @param actionData The {@link ActionData} associated with the {@link Message}. 
	 */
	protected Message(MessageType messageType, final ActionData actionData) {
		this.messageType = messageType;
		this.actionData = actionData;
	}
	
	/**
	 * @return The {@link Message}'s {@link ActionData}.
	 */
	public ActionData getActionData() {
		return this.actionData;
	}
	
	/**
	 * Generates a {@link JSONObject} representation of the {@link Message} which can be sent.
	 * Cannot be overridden.
	 * 
	 * @return the {@link JSONObject} representation of the {@link Message}.
	 */
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
	
	/**
	 * Allows children to add additional information to their {@link JSONObject} representation.
	 * @param jsonObject The {@link JSONObject} to add to JSONify.
	 */
	protected abstract void addToJSONify(JSONObject jsonObject);
	
}
