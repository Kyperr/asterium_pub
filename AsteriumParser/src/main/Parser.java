package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.ActionData;
import actiondata.CommunalInventoryRequestData;
import actiondata.CreateGameRequestData;
import actiondata.CreateGameResponseData;
import actiondata.DiscardItemRequestData;
import actiondata.ItemTurnRequestData;
import actiondata.JoinAsGameBoardRequestData;
import actiondata.JoinAsGameBoardResponseData;
import actiondata.JoinAsPlayerRequestData;
import actiondata.JoinAsPlayerResponseData;
import actiondata.QueryIsInGameRequestData;
import actiondata.QueryIsInGameResponseData;
import actiondata.SetReadyStatusRequestData;
import actiondata.SyncGameBoardDataRequestData;
import actiondata.SyncPlayerListRequestData;
import actiondata.ToggleReadyUpRequestData;
import actiondata.ToggleReadyUpResponseData;
import actiondata.TurnRequestData;
import actiondata.TurnSummaryRequestData;
import actiondata.UseItemRequestData;
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

		put(Arrays.asList(true, ActionData.QUERY_IS_IN_GAME), QueryIsInGameRequestData::parseArgs);
		put(Arrays.asList(false, ActionData.QUERY_IS_IN_GAME), QueryIsInGameResponseData::parseArgs);

		put(Arrays.asList(true, ActionData.TOGGLE_READY_UP), ToggleReadyUpRequestData::parseArgs);
		put(Arrays.asList(false, ActionData.TOGGLE_READY_UP), ToggleReadyUpResponseData::parseArgs);
		
		put(Arrays.asList(true, ActionData.SET_READY_STATUS), SetReadyStatusRequestData::parseArgs);

		put(Arrays.asList(true, ActionData.TURN_ACTION), TurnRequestData::parseArgs);

		put(Arrays.asList(true, ActionData.ITEM_TURN_ACTION), ItemTurnRequestData::parseArgs);

		put(Arrays.asList(true, ActionData.SYNC_GAME_BOARD_DATA), SyncGameBoardDataRequestData::parseArgs);
		//put(Arrays.asList(false, ActionData.SYNC_GAME_BOARD_DATA), SyncGameBoardDataResponseData::parseArgs);
		
		put(Arrays.asList(true, ActionData.SYNC_PLAYER_LIST), SyncPlayerListRequestData::parseArgs);

		put(Arrays.asList(true, ActionData.USE_ITEM), UseItemRequestData::parseArgs);
		put(Arrays.asList(true, ActionData.COMMUNAL_INVENTORY), CommunalInventoryRequestData::parseArgs);
		put(Arrays.asList(true, ActionData.DISCARD_ITEM), DiscardItemRequestData::parseArgs);
		
		put(Arrays.asList(true, ActionData.SUMMARY), TurnSummaryRequestData::parseArgs);
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
		String authToken;
		
		JSONObject jsonObj = new JSONObject(msg);

		fields = JSONObject.getNames(jsonObj); 
		
		if (!fields[0].equals(Message.MessageType.REQUEST.getJSONTag()) && !fields[0].equals(Message.MessageType.RESPONSE.getJSONTag())) {
			throw new JSONException("JSON malformed: " + jsonObj.toString());
		}

		isRequest = fields[0].equals(Message.MessageType.REQUEST.getJSONTag());
				
		JSONObject innerJSONObj = jsonObj.getJSONObject(fields[0]); // reassign json object to next nested object
		
		fields = JSONObject.getNames(innerJSONObj); // reassign fields to get object's keys
		
		authToken = innerJSONObj.getString(Message.AUTH_TOKEN);
		actionName = innerJSONObj.getString(Message.ACTION_NAME);	
		messageID = UUID.fromString(innerJSONObj.getString(Message.MESSAGE_ID));
		
		jsonObj = innerJSONObj.getJSONObject(actionName);
		
		if (VERBOSE) {
			System.out.print("actionDataLookup.get(" + isRequest + ", " + actionName + ") = ");
			System.out.println(actionDataLookup.get(Arrays.asList(isRequest, actionName)));
		}

		try {
		actionData = actionDataLookup.get(Arrays.asList(isRequest, actionName)).apply(jsonObj);
		} catch(NullPointerException e) {
			System.err.println("No value found found for: {" + isRequest + ", " + actionName + "}.\n"
					+ "You probably forgot to map this actiondata.");
			e.printStackTrace();
		}
		
		Message message;
		
		if(isRequest) {
			message = new Request(actionData, messageID, authToken);
		} else {
			if (VERBOSE) {
				System.out.println("JSON Action Data: " + jsonObj);
				System.out.println("innerJSONObj: " + innerJSONObj);
			}
			
			Integer errorCode = innerJSONObj.getInt(Response.ERROR_CODE);
			message = new Response(actionData, errorCode, messageID, authToken);
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
