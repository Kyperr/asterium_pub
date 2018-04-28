package actiondata;

import org.json.JSONException;
import org.json.JSONObject;


public class JoinAsPlayerActionData extends AbstractActionData {

	private final String lobbyID;
	private final PlayerData playerData;

	public JoinAsPlayerActionData(String lobbyID, PlayerData playerData) {
		super(JOIN_AS_PLAYER);
		this.lobbyID = lobbyID;
		this.playerData = playerData;
	}

	@Override
	public JSONObject jsonify() {

		JSONObject data = new JSONObject();

		data.put("lobby_id", this.lobbyID);

		data.put("player_data", this.playerData.jsonify());

		return data;
	}

	public String getLobbyID() {
		return this.lobbyID;
	}
	
	public PlayerData getPlayerData() {
		return this.playerData;
	}
	
	/**
	 * Parses JSONObject into a {@link JoinAsPlayerActionData} object.
	 * 
	 * @param jsonObj	the JSONObject to be parsed
	 * @return	the JoinAsPlayerActionData object parsed from JSON
	 * @throws JSONException
	 */
	public static JoinAsPlayerActionData parseArgs(JSONObject jsonObj) throws JSONException { 
		
		//Getting lobby id
		String lobbyID = jsonObj.getString(LOBBY_ID); 
		
		//Get Player Data object
		JSONObject playerData = jsonObj.getJSONObject(PLAYER_DATA);
		
		//Get player data name
		String playerDataName = playerData.getString(NAME); 
		
		/* Create Player object */
		PlayerData pData = new PlayerData(playerDataName);
		
		//Construct and return
		return new JoinAsPlayerActionData(lobbyID, pData);

	}

	public static class PlayerData {

		private final String name;

		public PlayerData(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put("name", this.name);
			return data;
		}
		
		public boolean equals(final Object other) {
			if (other instanceof PlayerData) {
				PlayerData otherPlayerData = (PlayerData) other;
				return otherPlayerData.name.equals(this.name);
			} else {
				return false;
			}
		}
	}

	@Override
	protected boolean fieldsEqual(final Object other) {
		if (other instanceof JoinAsPlayerActionData) {
			JoinAsPlayerActionData otherData = (JoinAsPlayerActionData) other;
			return (otherData.lobbyID.equals(this.lobbyID) && 
				   otherData.playerData.equals(this.playerData));
		} else {
			return false;
		}
	}

}
