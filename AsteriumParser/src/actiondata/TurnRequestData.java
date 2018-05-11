package actiondata;

import org.json.JSONException;
import org.json.JSONObject;

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

	public static TurnRequestData parseArgs(final JSONObject jsonObj) throws JSONException {

		// Getting authToken
		String authToken = jsonObj.getString(ActionData.AUTH_TOKEN);

		// Get Location object
		JSONObject locationObj = jsonObj.getJSONObject(ActionData.LOCATION);
		String roomID = locationObj.getString(ActionData.ROOM_ID);
		Location location = new Location(roomID);

		// Get activity name
		String activityName = jsonObj.getString(ActionData.ACTIVITY_NAME);

		// Get Activity object
		Activity activity = new Activity(activityName);

		// Construct and return
		return new TurnRequestData(authToken, location, activityName, activity);

	}

	public static class Location {
		private String roomID;

		public Location(final String roomID) {
			this.roomID = roomID;
		}

		public final String getRoomID() {
			return roomID;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();

			data.put(ActionData.ROOM_ID, this.roomID);

			return data;
		}
	}

	public static class Activity {
		private String name;

		public Activity(final String name) {
			this.name = name;
		}

		public final String getName() {
			return name;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();

			data.put(ActionData.ACTIVITY_NAME, this.name);

			return data;
		}
	}
}
