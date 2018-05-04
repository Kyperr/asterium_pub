package message;

import java.util.UUID;

import org.json.JSONObject;

import actiondata.ActionData;

/**
 * {@link Request}s are {@link Message}s sent in order to request a specific action.
 */
public class Request extends Message {
	
	
	/**
	 * Creates and returns a new {@link Message} with the given {@link ActionData}.
	 * @param actionData
	 */
	public Request(final ActionData actionData) {
		this(actionData, generateMessageID());
	}
	
	/**
	 * Creates and returns a new {@link Message} with the given {@link ActionData} and {@link UUID}.
	 * @param actionData
	 * @param messageID 
	 */
	private Request(final ActionData actionData, final UUID messageID) {
		super(Message.MessageType.REQUEST, actionData, messageID);
	}

	/**
	 * Add a {@link JSONObject} to the {@link Request}'s JSONify method.
	 * (Add JSON to the {@link Request}'s JSON representation.)
	 */
	@Override
	protected void addToJSONify(JSONObject jo) {
		
	}
	
	/**
	 * 
	 * @return	Random String that is the {@link Message} ID for Clients.
	 */
	private static UUID generateMessageID() {
		return UUID.randomUUID();
	}

}
