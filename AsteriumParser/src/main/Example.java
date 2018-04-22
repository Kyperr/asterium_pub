package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONObject;

import actiondata.ActionData;
import actiondata.CreateGameActionData;
import actiondata.JoinAsPlayerActionData;
import actiondata.JoinAsPlayerActionData.PlayerData;
import message.Message;

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
		 Map<List<Object>, Function<JSONObject, ActionData>> actionLookup = new HashMap<List<Object>, Function<JSONObject, ActionData>>(){
			private static final long serialVersionUID = 1L;
			{
				put(Arrays.asList(true, ActionData.JOIN_AS_PLAYER), JoinAsPlayerActionData::parseArgs);
				put(Arrays.asList(true, ActionData.CREATE_GAME), CreateGameActionData::parseArgs);
			}
		};
		
		Parser parser = new Parser(actionLookup);
		
		//String msg = "{\"request\":{\"auth_token\":\"12345\",\"action_name\":\"join_as_player\",\"join_as_player\":{\"lobby_id\":\"Fuck Yeah\",\"player_data\":{\"name\":\"Lieutenant Dudefella\"}}}}";
		
		//ActionData ad = parser.parseToActionData(msg);
		
		JoinAsPlayerActionData.PlayerData pd = new PlayerData("Daniel");
		ActionData joinAsPlayer = new JoinAsPlayerActionData("abcd", pd);
		
		Message message = new Message(Message.REQUEST, joinAsPlayer);
		
		//Request r = new Request("join_as_player", joinAsPlayer.jsonify);
		
		System.out.println(message.jsonify().toString());

	}
	
	

}
