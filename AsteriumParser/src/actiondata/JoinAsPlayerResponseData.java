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
public class JoinAsPlayerResponseData extends AbstractResponseActionData {
	
	private final String authToken;

	public JoinAsPlayerResponseData(final String authToken) {
		super(ActionData.JOIN_AS_PLAYER);
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

}
