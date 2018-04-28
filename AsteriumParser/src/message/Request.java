package message;

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
	public Request(ActionData actionData) {
		super(Message.MessageType.REQUEST, actionData);
	}

	/**
	 * Add a {@link JSONObject} to the {@link Request}'s JSONify method.
	 * (Add JSON to the {@link Request}'s JSON representation.)
	 */
	@Override
	protected void addToJSONify(JSONObject jo) {
		
	}

}
