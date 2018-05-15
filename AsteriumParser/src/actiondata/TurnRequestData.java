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

	private String authToken;
	private TurnRequestData.Location location;
	private String activityName;
	private TurnRequestData.Activity activity;

	public TurnRequestData(final String authToken, final TurnRequestData.Location location, final String activityName,
			final TurnRequestData.Activity activity) {
		super(ActionData.TURN);
		this.authToken = authToken;
		this.location = location;
		this.activityName = activityName;
		this.activity = activity;
	}

	public String getAuthToken() {
		return this.authToken;
	}

	public TurnRequestData.Location getLocation() {
		return this.location;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public TurnRequestData.Activity getActivity() {
		return this.activity;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();

		data.put(ActionData.AUTH_TOKEN, this.authToken);

		data.put(ActionData.LOCATION, this.location.jsonify());

		data.put(ActionData.ACTIVITY_NAME, this.activityName);

		data.put(ActionData.ACTIVITY, this.activity.jsonify());

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

		// Getting authToken
		String authToken = jsonObj.getString(ActionData.AUTH_TOKEN);

		// Get Location object
		JSONObject locationObj = jsonObj.getJSONObject(ActionData.LOCATION);
		String roomID = locationObj.getString(ActionData.LOCATION_ID);
		Location location = new Location(roomID);

		// Get activity name
		String activityName = jsonObj.getString(ActionData.ACTIVITY_NAME);

		// Get Activity object
		Activity activity = new Activity(activityName);

		// Construct and return
		return new TurnRequestData(authToken, location, activityName, activity);

	}

	/**
	 * {@link Location} is the representation of a Location only for the purpose
	 * of taking a turn, not for game logic.
	 * 
	 * @author Studio Toozo
	 */
	public static class Location {
		private String locationID;

		public Location(final String locationID) {
			this.locationID = locationID;
		}
		
		public String getLocationID() {
			return this.locationID;
		}

		/**
		 * 
		 * @return	JSONObject representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();

			data.put(ActionData.LOCATION_ID, this.locationID);

			return data;
		}
	}

	/**
	 * {@link Activity} is the representation of an Activity only for
	 * the purpose of taking a turn, not for game logic.
	 * 
	 * @author Studio Toozo
	 */
	public static class Activity {
		private String name;

		public Activity(final String name) {
			this.name = name;
		}
		
		public String getActivityName() {
			return this.name;
		}

		/**
		 * 
		 * @return	JSONObject representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();

			data.put(ActionData.ACTIVITY_NAME, this.name);

			return data;
		}
	}
}
