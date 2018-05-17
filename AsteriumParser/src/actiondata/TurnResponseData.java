package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Response;

/**
 * {@link TurnResponseData} is the representation of data
 * to be used in a {@link Response} to the {@link Request} to
 * take a turn.
 * 
 * @author Studio Toozo
 */
public class TurnResponseData extends AbstractResponseActionData {

	public TurnResponseData() {
		super(ActionData.TURN);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link TurnResponseData} object
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return the {@link TurnResponseData} object parsed from JSON. 
	 * @throws JSONException
	 */
	public static TurnResponseData parseArgs(final JSONObject jsonObj) {
		return new TurnResponseData();
	}

}
