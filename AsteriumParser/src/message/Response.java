package message;

import org.json.JSONObject;

import actiondata.ActionData;

/**
 * {@link Response}s are {@link Message}s sent in order confirm success, indicate failure, 
 * or return information that has been requested.
 */
public class Response  extends Message {
	
	/*
	 * Indicates success or failure of an action that was requested.
	 */
	private Integer errorCode;
	
	/**
	 * Creates and returns a {@link Response} with given {@link ActionData} and error code.
	 * @param actionData
	 * @param errorCode
	 */
	public Response(ActionData actionData, Integer errorCode) {
		super(Message.MessageType.RESPONSE, actionData);
		this.errorCode = errorCode;
	}

	/**
	 * Add a {@link JSONObject} to the {@link Response}'s JSONify method.
	 * (Add JSON to the {@link Response}'s JSON representation.)
	 */
	@Override
	protected void addToJSONify(JSONObject jsonObject) {
		jsonObject.put("error_code", this.errorCode.toString());
	}

}