package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * CreateGameResponseData is the representation of data
 * to be used in a Response to the Request to create a game.
 * 
 * @author Bridgette Campbell, Jenna Hand, Daniel McBride, and Greg Schmitt
 *
 */
public class CreateGameResponseData extends AbstractActionData {

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
	 * Parses JSONObject into a {@link CreateGameResponseData} object.
	 * 
	 * @param jsonObj
	 * @return
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

	@Override
	protected boolean fieldsEqual(final Object other) {
		if (other instanceof CreateGameResponseData) {
			CreateGameResponseData data = CreateGameResponseData.class.cast(other);
			return (data.lobbyID.equals(this.lobbyID) && data.authToken.equals(this.authToken));
		} else {
			return false;
		}
		
	}
	
	
}
