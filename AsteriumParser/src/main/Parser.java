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
import actiondata.JoinAsPlayerActionData;
import message.Message;

public class Parser {

	/* Map of action names to action functions */
	private static Map<List<Object>, Function<JSONObject, ActionData>> actionDataLookup = new HashMap<List<Object>, Function<JSONObject, ActionData>>(){{
		put(Arrays.asList(true, ActionData.JOIN_AS_PLAYER), JoinAsPlayerActionData::parseArgs);
		put(Arrays.asList(true, ActionData.CREATE_GAME), CreateGameActionData::parseArgs);
	}};

	
	
	public Parser() {
	}

	public ActionData parseToActionData(final String msg) throws JSONException {
		
		ActionData actionData = null;
		String[] fields;
		Boolean isRequest;
		String actionName;
		JSONObject jsonObj = new JSONObject(msg);

		fields = JSONObject.getNames(jsonObj); // get object's keys
		
		if (!fields[0].equals(Message.MessageType.REQUEST.getJSONTag()) && !fields[0].equals(Message.MessageType.RESPONSE.getJSONTag())) {
			throw new JSONException("JSON malformed: " + jsonObj.toString());
		}

		isRequest = fields[0].equals(Message.MessageType.REQUEST.getJSONTag());
		
		jsonObj = jsonObj.getJSONObject(fields[0]); // reassign json object to next nested object
		
		fields = JSONObject.getNames(jsonObj); // reassign fields to get object's keys
		
		actionName = jsonObj.get(Message.ACTION_NAME).toString();
		
		jsonObj = jsonObj.getJSONObject(actionName);
		
		actionData = actionDataLookup.get(Arrays.asList(isRequest, actionName)).apply(jsonObj);

		return actionData;
	}


}
