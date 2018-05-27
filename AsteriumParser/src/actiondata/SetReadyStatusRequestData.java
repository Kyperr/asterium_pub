package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

public class SetReadyStatusRequestData extends AbstractRequestActionData {
	
	private boolean readyStatus;

	public SetReadyStatusRequestData(final boolean readyStatus) {
		super(ActionData.SET_READY_STATUS);
		this.readyStatus = readyStatus;
	}
	
	public boolean getIsReady() {
		return this.readyStatus;
	}
	
	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();

		data.put(ActionData.PLAYER_READY_STATUS, this.readyStatus);

		return data;
	}
	
	/**
	 * Parses {@link JSONObject} into a {@link SetReadyStatusRequestData} object
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return the {@link SetReadyStatusRequestData} object parsed from JSON. 
	 * @throws JSONException
	 */
	public static SetReadyStatusRequestData parseArgs(final JSONObject jsonObj) throws JSONException {

		boolean readyStatus = jsonObj.getBoolean(ActionData.PLAYER_READY_STATUS);

		// Construct and return
		return new SetReadyStatusRequestData(readyStatus);

	}

}
