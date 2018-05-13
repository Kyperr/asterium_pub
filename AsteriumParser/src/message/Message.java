package message;

import java.util.UUID;

import org.json.JSONObject;

import actiondata.ActionData;

/**
 * Abstract {@link Message} is a blueprint for messages sent to and from
 * {@link Client}'s and the server.
 * 
 * @author Studio Toozo
 */
public abstract class Message {

	/**
	 * {@link Message}s are categorized as either requests or responses.
	 */
	public enum MessageType {
		REQUEST("request"), RESPONSE("response");

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
	public static final String MESSAGE_ID = "message_id";
	public static final String AUTH_TOKEN = "auth_token";

	/*
	 * Request or response.
	 */
	protected final MessageType messageType;

	/*
	 * The data associated with the Message. Used for specifying JSON for individual
	 * actions.
	 */
	private final ActionData actionData;

	/*
	 * The message identifier. Used for Clients to respond to correct messages.
	 */
	protected final UUID messageID;

	protected /*final*/ String authToken;

	/**
	 * Creates and returns a new {@link Message} with given type and data.
	 * 
	 * @param messageType
	 *            Request or response {@link Message} type.
	 * @param actionData
	 *            The {@link ActionData} associated with the {@link Message}.
	 * @param messageID
	 *            The identifier for a {@link Message} for Responses to respond to
	 *            the correct Request.
	 */
	protected Message(final MessageType messageType, final ActionData actionData, final UUID messageID,
			final String authToken) {
		this.messageType = messageType;
		this.actionData = actionData;
		this.messageID = messageID;
		this.authToken = authToken;
	}

	/**
	 * @return The {@link Message}'s {@link ActionData}.
	 */
	public ActionData getActionData() {
		return this.actionData;
	}

	/**
	 * @return The {@link Message} identifier.
	 */
	public UUID getMessageID() {
		return this.messageID;
	}

	public String getAuthToken() {
		return this.authToken;
	}

	@Deprecated
	/**
	 * This is deprecated because it needs to be removed. It is here because we need
	 * some way to inject an auth token to this "immutable" object. It should be
	 * removed once we add protocol for the requesting authToken.
	 * 
	 * @param authToken
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * Generates a {@link JSONObject} representation of the {@link Message} which
	 * can be sent. Cannot be overridden.
	 * 
	 * @return the {@link JSONObject} representation of the {@link Message}.
	 */
	public final JSONObject jsonify() {
		JSONObject thisJO = new JSONObject();

		JSONObject inner = new JSONObject();

		// Add extra fields
		addToJSONify(inner);
		inner.put(AUTH_TOKEN, this.authToken);

		// Add action name
		inner.put(ACTION_NAME, this.actionData.getName());

		// Add action data
		inner.put(this.actionData.getName(), this.actionData.jsonify());

		thisJO.put(this.messageType.getJSONTag(), inner);

		inner.put(MESSAGE_ID, this.messageID);

		return thisJO;
	}

	public boolean isResponse() {
		return this.messageType.equals(Message.MessageType.RESPONSE);
	}

	/**
	 * @return A String representation of this message in JSON format.
	 */
	public String toString() {
		// Could be changed if we want a String that's not JSON.
		return this.jsonify().toString();
	}

	/**
	 * Allows children to add additional information to their {@link JSONObject}
	 * representation.
	 * 
	 * @param jsonObject
	 *            The {@link JSONObject} to add to JSONify.
	 */
	protected abstract void addToJSONify(JSONObject jsonObject);
}
