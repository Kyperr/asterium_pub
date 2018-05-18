package actiondata;

import java.util.Collection;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import message.Request;

/**
 * {@link UseItemRequestData} is the representation of data to be used
 * in a {@link Request} from a PlayerCharacter to use an Item from an Inventory.
 * 
 * @author Studio Toozo
 */
public class UseItemRequestData extends AbstractRequestActionData {

	private PlayerCharacterData user;
	private Collection<PlayerCharacterData> targets;
	private ItemData item;
	private boolean isCommunal;

	public UseItemRequestData(final PlayerCharacterData user, final Collection<PlayerCharacterData> targets,
			final ItemData item, final boolean isCommunal) {
		super(ActionData.USE_PERSONAL_ITEM);
		this.user = user;
		this.targets = targets;
		this.item = item;
		this.isCommunal = isCommunal;
	}

	public final PlayerCharacterData getUser() {
		return this.user;
	}

	public final Collection<PlayerCharacterData> getTargets() {
		return this.targets;
	}

	public final ItemData getItem() {
		return this.item;
	}
	
	public final boolean getIsCommunal() {
		return this.isCommunal;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		data.put(ActionData.USER, this.user.jsonify());

		JSONArray targetsArray = new JSONArray();
		for (PlayerCharacterData target : this.targets) {
			targetsArray.put(target.jsonify());
		}
		data.put(ActionData.TARGETS, targetsArray);

		data.put(ActionData.ITEM, this.item.jsonify());
		
		data.put(ActionData.IS_COMMUNAL, this.isCommunal);

		return data;
	}

	/**
	 * Parses {@link JSONObject} into a {@link UseItemRequestData} object
	 * 
	 * @param jsonObj
	 *            the {@link JSONObject} to be parsed
	 * @return the {@link UseItemRequestData} object parsed from JSON.
	 * @throws JSONException
	 */
	public static UseItemRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		PlayerCharacterData user = PlayerCharacterData.parseArgs(jsonObj.getJSONObject(ActionData.USER));

		JSONArray targetsArray = jsonObj.getJSONArray(ActionData.TARGETS);
		Collection<PlayerCharacterData> targets = new HashSet<PlayerCharacterData>();
		for (int i = 0; i < targetsArray.length(); i++) {
			targets.add(PlayerCharacterData.parseArgs(targetsArray.getJSONObject(i)));
		}

		JSONObject itemObject = jsonObj.getJSONObject(ActionData.ITEM);
		String itemID = itemObject.getString(ActionData.ITEM_ID);
		ItemData item = new ItemData(itemID);
		
		boolean isCommunal = jsonObj.getBoolean(ActionData.IS_COMMUNAL);

		return new UseItemRequestData(user, targets, item, isCommunal);
	}

	/**
	 * {@link PlayerCharacterData} is an inner class of {@link UseItemRequestData}
	 * that holds data for a player character only for the purpose of using
	 * an item from a player character's personal inventory.
	 * 
	 * @author Studio Toozo
	 */
	public static class PlayerCharacterData {
		private String authToken;

		public PlayerCharacterData(final String authToken) {
			this.authToken = authToken;
		}

		public final String getAuthToken() {
			return this.authToken;
		}

		/**
		 * 
		 * @return {@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.AUTH_TOKEN, this.authToken);
			return data;
		}

		/**
		 * Parses {@link JSONObject} into a {@link PlayerCharacterData} object
		 * 
		 * @param jsonObj
		 *            the {@link JSONObject} to be parsed
		 * @return the {@link PlayerCharacterData} object parsed from JSON.
		 * @throws JSONException
		 */
		public static PlayerCharacterData parseArgs(JSONObject jsonObj) throws JSONException {
			jsonObj = jsonObj.getJSONObject(ActionData.USER);
			String userAuth = jsonObj.getString(ActionData.AUTH_TOKEN);
			return new PlayerCharacterData(userAuth);
		}
	}

	/**
	 * {@link ItemData} is an inner class of {@link UseItemRequestData}
	 * that holds data for an item only for the purpose of using it 
	 * from a player character's personal inventory.
	 * 
	 * @author Studio Toozo
	 */
	public static class ItemData {
		private String itemID;
		
		public ItemData(final String itemID) {
			this.itemID = itemID;
		}

		public final String getItemID() {
			return this.itemID;
		}

		/**
		 * 
		 * @return {@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.ITEM_ID, this.itemID);
			return data;
		}
	}
}
