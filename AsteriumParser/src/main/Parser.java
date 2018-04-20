package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import general.AbstractAction;
import general.CreateGameAction;
import general.JoinAsPlayerAction;

public class Parser {
	
	private static final String JSON_ERR = "JSON not formatted properly.";

	/* Map of action names to action functions */
	private Map<List<Object>, Function<Map<String, Object>, AbstractAction>> actionsMap;
	private static Map<String, Consumer<JSONObject>> actionLookup = new HashMap<String, Consumer<JSONObject>>(){{
		put("join_as_player", Parser::joinAsPlayer);
	}};
	
	public Parser() {
		actionsMap = new HashMap<List<Object>, Function<Map<String, Object>, AbstractAction>>();
		addAction(Arrays.asList(true, "create_game"), CreateGameAction::new);
		addAction(Arrays.asList(true, "join_as_player"), JoinAsPlayerAction::new);
	}

	public void addAction(final List<Object> actionID, final Function<Map<String, Object>, AbstractAction> function) {
		actionsMap.put(actionID, function);
	}
	
	public AbstractAction parse(final String msg) {
		AbstractAction action = null;
		String[] fields;
		
		Boolean isRequest = false;
		String actionName = null;
		Map<String, Object> jsonArgs = new HashMap<String, Object>();
		
        JSONObject jsonObj = new JSONObject(msg); 
        System.out.println("JSON String: " + jsonObj);
        
        fields = JSONObject.getNames(jsonObj);	//get object's keys
        if (fields != null && fields.length >= 1 
        		&& (fields[0].equals("request") || fields[0].equals("response"))) {
        	isRequest = fields[0].equals("request");
        	jsonObj = jsonObj.getJSONObject(fields[0]); //reassign json object to next nested object
        	fields = JSONObject.getNames(jsonObj); 		//reassign fields to get object's keys      
        	
        	if (jsonObj.has("action_name")) {
        		actionName = jsonObj.get("action_name").toString();
        	} else {
            	throw new JSONException(JSON_ERR);
        	}
        	
        	if (jsonObj.has(actionName)) {
        		jsonObj = jsonObj.getJSONObject(actionName);
            	
        		Consumer<JSONObject> run = actionLookup.get(actionName);
        		
        		run.accept(jsonObj);
        	} else {
            	throw new JSONException(JSON_ERR);
        	}
        	
        } else {
        	throw new JSONException(JSON_ERR);
        }
		
		List<Object> key = new ArrayList<Object>();
		key.add(isRequest);
		key.add(actionName);
		Function<Map<String, Object>, AbstractAction> func = actionsMap.get(key);
		action =  func.apply(jsonArgs);
		
		return action;
	}
	
	
	//static methods
	private static void joinAsPlayer(final JSONObject object) {
		System.out.println("Join as player args: " + object);
		
		//parse args to correct things
	}
	

}
