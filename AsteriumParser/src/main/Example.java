package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONObject;

import action.AbstractAction;
import action.CreateGameAction;
import action.JoinAsPlayerAction;

public class Example {

	public static final String JSON_FORMAT_ERR = "JSON not formatted properly.";
	public static final String JSON_EMPTY_ERR = "Empty field %s.";
	
	public static final Runnable CONST_ERROR_ACTION = new Runnable() {
		
		@Override
		public void run() {
			System.out.println("Fuck.");
		}
	};

	public static void main(String[] args) {
		 Map<List<Object>, Function<JSONObject, Runnable>> actionLookup = new HashMap<List<Object>, Function<JSONObject, Runnable>>(){
			private static final long serialVersionUID = 1L;
			{
				put(Arrays.asList(true, AbstractAction.JOIN_AS_PLAYER), JoinAsPlayerAction::parseArgs);
				put(Arrays.asList(true, AbstractAction.CREATE_GAME), CreateGameAction::parseArgs);
			}
		};
		
		Parser parser = new Parser(actionLookup, CONST_ERROR_ACTION);
		
		String msg = "{\"request\":{\"auth_token\":\"12345\",\"action_name\":\"join_as_player\",\"join_as_player\":{\"lobby_id\":\"Fuck Yeah\",\"player_data\":{\"name\":\"Lieutenant Dudefella\"}}}}";
		
		Runnable a = parser.parse(msg);
		a.run();

	}
	
	

}
