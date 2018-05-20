package actiondata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.SyncPlayerClientDataRequestData.PlayerCharacterData.StatsData;
import message.Request;

/**
 * {@link SyncPlayerClientDataRequestData} is the representation of data to be
 * used in a {@link Request} to the PlayerClient to update the data.
 * 
 * @author Studio Toozo
 */
public class SyncPlayerClientDataRequestData extends AbstractRequestActionData {

	private List<LocationData> locations;
	private PlayerCharacterData character;
	private List<InventoryData> inventory;
	private String gamePhaseName;

	public SyncPlayerClientDataRequestData(final List<LocationData> locations, final PlayerCharacterData character,
			final String gamePhaseName, final List<InventoryData> inventory) {
		super(ActionData.SYNC_PLAYER_CLIENT_DATA);
		this.locations = locations;
		this.character = character;
		this.inventory = inventory;
		this.gamePhaseName = gamePhaseName;
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
		JSONArray inventoryArray = new JSONArray();
		for (InventoryData item : this.inventory) {
			data.put(ActionData.ITEM, item.jsonify());
		}
		data.put(ActionData.COMMUNAL_INVENTORY, inventoryArray);
		data.put(ActionData.GAME_PHASE_NAME, this.gamePhaseName);
		return data;
	}

	/**
	 * Parses JSONObject into a {@link SyncPlayerClientDataRequestData} object.
	 * 
	 * @param jsonObj
	 *            the JSONObject to be parsed
	 * @return the SyncPlayerClientDataRequestData object parsed from JSON
	 * @throws JSONException
	 */
	public static SyncPlayerClientDataRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		PlayerCharacterData character;
		JSONObject characterObj = jsonObj.getJSONObject(ActionData.CHARACTER);
		String characterName = characterObj.getString(ActionData.CHARACTER_NAME);
		JSONObject statsObj = characterObj.getJSONObject(ActionData.STATS);
		Integer health = statsObj.getInt(ActionData.HEALTH);
		Integer stamina = statsObj.getInt(ActionData.STAMINA);
		Integer luck = statsObj.getInt(ActionData.LUCK);
		Integer intuition = statsObj.getInt(ActionData.INTUITION);
		StatsData stats = new StatsData(health, stamina, luck, intuition);
		character = null;//new PlayerCharacterData(characterName, stats);

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
		
		JSONArray inventoryArray = jsonObj.getJSONArray(ActionData.COMMUNAL_INVENTORY);
		List<InventoryData> inventory = new ArrayList<InventoryData>();
		JSONObject itemObject;
		InventoryData item;
		for (int j = 0; j < inventoryArray.length(); j++) {
			itemObject = inventoryArray.getJSONObject(j);
			String name = itemObject.getString(ActionData.ITEM_NAME);
			item = new InventoryData(name);
			inventory.add(item);
		}

		String gamePhaseName = jsonObj.getString(ActionData.GAME_PHASE_NAME);
		return new SyncPlayerClientDataRequestData(locations, character, gamePhaseName, inventory);
	}

	/**
	 * {@link LocationData} is an inner class of
	 * {@link SyncPlayerClientDataRequestData} that holds data for a location only
	 * for the purpose of updating a player client.
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
	 * {@link PlayerCharacterData} is an inner class of
	 * {@link SyncPlayerClientDataRequestData} used only for the purpose of updating
	 * a player client.
	 * 
	 * @author Studio Toozo
	 */
	public static class PlayerCharacterData {

		private String name;
		private StatsData stats;
		private List<InventoryData> inventory;
		private LoadoutData equipped;

		public PlayerCharacterData(final String characterName, final StatsData stats, 
								   final List<InventoryData> inventory, final LoadoutData equipped) {
			this.name = characterName;
			this.stats = stats;
			this.inventory = inventory;
			this.equipped = equipped;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.CHARACTER_NAME, this.name);
			data.put(ActionData.STATS, this.stats.jsonify());
			JSONArray inventoryArray = new JSONArray();
			for (InventoryData item : this.inventory) {
				data.put(ActionData.ITEM, item.jsonify());
			}
			data.put(ActionData.PERSONAL_INVENTORY, inventoryArray);

			return data;
		}

		/**
		 * {@link StatsData} is an inner class of {@link PlayerCharacterData} used only for
		 * the purpose of updating a player client within a
		 * {@link SyncPlayerClientDataRequestData}.
		 * 
		 * @author Studio Toozo
		 */
		public static class StatsData {

			private Integer health;
			private Integer stamina;
			private Integer luck;
			private Integer intuition;

			public StatsData(final Integer health, final Integer stamina, final Integer luck, final Integer intuition) {
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
		
		public static class LoadoutData {
			
		}
	}

	public static class InventoryData {
		private String name;

		public InventoryData(final String name) {
			this.name = name;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.ITEM_NAME, this.name);

			return data;
		}
	}
}
