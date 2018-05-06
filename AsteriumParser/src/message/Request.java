package message;

import java.util.UUID;

import org.json.JSONObject;

import actiondata.ActionData;

/**
 * {@link Request}s are {@link Message}s sent in order to request a specific action.
 * 
 * @author Studio Toozo
 */
public class Request extends Message {
	
	
	/**
	 * Creates and returns a new {@link Message} with the given {@link ActionData}.
	 * @param actionData The {@link ActionData} associated with the {@link Message}. 
	 */
	public Request(final ActionData actionData, final String authToken) {
		this(actionData, generateMessageID(), authToken);
	}
	
	/**
	 * Creates and returns a new {@link Message} with the given {@link ActionData} and {@link UUID}.
	 * @param actionData The {@link ActionData} associated with the {@link Message}. 
	 * @param messageID	The identifier for a {@link Message} for Responses to respond to the correct Request.
	 */
	public Request(final ActionData actionData, final UUID messageID, final String authToken) {
		super(Message.MessageType.REQUEST, actionData, messageID, authToken);
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
	 * @return	{@link UUID} that is the {@link Message} ID for Clients.
	 */
	private static UUID generateMessageID() {
		return UUID.randomUUID();
	}

}
