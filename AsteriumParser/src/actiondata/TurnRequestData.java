package actiondata;

import org.json.JSONObject;

public class TurnRequestData extends AbstractRequestActionData {

	private String authToken;
	private TurnRequestData.Location location;
	private TurnRequestData.Activity activity;

	public TurnRequestData() {
		super(ActionData.TURN);
	}

	public String getAuthToken() {
		return this.authToken;
	}

	public TurnRequestData.Location getLocation() {
		return this.location;
	}

	public TurnRequestData.Activity getActivity() {
		return this.activity;
	}

	@Override
	public JSONObject jsonify() {
		// TODO Auto-generated method stub
		return null;
	}

	public class Location {
		private String roomID;

	}

	public class Activity {
		private String name;

	}
}
