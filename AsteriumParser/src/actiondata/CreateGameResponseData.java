package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link CreateGameResponseData} is the representation of data
 * to be used in a {@link Response} to the {@link Request} to create a game.
 * 
 * @author Studio Toozo
 *
 */
public class CreateGameResponseData extends AbstractResponseActionData {

	private final String lobbyID;
	private final String authToken;
	
	public CreateGameResponseData(final String lobbyID, final String authToken) {
		super(CREATE_GAME_RESPONSE);
		this.lobbyID = lobbyID;
		this.authToken = authToken;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();

		data.put(LOBBY_ID, this.lobbyID);
		
		data.put(AUTH_TOKEN, this.authToken);
		
		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link CreateGameResponseData} object.
	 * 
	 * @param jsonObj	the {@link JSONObject} to be parsed
	 * @return the {@link CreateGameResponseData} parsed from JSON
	 * @throws JSONException
	 */
	public static CreateGameResponseData parseArgs(final JSONObject jsonObj) throws JSONException {
		
		//Get lobbyID
		String lobbyID = jsonObj.getString(LOBBY_ID); 
		
		//Get authToken
		String authToken = jsonObj.getString(AUTH_TOKEN);
		
		//Construct and return
		return new CreateGameResponseData(lobbyID, authToken);

	}	
	
}
