package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.ActionData;
import message.Message;

public class Parser {

	/* Map of action names to action functions */
	private Map<List<Object>, Function<JSONObject, ActionData>> actionDataLookup = new HashMap<List<Object>, Function<JSONObject, ActionData>>();

	public Parser(final Map<List<Object>, Function<JSONObject, ActionData>> actions) {
		setActionMap(actions);
	}

	public void setActionMap(final Map<List<Object>, Function<JSONObject, ActionData>> actions) {
		this.actionDataLookup = actions;
	}

	public ActionData parseToActionData(final String msg) throws JSONException {
		ActionData actionData = null;
		String[] fields;
		Boolean isRequest;
		String actionName;
		JSONObject jsonObj = new JSONObject(msg);

		fields = JSONObject.getNames(jsonObj); // get object's keys
		
		if (!fields[0].equals(Message.REQUEST) && !fields[0].equals(Message.RESPONSE)) {
			throw new JSONException("JSON malformed: " + jsonObj.toString());
		}

		isRequest = fields[0].equals(Message.REQUEST);
		
		jsonObj = jsonObj.getJSONObject(fields[0]); // reassign json object to next nested object
		
		fields = JSONObject.getNames(jsonObj); // reassign fields to get object's keys
		
		actionName = jsonObj.get(Message.ACTION_NAME).toString();
		
		jsonObj = jsonObj.getJSONObject(actionName);
		
		actionData = actionDataLookup.get(Arrays.asList(isRequest, actionName)).apply(jsonObj);

		return actionData;
	}

}
