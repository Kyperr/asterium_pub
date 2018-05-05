package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link JoinAsPlayerResponseData} is the representation of data to be used in a
 * {@link Response} to the {@link Request} to join a lobby as a player.
 * 
 * @author Studio Toozo
 *
 */
public class JoinAsPlayerResponseData extends AbstractActionData {
	
	private final String authToken;

	public JoinAsPlayerResponseData(final String authToken) {
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
	 * Parses JSONObject into a {@link JoinAsPlayerResponseData} object.
	 * 
	 * @param jsonObj the JSONObject to be parsed
	 * @return	the JoinAsPlayerResponseData object parsed from JSON
	 * @throws JSONException
	 */
	public static JoinAsPlayerResponseData parseArgs(final JSONObject jsonObj) throws JSONException {
		//Get authToken
		String authToken = jsonObj.getString(AUTH_TOKEN);
		
		//Construct and return
		return new JoinAsPlayerResponseData(authToken);

	}

	@Override
	protected boolean fieldsEqual(final Object other) {
		if (other instanceof JoinAsPlayerResponseData) {
			JoinAsPlayerResponseData data = JoinAsPlayerResponseData.class.cast(other);
			return (data.authToken.equals(this.authToken));
		} else {
			return false;
		}
	}

}
