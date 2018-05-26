package actiondata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.SyncPlayerClientDataRequestData.LocationData.LocationType;
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

	private int food;
	private int fuel;
	private int day;
	private List<LocationData> locations;
	private PlayerCharacterData character;
	private Collection<String> characters;
	private List<ItemData> inventory;
	private String gamePhaseName;

	public SyncPlayerClientDataRequestData(final int food, final int fuel, final int day,
			final List<LocationData> locations, final PlayerCharacterData character, 
			final Collection<String> characters, final String gamePhaseName,
			final List<ItemData> inventory) {
		super(ActionData.SYNC_PLAYER_CLIENT_DATA);
		this.food = food;
		this.fuel = fuel;
		this.day = day;
		this.locations = locations;
		this.character = character;
		this.characters = characters;
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

		data.put(ActionData.FOOD, this.food);
		data.put(ActionData.FUEL, this.fuel);
		data.put(ActionData.DAY, this.day);
		data.put(ActionData.LOCATIONS, array);
		data.put(ActionData.CHARACTER, this.character.jsonify());
		JSONArray charactersArray = new JSONArray();
		for (String s : this.characters) {
			charactersArray.put(s);
		}
		data.put(ActionData.CHARACTERS, charactersArray);
		JSONArray inventoryArray = new JSONArray();
		for (ItemData item : this.inventory) {
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
		int food = jsonObj.getInt(ActionData.FOOD);
		int fuel = jsonObj.getInt(ActionData.FUEL);
		int day = jsonObj.getInt(ActionData.DAY);
		
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
		
		Collection<String> characters = new ArrayList<String>();
		JSONArray charactersArray = jsonObj.getJSONArray(ActionData.CHARACTERS);
		for (int c = 0; c < characters.size(); c++) {
			characters.add(charactersArray.getString(c));
		}

		// character personal inventory
		JSONArray personalInvArray = characterObj.getJSONArray(ActionData.PERSONAL_INVENTORY);
		List<ItemData> personalInv = new ArrayList<ItemData>();
		JSONObject personalInvObject;
		ItemData personalInvItem;
		for (int g = 0; g < personalInvArray.length(); g++) {
			personalInvObject = personalInvArray.getJSONObject(g);
			personalInvItem = ItemData.parseArgs(personalInvObject);
			personalInv.add(personalInvItem);
		}

		// character loadout
		JSONArray loadoutArray = characterObj.getJSONArray(ActionData.LOADOUT);
		Map<EquipmentType, ItemData> equipments = new HashMap<EquipmentType, ItemData>();
		LoadoutData loadout;
		JSONObject equipmentObject;
		ItemData equipment;

		for (int i = 0; i < loadoutArray.length(); i++) {
			JSONObject jo = loadoutArray.getJSONObject(i);
			EquipmentType type = EquipmentType.valueOf(jo.getString(ActionData.SLOT));
			equipmentObject = jo.getJSONObject(ActionData.EQUIPMENT);
			equipment = ItemData.parseArgs(equipmentObject);
			equipments.put(type, equipment);
		}
		loadout = new LoadoutData(equipments);

		boolean turnTaken = characterObj.getBoolean(ActionData.TURN_TAKEN);
		boolean ready = characterObj.getBoolean(ActionData.READY);

		character = new PlayerCharacterData(characterName, stats, personalInv, loadout, turnTaken, ready);

		// build locations
		JSONArray locationsArray = jsonObj.getJSONArray(ActionData.LOCATIONS);
		List<LocationData> locations = new ArrayList<LocationData>();
		for (int i = 0; i < locationsArray.length(); i++) {
			JSONObject locationObject = locationsArray.getJSONObject(i);
			String locationID = locationObject.getString(ActionData.LOCATION_ID);
			String name = locationObject.getString(ActionData.LOCATION_NAME);
			LocationType type;
			JSONObject typeObject = locationObject.getJSONObject(ActionData.LOCATION_TYPE);
			type = LocationType.valueOf(typeObject.getString(ActionData.LOCATION_TYPE));
			Set<String> activities = new HashSet<String>();
			JSONArray activitiesArray = locationObject.getJSONArray(ActionData.ACTIVITIES);
			for (int j = 0; j < activitiesArray.length(); j++) {
				activities.add(activitiesArray.getString(j));
			}

			locations.add(new LocationData(locationID, name, type, activities));
		}

		// build communal inventory
		JSONArray inventoryArray = jsonObj.getJSONArray(ActionData.COMMUNAL_INVENTORY);
		List<ItemData> inventory = new ArrayList<ItemData>();
		JSONObject itemObject;
		ItemData item;
		for (int j = 0; j < inventoryArray.length(); j++) {
			itemObject = inventoryArray.getJSONObject(j);
			item = ItemData.parseArgs(itemObject);
			inventory.add(item);
		}

		String gamePhaseName = jsonObj.getString(ActionData.GAME_PHASE_NAME);
		return new SyncPlayerClientDataRequestData(food, fuel, day, locations, character, characters, gamePhaseName, inventory);
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
		private final String name;
		private final LocationType type;
		private Set<String> activities;

		public enum LocationType {
			CONTROL_ROOM("control_room"),
			MED_BAY("med_bay"), 
			MESS_HALL("mess_hall"), 
			RESIDENTAIL("residential");

			private final String jsonVersion;

			LocationType(String jsonVersion) {
				this.jsonVersion = jsonVersion;
			}

			public String getJSONVersion() {
				return this.jsonVersion;
			}
		}

		public LocationData(final String locationID, final String name, final LocationType type,
				final Set<String> activities) {
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
		private List<ItemData> inventory;
		private LoadoutData equipped;
		private boolean turnTaken;
		private boolean ready;

		public PlayerCharacterData(final String characterName, final StatsData stats,
				final List<ItemData> inventory, final LoadoutData equipped, 
				final boolean turnTaken, final boolean ready) {
			this.name = characterName;
			this.stats = stats;
			this.inventory = inventory;
			this.equipped = equipped;
			this.turnTaken = turnTaken;
			this.ready = ready;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.CHARACTER_NAME, this.name);
			data.put(ActionData.STATS, this.stats.jsonify());
			JSONArray inventoryArray = new JSONArray();
			for (ItemData item : this.inventory) {
				inventoryArray.put(item.jsonify());
			}
			data.put(ActionData.PERSONAL_INVENTORY, inventoryArray);
			data.put(ActionData.LOADOUT, this.equipped.jsonify());
			data.put(ActionData.TURN_TAKEN, this.turnTaken);
			data.put(ActionData.READY, this.ready);

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
			private Map<EquipmentType, ItemData> equipment;

			public LoadoutData(final Map<EquipmentType, ItemData> equipment) {
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

	public static class ItemData {
		private String name;
		private String description;
		private String flavorText;
		private String imagePath;
		private boolean isLocationItem;
		private Collection<LocationType> useLocations;

		public ItemData(final String name, final String description, final String flavor, final String image,
				final boolean isLocationItem, final Collection<LocationType> locations) {
			this.name = name;
			this.description = description;
			this.flavorText = flavor;
			this.imagePath = image;
			this.isLocationItem = isLocationItem;
			this.useLocations = locations;
		}

		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.ITEM_NAME, this.name);
			data.put(ActionData.ITEM_DESC, this.description);
			data.put(ActionData.ITEM_FLAVOR_TEXT, this.flavorText);
			data.put(ActionData.ITEM_IMG, this.imagePath);
			data.put(ActionData.IS_LOCATION_ITEM, this.isLocationItem);
			JSONArray use = new JSONArray();
			for (LocationType type : this.useLocations) {
				use.put(type.toString());
			}
			data.put(ActionData.USE_LOCATIONS, use);

			return data;
		}

		public static ItemData parseArgs(final JSONObject jsonObj) {
			String name = jsonObj.getString(ActionData.ITEM_NAME);
			String desc = jsonObj.getString(ActionData.ITEM_DESC);
			String flavor = jsonObj.getString(ActionData.ITEM_FLAVOR_TEXT);
			String img = jsonObj.getString(ActionData.ITEM_IMG);
			boolean isLocItem = jsonObj.getBoolean(ActionData.IS_LOCATION_ITEM);
			Collection<LocationType> locs = new ArrayList<LocationType>();
			JSONArray locArray = jsonObj.getJSONArray(ActionData.USE_LOCATIONS);
			for (int i = 0; i < locArray.length(); i++) {
				String type = locArray.get(i).toString();
				locs.add(LocationType.valueOf(type));
			}
			
			return new ItemData(name, desc, flavor, img, isLocItem, locs);
		}
	}
}
