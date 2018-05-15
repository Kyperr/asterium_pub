package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

public class SyncLocationsResponseData extends AbstractResponseActionData {

	public SyncLocationsResponseData() {
		super(ActionData.SYNC_LOCATIONS);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	public static SyncLocationsResponseData parseArgs(final JSONObject jsonObj) throws JSONException {
		return new SyncLocationsResponseData();
	}

}
