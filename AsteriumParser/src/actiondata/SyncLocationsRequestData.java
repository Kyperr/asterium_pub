package actiondata;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import message.Message;

public class SyncLocationsRequestData extends AbstractRequestActionData {

	private List<LocationData> locations;
	
	public SyncLocationsRequestData(final List<LocationData> locations) {
		super(ActionData.SYNC_LOCATIONS);
		this.locations = locations; 
	}


	public List<LocationData> getLocationList() {
		return locations;
	}
	
	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (LocationData datum : this.locations) {
			array.put(datum);
		}
		
		data.put(ActionData.LOCATIONS, array);
		return data;
	}

	
	public static SyncLocationsRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		List<LocationData> locations;
		
		
		return null; //new SyncLocationsRequestData();
	}
	

	public static SyncLocationsRequestData fromMessage(final Message message) throws JSONException {
		return SyncLocationsRequestData.class.cast(message.getActionData());
	}
	
	public static class LocationData {
		
		private String locationID;
		private List<String> activities;
		
		public LocationData(final String locationID, final List<String> activities) {
			this.locationID = locationID;
			this.activities = activities;
		}
		
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.LOCATION_ID, this.locationID);
			JSONArray array = new JSONArray();
			
			for (String s : this.activities) {
				array.put(s);
			}
			data.put(ActionData.ACTIVITIES, array);
			
			return data;
		}
	}
	
}
