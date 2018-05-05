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
public class JoinAsGameBoardResponseData extends AbstractActionData {
	
	private final String authToken;

	public JoinAsGameBoardResponseData(final String authToken) {
		super(JOIN_AS_PLAYER_RESPONSE);
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

	@Override
	protected boolean fieldsEqual(final Object other) {
		if (other instanceof JoinAsGameBoardResponseData) {
			JoinAsGameBoardResponseData data = JoinAsGameBoardResponseData.class.cast(other);
			return (data.authToken.equals(this.authToken));
		} else {
			return false;
		}
	}

}
