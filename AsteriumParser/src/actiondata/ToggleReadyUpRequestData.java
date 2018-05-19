package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Request;

/**
 * {@link ToggleReadyUpRequestData} is the representation of data to 
 * be used in a {@link Request} to ready up as a player.
 * 
 * @author Studio Toozo
 */
public class ToggleReadyUpRequestData extends AbstractRequestActionData {
	
	public ToggleReadyUpRequestData() {
		super(TOGGLE_READY_UP);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	
	/**
	 * Parses {@link JSONObject} into a {@link ToggleReadyUpRequestData} object
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return the {@link ToggleReadyUpRequestData} object parsed from JSON. 
	 * @throws JSONException
	 */
	public static ToggleReadyUpRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		return new ToggleReadyUpRequestData();
	}

}
