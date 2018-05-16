package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link SuccessResponseData} is the representation of data to be used in a
 * {@link Response} to the {@link Request} which does not require a specified 
 * success response.
 * 
 * @author Studio Toozo
 */
public class SuccessResponseData extends AbstractResponseActionData {

	/**
	 * 
	 * @param name The name of the successful action.
	 */
	public SuccessResponseData(final String name) {
		super(name);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		data.put(ActionData.ACTION_NAME, this.getName());
		return data;
	}
	
	/**
	 * Parses JSONObject into a {@link SuccessResponseData} object.
	 * 
	 * @param jsonObj the JSONObject to be parsed
	 * @return	the SuccessResponseData object parsed from JSON
	 */
	public static SuccessResponseData parseArgs(final JSONObject jsonObj) throws JSONException {
		String name = jsonObj.getString(ActionData.ACTION_NAME);
		return new SuccessResponseData(name);
	}

}
