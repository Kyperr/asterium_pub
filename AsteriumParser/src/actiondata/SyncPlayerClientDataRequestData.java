package actiondata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.SyncPlayerClientDataRequestData.Character.Stats;
import message.Request;

/**
 * {@link SyncPlayerClientDataRequestData} is the representation of data to 
 * be used in a {@link Request} to the PlayerClient to update the data.
 * 
 * @author Studio Toozo
 */
public class SyncPlayerClientDataRequestData extends AbstractRequestActionData {

	private List<LocationData> locations;
	private Character character;

	public SyncPlayerClientDataRequestData(final List<LocationData> locations, final Character character) {
		super(ActionData.SYNC_PLAYER_CLIENT_DATA);
		this.locations = locations;
		this.character = character;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();

		for (LocationData datum : this.locations) {
			array.put(datum.jsonify());
		}

		data.put(ActionData.LOCATIONS, array);
		data.put(ActionData.CHARACTER, this.character.jsonify());
		return data;
	}

	/**
	 * Parses JSONObject into a {@link SyncPlayerClientDataRequestData} object.
	 * 
	 * @param jsonObj the JSONObject to be parsed
	 * @return	the SyncPlayerClientDataRequestData object parsed from JSON
	 * @throws JSONException
	 */
	public static SyncPlayerClientDataRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		Character character;
		JSONObject characterObj = jsonObj.getJSONObject(ActionData.CHARACTER);
		String characterName = characterObj.getString(ActionData.CHARACTER_NAME);
		JSONObject statsObj = characterObj.getJSONObject(ActionData.STATS);
		Integer health = statsObj.getInt(ActionData.HEALTH);
		Integer stamina = statsObj.getInt(ActionData.STAMINA);
		Integer luck = statsObj.getInt(ActionData.LUCK);
		Integer intuition = statsObj.getInt(ActionData.INTUITION);
		Stats stats = new Stats(health, stamina, luck, intuition);
		character = new Character(characterName, stats);	
		
		JSONArray locationsArray = jsonObj.getJSONArray(ActionData.LOCATIONS);
		List<LocationData> locations = new ArrayList<LocationData>();
		JSONObject locationObject;
		LocationData location;
		for (int i = 0; i < locationsArray.length(); i++) {
			locationObject = locationsArray.getJSONObject(i);
			String locationID = locationObject.getString(ActionData.LOCATION_ID);
			String locationType = locationObject.getString(ActionData.LOCATION_TYPE);
			Set<String> activities = new HashSet<String>();
			JSONArray activitiesArray = locationObject.getJSONArray(ActionData.ACTIVITIES);			
			for (int j = 0; j < activitiesArray.length(); j++) {
				String activity = String.class.cast(activitiesArray.getJSONObject(j));
				activities.add(activity);
			}
			
			location = new LocationData(locationID, locationType, activities);
			locations.add(location);
		}

		return new SyncPlayerClientDataRequestData(locations, character);
	}

	/**
	 * {@link LocationData} is an inner class of {@link SyncPlayerClientDataRequestData}
	 * that holds data for a location only for the purpose of updating a player client.
	 * 
	 * @author Studio Toozo
	 */
	public static class LocationData {

		private String locationID;
		private Set<String> activities;

		public LocationData(final String locationID, final String locationType, final Set<String> activities) {
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
	
	/**
	 * {@link Character} is an inner class of {@link SyncPlayerClientDataRequestData}
	 * used only for the purpose of updating a player client.
	 * 
	 * @author Studio Toozo
	 */
	public static class Character {
		
		private String name;
		private Stats stats;
		
		public Character(final String characterName, final Stats stats) {
			this.name = characterName;
			this.stats = stats;
		}
		
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.CHARACTER_NAME, this.name);
			data.put(ActionData.STATS, this.stats.jsonify());
			
			return data;
		}
		
		/**
		 * {@link Stats} is an inner class of {@link Character}
		 * used only for the purpose of updating a player client
		 * within a {@link SyncPlayerClientDataRequestData}.
		 * 
		 * @author Studio Toozo
		 */
		public static class Stats {
			
			private Integer health;
			private Integer stamina;
			private Integer luck;
			private Integer intuition;
			
			public Stats(final Integer health, final Integer stamina, final Integer luck, final Integer intuition) {
				this.health = health;
				this.stamina = stamina;
				this.luck = luck;
				this.intuition = intuition;
			}
			
			public JSONObject jsonify() {
				JSONObject data = new JSONObject();
				data.put(ActionData.HEALTH, this.health);
				data.put(ActionData.STAMINA, this.stamina);
				data.put(ActionData.LUCK, this.luck);
				data.put(ActionData.INTUITION, this.intuition);
				
				return data;
			}
		}
	}
}
