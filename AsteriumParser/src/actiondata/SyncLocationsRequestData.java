package actiondata;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.SyncLocationsRequestData.LocationData.LocationType;
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
		List<LocationData> locations = new ArrayList<LocationData>();
		JSONArray locationsArray = jsonObj.getJSONArray(ActionData.LOCATIONS);
		for (int i = 0; i < locationsArray.length(); i++) {
			JSONObject locationObject = locationsArray.getJSONObject(i);
			String locationID = locationObject.getString(ActionData.LOCATION_ID);
			String name = locationObject.getString(ActionData.LOCATION_NAME);
			LocationType type;
			JSONObject typeObject = locationObject.getJSONObject(ActionData.LOCATION_TYPE);
			type = LocationType.valueOf(typeObject.getString(ActionData.LOCATION_TYPE));
			List<String> activities = new ArrayList<String>();
			JSONArray activitiesArray  = locationObject.getJSONArray(ActionData.ACTIVITIES);
			for (int j = 0; j < activitiesArray.length(); j++) {
				activities.add(activitiesArray.getString(j));	
			}
			
			locations.add(new LocationData(locationID, name, type, activities));
		}

		return new SyncLocationsRequestData(locations);
	}

	public static SyncLocationsRequestData fromMessage(final Message message) throws JSONException {
		return SyncLocationsRequestData.class.cast(message.getActionData());
	}

	public static class LocationData {

		private String locationID;
		private final String name;
		private final LocationType type;
		private List<String> activities;

		public enum LocationType {
			CONTROL_ROOM("control_room"), MED_BAY("med_bay");

			private final String jsonVersion;

			LocationType(String jsonVersion) {
				this.jsonVersion = jsonVersion;
			}

			public String getJSONVersion() {
				return this.jsonVersion;
			}
		}

		public LocationData(final String locationID, final String name, final LocationType type,
				final List<String> activities) {
			this.locationID = locationID;
			this.name = name;
			this.type = type;
			this.activities = activities;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.LOCATION_ID, this.locationID);
			data.put(ActionData.LOCATION_NAME, this.name);
			data.put(ActionData.LOCATION_TYPE, this.type.getJSONVersion());
			JSONArray array = new JSONArray();

			for (String s : this.activities) {
				array.put(s);
			}
			data.put(ActionData.ACTIVITIES, array);

			return data;
		}
	}

}
