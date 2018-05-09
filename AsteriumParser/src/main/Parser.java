package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.ActionData;
import actiondata.CreateGameRequestData;
import actiondata.CreateGameResponseData;
import actiondata.JoinAsGameBoardRequestData;
import actiondata.JoinAsGameBoardResponseData;
import actiondata.JoinAsPlayerRequestData;
import actiondata.JoinAsPlayerResponseData;
import message.Message;
import message.Request;
import message.Response;

/**
 * {@link Parser} changes a {@link JSONObject} into a {@link Message} with appropriate {@link ActionData}. 
 * 
 * @author Studio Toozo
 */
public class Parser {
	public static final boolean VERBOSE = false;
	
	/* Map of action names to action functions */
	private static HashMap<List<Object>, Function<JSONObject, ActionData>> actionDataLookup = new HashMap<List<Object>, Function<JSONObject, ActionData>>(){
		private static final long serialVersionUID = 1L;
	{
		put(Arrays.asList(true, ActionData.CREATE_GAME), CreateGameRequestData::parseArgs);
		put(Arrays.asList(false, ActionData.CREATE_GAME), CreateGameResponseData::parseArgs);
		
		put(Arrays.asList(true, ActionData.JOIN_AS_PLAYER), JoinAsPlayerRequestData::parseArgs);
		put(Arrays.asList(false, ActionData.JOIN_AS_PLAYER), JoinAsPlayerResponseData::parseArgs);
		
		put(Arrays.asList(true, ActionData.JOIN_AS_GAMEBOARD), JoinAsGameBoardRequestData::parseArgs);
		put(Arrays.asList(false, ActionData.JOIN_AS_GAMEBOARD), JoinAsGameBoardResponseData::parseArgs);
	}};

	/**
	 * Turn a JSON string into a {@link Message}.
	 * 
	 * @param msg	the JSON message to be parsed
	 * @return	a {@link Message} representation of the JSON sent.
	 * @throws JSONException
	 */
	public Message parse(final String msg) throws JSONException {
		
		ActionData actionData = null;
		String[] fields;
		Boolean isRequest;
		String actionName;
		UUID messageID;
		
		JSONObject jsonObj = new JSONObject(msg);

		fields = JSONObject.getNames(jsonObj); 
		
		if (!fields[0].equals(Message.MessageType.REQUEST.getJSONTag()) && !fields[0].equals(Message.MessageType.RESPONSE.getJSONTag())) {
			throw new JSONException("JSON malformed: " + jsonObj.toString());
		}

		isRequest = fields[0].equals(Message.MessageType.REQUEST.getJSONTag());
				
		JSONObject innerJSONObj = jsonObj.getJSONObject(fields[0]); // reassign json object to next nested object
		
		fields = JSONObject.getNames(innerJSONObj); // reassign fields to get object's keys
		
		actionName = innerJSONObj.get(Message.ACTION_NAME).toString();	
		messageID = UUID.fromString(innerJSONObj.getString(Message.MESSAGE_ID));
		
		jsonObj = innerJSONObj.getJSONObject(actionName);
		
		if (VERBOSE) {
			System.out.print("actionDataLookup.get(" + isRequest + ", " + actionName + ") = ");
			System.out.println(actionDataLookup.get(Arrays.asList(isRequest, actionName)));
		}
		actionData = actionDataLookup.get(Arrays.asList(isRequest, actionName)).apply(jsonObj);

		
		Message message;
		
		if(isRequest) {
			message = new Request(actionData, messageID);
		} else {
			if (VERBOSE) {
				System.out.println("JSON Action Data: " + jsonObj);
				System.out.println("innerJSONObj: " + innerJSONObj);
			}
			
			Integer errorCode = innerJSONObj.getInt(Response.ERROR_CODE);
			message = new Response(actionData, errorCode, messageID);
		}
		
		return message;
	}

	/**
	 * Gets the message ID of message.
	 * 
	 * @param message - The JSON string to be parsed.
	 * @return The message ID (UUID) of message.
	 */
	public UUID getMessageID(final String message) {
		return this.parse(message).getMessageID();
	}

}
