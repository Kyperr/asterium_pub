package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.ActionData;
import actiondata.CreateGameRequestData;
import actiondata.JoinAsPlayerRequestData;
import message.Message;
import message.Request;
import message.Response;

public class Parser {

	/* Map of action names to action functions */
	private static Map<List<Object>, Function<JSONObject, ActionData>> actionDataLookup = new HashMap<List<Object>, Function<JSONObject, ActionData>>(){
		private static final long serialVersionUID = 1L;
	{
		put(Arrays.asList(true, ActionData.JOIN_AS_PLAYER), JoinAsPlayerRequestData::parseArgs);
		put(Arrays.asList(true, ActionData.CREATE_GAME), CreateGameRequestData::parseArgs);
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
				
		jsonObj = jsonObj.getJSONObject(fields[0]); // reassign json object to next nested object
		
		fields = JSONObject.getNames(jsonObj); // reassign fields to get object's keys
		
		actionName = jsonObj.get(Message.ACTION_NAME).toString();
		
		jsonObj = jsonObj.getJSONObject(actionName);
		
		actionData = actionDataLookup.get(Arrays.asList(isRequest, actionName)).apply(jsonObj);

		
		Message message;
		
		if(isRequest) {
			message = new Request(actionData);
		} else {			
			Integer errorCode = jsonObj.getInt("error_code");			
			message = new Response(actionData, errorCode);
		}
		
		return message;
	}


}
