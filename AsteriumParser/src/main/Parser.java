package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import general.AbstractAction;
import general.CreateGameAction;
import general.JoinAsPlayerAction;
import general.Player;

public class Parser {

	private static final String JSON_FORMAT_ERR = "JSON not formatted properly.";
	private static final String JSON_EMPTY_ERR = "Empty field %s.";

	/* Map of action names to action functions */
	private static Map<List<Object>, Function<JSONObject, Runnable>> actionLookup = new HashMap<List<Object>, Function<JSONObject, Runnable>>() {
		private static final long serialVersionUID = 1L;
		{
			put(Arrays.asList(true, AbstractAction.JOIN_AS_PLAYER), Parser::joinAsPlayer);
			put(Arrays.asList(true, AbstractAction.CREATE_GAME), Parser::createGame);
		}
	};

	public Parser() {
		
	}

	public Runnable parse(final String msg) {
		Runnable action = null;
		String[] fields;

		Boolean isRequest = false;
		String actionName = null;

		JSONObject jsonObj = new JSONObject(msg);

		fields = JSONObject.getNames(jsonObj); // get object's keys
		if (fields != null && fields.length >= 1 && (fields[0].equals(AbstractAction.REQUEST) || fields[0].equals(AbstractAction.RESPONSE))) {
			isRequest = fields[0].equals(AbstractAction.REQUEST);
			jsonObj = jsonObj.getJSONObject(fields[0]); // reassign json object to next nested object
			fields = JSONObject.getNames(jsonObj); // reassign fields to get object's keys

			if (jsonObj.has(AbstractAction.ACTION_NAME)) {
				actionName = jsonObj.get(AbstractAction.ACTION_NAME).toString();
			} else {
				throw new JSONException(JSON_FORMAT_ERR);
			}

			if (jsonObj.has(actionName)) {
				jsonObj = jsonObj.getJSONObject(actionName);

				action = actionLookup.get(Arrays.asList(isRequest, actionName)).apply(jsonObj);
			} else {
				throw new JSONException(JSON_FORMAT_ERR);
			}

		} else {
			throw new JSONException(JSON_FORMAT_ERR);
		}

		return action;
	}

	// static parsing methods for parsing action specific arguments
	private static JoinAsPlayerAction joinAsPlayer(JSONObject jsonObj) {
		String[] fields;
		String lobby_id;
		Player player;
		String name;
		JoinAsPlayerAction action;
		Map<String, Object> map = new HashMap<String, Object>();
		// parse args to correct things

		fields = JSONObject.getNames(jsonObj); // get object's keys
		if (fields != null && fields.length >= 1) {
			if (jsonObj.has(JoinAsPlayerAction.LOBBY_ID)) {
				lobby_id = jsonObj.get(JoinAsPlayerAction.LOBBY_ID).toString();
			} else {
				throw new JSONException(JSON_FORMAT_ERR);
			}

			if (jsonObj.has(JoinAsPlayerAction.PLAYER)) {
				jsonObj = jsonObj.getJSONObject(JoinAsPlayerAction.PLAYER);
				fields = JSONObject.getNames(jsonObj); // get object's keys
				
				if (jsonObj.has(Player.NAME)) {
					name = jsonObj.get(Player.NAME).toString();
				} else {
					throw new JSONException(JSON_FORMAT_ERR);
				}
				
				
				/* Create Player object  */
				if (!name.isEmpty()) {
					player = new Player(name);

					map.put(JoinAsPlayerAction.LOBBY_ID, lobby_id);
					map.put(JoinAsPlayerAction.PLAYER, player);
					
					action = new JoinAsPlayerAction(map);
					
				} else {
					throw new JSONException(String.format(JSON_EMPTY_ERR, Player.NAME));
				}
				
			} else {
				throw new JSONException(JSON_FORMAT_ERR);
			}

		} else {
			throw new JSONException(JSON_FORMAT_ERR);
		}
		
		return action;

	}
	
	private static CreateGameAction createGame(JSONObject jsonObj) {
		return null;
	}

}
