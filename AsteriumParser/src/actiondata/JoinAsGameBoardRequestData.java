package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link JoinAsGameBoardRequestData} is the representation of data
 * to be used in a {@link Request} from a game board to join a game.
 * 
 * @author Studio Toozo
 */
public class JoinAsGameBoardRequestData extends AbstractRequestActionData {
	private final String lobbyID;
	private final JoinAsGameBoardRequestData.GameBoardData gameBoardData;

	public JoinAsGameBoardRequestData(final String lobbyID, final JoinAsGameBoardRequestData.GameBoardData gameBoardData) {
		super(ActionData.JOIN_AS_GAMEBOARD);
		this.lobbyID = lobbyID;
		this.gameBoardData = gameBoardData;
	}

	@Override
	public JSONObject jsonify() {

		JSONObject data = new JSONObject();

		data.put(ActionData.LOBBY_ID, this.lobbyID);

		data.put(ActionData.GAMEBOARD_DATA, this.gameBoardData.jsonify());

		return data;
	}

	public String getLobbyID() {
		return this.lobbyID;
	}
	
	public JoinAsGameBoardRequestData.GameBoardData getGameBoardData() {
		return this.gameBoardData;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link JoinAsGameBoardRequestData} object.
	 * 
	 * @param jsonObj - the {@link JSONObject} to be parsed
	 * @return	the {@link JoinAsGameBoardRequestData} object parsed from JSON
	 * @throws JSONException
	 */
	public static JoinAsGameBoardRequestData parseArgs(final JSONObject jsonObj) throws JSONException { 
		
		// Getting lobby id
		String lobbyID = jsonObj.getString(ActionData.LOBBY_ID); 
		
		// Get JSONObject representing gameBoardData. This is used 
		// for retrieving data to be used in GameBoardData constructor.
		//JSONObject gameBoardDataObject = jsonObj.getJSONObject(ActionData.GAMEBOARD_DATA);
		
		/* Create GameBoard object */
		JoinAsGameBoardRequestData.GameBoardData gameBoardData = new GameBoardData();
		
		//Construct and return
		return new JoinAsGameBoardRequestData(lobbyID, gameBoardData);

	}

	/**
	 * {@link JoinAsGameBoardRequestData.GameBoardData} is the representation 
	 * of a {@link GameBoard} used only for the purposes of joining a lobby.
	 * 
	 * Mostly hijacked from {@link JoinAsPlayerRequestData.PlayerData}.
	 * 
	 * @author Studio Toozo
	 */
	public static class GameBoardData {
		public GameBoardData() {
		}

		/**
		 * Converts the GameBoardData into a JSONObject.
		 * 
		 * @return JSONObject representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			return data;
		}
		
		public boolean equals(final Object other) {
			if (other instanceof GameBoardData) {
				//GameBoardData otherGameBoardData = (GameBoardData) other;
				// Note: Returns true because there are no other fields currently.
				// When new fields are added, what it returns should be based on otherGameBoardData's fields.
				return true;
			} else {
				return false;
			}
		}
	}

}
