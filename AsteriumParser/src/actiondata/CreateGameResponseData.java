package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateGameResponseData extends AbstractActionData {

	private final String lobbyID;
	private final String authToken;
	
	public CreateGameResponseData(String lobbyID, String authToken) {
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
	 * Parses JSONObject into a {@link CreateGameResponseData} object.
	 * 
	 * @param jsonObj
	 * @return
	 * @throws JSONException
	 */
	public static CreateGameResponseData parseArgs(JSONObject jsonObj) throws JSONException {
		
		//Get lobbyID
		String lobbyID = jsonObj.getString(LOBBY_ID); 
		
		//Get authToken
		String authToken = jsonObj.getString(AUTH_TOKEN);
		
		//Construct and return
		return new CreateGameResponseData(lobbyID, authToken);

	}

	@Override
	protected boolean fieldsEqual(Object other) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
