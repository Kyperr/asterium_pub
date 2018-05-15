package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Response;

/**
 * {@link ReadyUpResponseData} is the representation of data
 * to be used in a {@link Response} to the {@link Request} to
 * ready up as a player.
 * 
 * @author Studio Toozo
 */
public class ReadyUpResponseData extends AbstractResponseActionData{

	public ReadyUpResponseData() {
		super(READY_UP);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link ReadyUpResponseData} object
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return the {@link ReadyUpResponseData} object parsed from JSON. 
	 * @throws JSONException
	 */
	public static ReadyUpResponseData parseArgs(final JSONObject jsonObj) {
		return new ReadyUpResponseData();
	}

}
