package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link JoinAsGameBoardResponseData} is the representation of data
 * to be used in a {@link Response} to the {@link Request} to join a lobby
 * as a game board.
 * 
 * @author Studio Toozo
 *
 */
public class JoinAsGameBoardResponseData extends AbstractResponseActionData {
	
	private final String authToken;

	public JoinAsGameBoardResponseData(final String authToken) {
		super(ActionData.JOIN_AS_GAMEBOARD);
		this.authToken = authToken;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		
		data.put(AUTH_TOKEN, this.authToken);
		
		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link JoinAsGameBoardResponseData} object.
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return	the {@link JoinAsGameBoardResponseData} object parsed from JSON
	 * @throws JSONException
	 */
	public static JoinAsGameBoardResponseData parseArgs(final JSONObject jsonObj) throws JSONException {
		//Get authToken
		String authToken = jsonObj.getString(AUTH_TOKEN);
		
		//Construct and return
		return new JoinAsGameBoardResponseData(authToken);

	}

}
