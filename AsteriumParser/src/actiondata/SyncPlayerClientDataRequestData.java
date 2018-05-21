package actiondata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData;
import actiondata.SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType;
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
		// build character
		PlayerCharacterData character;
		JSONObject characterObj = jsonObj.getJSONObject(ActionData.CHARACTER);
		String characterName = characterObj.getString(ActionData.CHARACTER_NAME);
		// character stats
		JSONObject statsObj = characterObj.getJSONObject(ActionData.STATS);
		Integer health = statsObj.getInt(ActionData.HEALTH);
		Integer stamina = statsObj.getInt(ActionData.STAMINA);
		Integer luck = statsObj.getInt(ActionData.LUCK);
		Integer intuition = statsObj.getInt(ActionData.INTUITION);
		StatsData stats = new StatsData(health, stamina, luck, intuition);

		// character personal inventory
		JSONArray personalInvArray = characterObj.getJSONArray(ActionData.PERSONAL_INVENTORY);
		List<InventoryData> personalInv = new ArrayList<InventoryData>();
		JSONObject personalInvObject;
		InventoryData personalInvItem;
		for (int g = 0; g < personalInvArray.length(); g++) {
			personalInvObject = personalInvArray.getJSONObject(g);
			personalInvItem = InventoryData.parseArgs(personalInvObject);
			personalInv.add(personalInvItem);
		}

		// character loadout
		JSONArray loadoutArray = characterObj.getJSONArray(ActionData.LOADOUT);
		Map<EquipmentType, InventoryData> equipments = new HashMap<EquipmentType, InventoryData>();
		LoadoutData loadout;
		JSONObject equipmentObject;
		InventoryData equipment;

		for (int i = 0; i < loadoutArray.length(); i++) {
			JSONObject jo = loadoutArray.getJSONObject(i);
			EquipmentType type = EquipmentType.valueOf(jo.getString(ActionData.SLOT));
			equipmentObject = jo.getJSONObject(ActionData.EQUIPMENT);			
			equipment = InventoryData.parseArgs(equipmentObject);
			equipments.put(type, equipment);
		}
		loadout = new LoadoutData(equipments);
		
		boolean turnTaken = characterObj.getBoolean(ActionData.TURN_TAKEN);

		character = new PlayerCharacterData(characterName, stats, personalInv, loadout, turnTaken);

		// build locations
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

		// build communal inventory
		JSONArray inventoryArray = jsonObj.getJSONArray(ActionData.COMMUNAL_INVENTORY);
		List<InventoryData> inventory = new ArrayList<InventoryData>();
		JSONObject itemObject;
		InventoryData item;
		for (int j = 0; j < inventoryArray.length(); j++) {
			itemObject = inventoryArray.getJSONObject(j);
			item = InventoryData.parseArgs(itemObject);
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
		private boolean turnTaken;

		public PlayerCharacterData(final String characterName, final StatsData stats,
				final List<InventoryData> inventory, final LoadoutData equipped, final boolean turnTaken) {
			this.name = characterName;
			this.stats = stats;
			this.inventory = inventory;
			this.equipped = equipped;
			this.turnTaken = turnTaken;
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
			data.put(ActionData.LOADOUT, this.equipped.jsonify());
			data.put(ActionData.TURN_TAKEN, this.turnTaken);

			return data;
		}

		/**
		 * {@link StatsData} is an inner class of {@link PlayerCharacterData} used only
		 * for the purpose of updating a player client within a
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
			private Map<EquipmentType, InventoryData> equipment;

			public LoadoutData(final Map<EquipmentType, InventoryData> equipment) {
				this.equipment = equipment;
			}

			public JSONObject jsonify() {
				JSONObject data = new JSONObject();

				JSONArray loadoutArray = new JSONArray();
				JSONObject loadoutObject = new JSONObject();
				for (EquipmentType type : equipment.keySet()) {
					loadoutObject.put(ActionData.SLOT, type.toString());
					loadoutObject.put(ActionData.EQUIPMENT, this.equipment.get(type).jsonify());
					loadoutArray.put(loadoutObject);
				}
				data.put(ActionData.LOADOUT, loadoutArray);

				return data;
			}

			public enum EquipmentType {
				HEAD, TORSO, ARMS, LEGS;

				@Override
				public String toString() {
					return this.name();
				}

			}
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

		public static InventoryData parseArgs(final JSONObject jsonObj) {
			String name = jsonObj.getString(ActionData.ITEM_NAME);
			return new InventoryData(name);
		}
	}
}
