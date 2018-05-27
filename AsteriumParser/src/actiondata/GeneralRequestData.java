package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link GeneralRequestData} is the representation of data
 * to be used in a {@link Request} that has no specified action data.
 * 
 * @author Studio Toozo
 *
 */
public class GeneralRequestData extends AbstractRequestActionData {

	public GeneralRequestData(String name) {
		super(name);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		data.put(ActionData.ACTION_NAME, this.getName());
		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link GeneralRequestData} object.
	 * 
	 * @param jsonObj	the {@link JSONObject} to be parsed
	 * @return	the {@link GeneralRequestData} object parsed from JSON
	 * @throws JSONException
	 */
	public static GeneralRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		// Construct and return
		System.out.println(jsonObj.toString());
		String name = jsonObj.getString(ActionData.ACTION_NAME);
		return new GeneralRequestData(name);

	}

}
