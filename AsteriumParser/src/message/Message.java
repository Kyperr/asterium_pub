package message;

import java.util.UUID;

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
	public static final int ID_LENGTH = 64;
	public static final String MESSAGE_ID = "message_id";
	
	/*
	 * Request or response.
	 */
	protected final MessageType messageType;
	
	/*
	 * The data associated with the Message. Used for specifying JSON for individual actions.
	 */
	private final ActionData actionData;	
	
	/*
	 * The message identifier. Used for Clients to respond to correct messages. 
	 */
	protected final UUID messageID;
	
	/**
	 * Creates and returns a new {@link Message} with given type and data.
	 * 
	 * @param messageType Request or response {@link Message} type.
	 * @param actionData The {@link ActionData} associated with the {@link Message}. 
	 */
	protected Message(final MessageType messageType, final ActionData actionData, final UUID messageID) {
		this.messageType = messageType;
		this.actionData = actionData;
		this.messageID = messageID;
	}
	
	/**
	 * @return The {@link Message}'s {@link ActionData}.
	 */
	public ActionData getActionData() {
		return this.actionData;
	}

	/**
	 * 
	 * @return The {@link Message} identifier.
	 */
	public UUID getMessageID() {
		return this.messageID;
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
		
		//Add extra fields
		addToJSONify(inner);
		
		//Add action name
		inner.put(ACTION_NAME, this.actionData.getName());

		//Add action data
		inner.put(this.actionData.getName(), this.actionData.jsonify());
		
		thisJO.put(this.messageType.getJSONTag(), inner);
		
		inner.put(MESSAGE_ID, this.messageID);
		
		return thisJO;
	}
	
	/**
	 * Allows children to add additional information to their {@link JSONObject} representation.
	 * @param jsonObject The {@link JSONObject} to add to JSONify.
	 */
	protected abstract void addToJSONify(JSONObject jsonObject);
	
}
