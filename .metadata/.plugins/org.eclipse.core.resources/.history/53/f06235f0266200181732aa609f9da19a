package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

import message.Request;

/**
 * {@link TurnRequestData} is the representation of data to 
 * be used in a {@link Request} from a player to take a turn.
 * 
 * @author Studio Toozo
 */
public class TurnRequestData extends AbstractRequestActionData {

	private String locationID;
	private String activityName;

	public TurnRequestData(final String locationID, final String activityName) {
		super(ActionData.TURN_ACTION);
		this.locationID = locationID;
		this.activityName = activityName;
	}

	public String getActivityName() {
		return this.activityName;
	}
	
	public String getLocationID() {
		return this.locationID;
	}


	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();

		data.put(ActionData.MAP_LOCATION, this.locationID);

		data.put(ActionData.ACTIVITY_NAME, this.activityName);

		return data;
	}

	/**
	 * Parses {@link JSONObject} into a {@link TurnRequestData} object
	 * 
	 * @param jsonObj the {@link JSONObject} to be parsed
	 * @return the {@link TurnRequestData} object parsed from JSON. 
	 * @throws JSONException
	 */
	public static TurnRequestData parseArgs(final JSONObject jsonObj) throws JSONException {

		// Get Location object
		String roomID = jsonObj.getString(ActionData.MAP_LOCATION);

		// Get activity name
		String activityName = jsonObj.getString(ActionData.ACTIVITY_NAME);


		// Construct and return
		return new TurnRequestData(roomID, activityName);

	}


	
	/*

//Commented out because it probably isn't needed.

	public static class Location {
		private String locationID;

		public Location(final String locationID) {
			this.locationID = locationID;
		}
		
		public String getLocationID() {
			return this.locationID;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();

			data.put(ActionData.LOCATION_ID, this.locationID);

			return data;
		}
	}
	*/
}
