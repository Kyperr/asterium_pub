package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Response;

/**
 * {@link AllocateStatsResponseData} is the representation of data
 * to be used in a {@link Response} to the {@link Request} to
 * allocate a character's stats.
 * 
 * @author Studio Toozo
 */
public class AllocateStatsResponseData extends AbstractResponseActionData {

	public AllocateStatsResponseData() {
		super(ActionData.ALLOCATE_STATS);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}

	/**
	 * Parses {@link JSONObject} into a {@link AllocateStatsResponseData} object
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return the {@link AllocateStatsResponseData} object parsed from JSON. 
	 * @throws JSONException
	 */
	public static AllocateStatsResponseData parseArgs(JSONObject jsonObj) throws JSONException{
		
		return new AllocateStatsResponseData();
	}
}
