package main;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import general.AbstractAction;
import general.CreateGameAction;
import general.JoinLobbyRequestAction;
import general.Message;

public class Parser {

	/* Map of action names to action functions */
	Map<List<Object>, Function<Map<String, Object>, AbstractAction>> actionsMap;
	Gson gson;
	
	public Parser() {
		gson = new Gson();
		actionsMap = new HashMap<List<Object>, Function<Map<String, Object>, AbstractAction>>();
		addAction(Arrays.asList(true, "create_game"), CreateGameAction::new);
		addAction(Arrays.asList(true, "join_lobby"), JoinLobbyRequestAction::new);
	}

	public void addAction(final List<Object> actionID, final Function<Map<String, Object>, AbstractAction> function) {
		actionsMap.put(actionID, function);
	}
	
	public AbstractAction parse(final String msg) {
		AbstractAction action;
		Boolean isRequest;
		String actionName;
		Map<String, Object> jsonArgs;
		Message json;
		
		//parse json using gson to assign fields
		Type type = new TypeToken<Message>(){}.getType();
		json = gson.fromJson(msg, type);
		isRequest = json.isRequest();
		actionName = json.getActionName();
		jsonArgs = json.getArgs();
		
		List<Object> key = new ArrayList<Object>();
		key.add(isRequest);
		key.add(actionName);
		Function<Map<String, Object>, AbstractAction> func = actionsMap.get(key);
		action =  func.apply(jsonArgs);
		
		return action;
	}

}
