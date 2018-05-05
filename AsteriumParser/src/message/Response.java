package message;

import java.util.UUID;

import org.json.JSONObject;

import actiondata.ActionData;

/**
 * {@link Response}s are {@link Message}s sent in order confirm success, indicate failure, 
 * or return information that has been requested.
 */
public class Response  extends Message {
	

	public static final String ERROR_CODE = "error_code";
	
	/*
	 * Indicates success or failure of an action that was requested.
	 */
	private Integer errorCode;
	
	/**
	 * Creates and returns a {@link Response} with given {@link ActionData} and error code.
	 * @param actionData
	 * @param errorCode
	 */
	public Response(final ActionData actionData, final Integer errorCode, final UUID messageID) {
		super(Message.MessageType.RESPONSE, actionData, messageID);
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