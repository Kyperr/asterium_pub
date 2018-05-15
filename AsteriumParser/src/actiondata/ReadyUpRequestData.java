package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Request;

/**
 * {@link ReadyUpRequestData} is the representation of data to 
 * be used in a {@link Request} to ready up as a player.
 * 
 * @author Studio Toozo
 */
public class ReadyUpRequestData extends AbstractRequestActionData {

	private final String authToken;
	
	public ReadyUpRequestData(final String authToken) {
		super(READY_UP);
		this.authToken = authToken;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	public String getAuthToken() {
		return this.authToken;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link ReadyUpRequestData} object
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return the {@link ReadyUpRequestData} object parsed from JSON. 
	 * @throws JSONException
	 */
	public static ReadyUpRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		String authToken = jsonObj.getString(AUTH_TOKEN);
		return new ReadyUpRequestData(authToken);
	}

}
