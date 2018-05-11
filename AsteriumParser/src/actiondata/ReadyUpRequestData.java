package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static ReadyUpRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		String authToken = jsonObj.getString(AUTH_TOKEN);
		return new ReadyUpRequestData(authToken);
	}

}
