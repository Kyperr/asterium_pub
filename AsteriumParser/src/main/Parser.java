package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.ActionData;
import actiondata.CreateGameActionData;
import actiondata.CreateGameResponseData;
import actiondata.JoinAsPlayerActionData;
import message.Message;
import message.Request;
import message.Response;

public class Parser {

	/* Map of action names to action functions */
	private static Map<List<Object>, Function<JSONObject, ActionData>> actionDataLookup = new HashMap<List<Object>, Function<JSONObject, ActionData>>(){{
		put(Arrays.asList(true, ActionData.JOIN_AS_PLAYER), JoinAsPlayerActionData::parseArgs);
		
		put(Arrays.asList(true, ActionData.CREATE_GAME), CreateGameActionData::parseArgs);
		put(Arrays.asList(false, ActionData.CREATE_GAME_RESPONSE), CreateGameResponseData::parseArgs);
	}};

	
	
	public Parser() {
	}

	public Message parse(final String msg) throws JSONException {
		
		ActionData actionData = null;
		String[] fields;
		Boolean isRequest;
		String actionName;
		
		System.out.println("Message: " + msg);
		
		JSONObject jsonObj = new JSONObject(msg);

		fields = JSONObject.getNames(jsonObj); 
		System.out.println("problem? " + fields.toString());
		if (!fields[0].equals(Message.MessageType.REQUEST.getJSONTag()) && !fields[0].equals(Message.MessageType.RESPONSE.getJSONTag())) {
			throw new JSONException("JSON malformed: " + jsonObj.toString());
		}

		isRequest = fields[0].equals(Message.MessageType.REQUEST.getJSONTag());
				
		JSONObject innerJSONObj = jsonObj.getJSONObject(fields[0]); // reassign json object to next nested object
		
		fields = JSONObject.getNames(innerJSONObj); // reassign fields to get object's keys
		
		actionName = innerJSONObj.get(Message.ACTION_NAME).toString();
		
		jsonObj = innerJSONObj.getJSONObject(actionName);
		
		System.out.println(actionDataLookup.get(Arrays.asList(isRequest, actionName)));
		actionData = actionDataLookup.get(Arrays.asList(isRequest, actionName)).apply(jsonObj);

		
		Message message;
		
		if(isRequest) {
			message = new Request(actionData);
		} else {			
			System.out.println("json: " + innerJSONObj.toString());
			Integer errorCode = innerJSONObj.getInt("error_code");			
			message = new Response(actionData, errorCode);
		}
		
		return message;
	}


}
