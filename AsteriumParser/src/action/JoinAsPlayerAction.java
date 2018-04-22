package action;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import general.PlayerData;
import main.Example;

public class JoinAsPlayerAction extends AbstractAction {

	/* Argument names */
	public static final String LOBBY_ID = "lobby_id";
	public static final String PLAYER_DATA = "player_data";

	public JoinAsPlayerAction(Map<String, Object> args) {
		super(AbstractAction.JOIN_AS_PLAYER, args);
	}

	@Override
	public void run() {
		PlayerData p = (PlayerData) this.getArg(PLAYER_DATA);
		System.out.println("Player " + p.getName() + " has joined lobby " + this.getArg("lobby_id"));
	}

	// static parsing methods for parsing action specific arguments
	public static JoinAsPlayerAction parseArgs(JSONObject jsonObj) {
		String lobby_id;
		PlayerData player;
		String name;
		JoinAsPlayerAction action;
		Map<String, Object> map = new HashMap<String, Object>();

		// parse args to correct places
		lobby_id = jsonObj.get(JoinAsPlayerAction.LOBBY_ID).toString();
		jsonObj = jsonObj.getJSONObject(JoinAsPlayerAction.PLAYER_DATA);
		name = jsonObj.get(PlayerData.NAME).toString();

		/* Create Player object */
		if (!name.isEmpty()) {
			player = new PlayerData(name);

			map.put(JoinAsPlayerAction.LOBBY_ID, lobby_id);
			map.put(JoinAsPlayerAction.PLAYER_DATA, player);

			action = new JoinAsPlayerAction(map);

		} else {
			throw new JSONException(String.format(Example.JSON_EMPTY_ERR, PlayerData.NAME));
		}

		return action;

	}

}
