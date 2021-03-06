package actiondata;

import java.util.Collection;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actiondata.InventoryData.ItemData;
import message.Request;

/**
 * {@link UseItemRequestData} is the representation of data to be used in a
 * {@link Request} from a PlayerCharacter to use an Item from an Inventory.
 * 
 * @author Studio Toozo
 */
public class UseItemRequestData extends AbstractRequestActionData {

	private Collection<String> targets;
	private ItemData item;
	private boolean isCommunal;
	private boolean isEquipped;

	public UseItemRequestData(final Collection<String> targets, final ItemData item, 
							  final boolean isCommunal, final boolean isEquipped) {
		super(ActionData.USE_ITEM);
		this.targets = targets;
		this.item = item;
		this.isCommunal = isCommunal;
		this.isEquipped = isEquipped;
	}

	public final Collection<String> getTargets() {
		return this.targets;
	}

	public final ItemData getItem() {
		return this.item;
	}

	public final boolean getIsCommunal() {
		return this.isCommunal;
	}
	public boolean getIsEquipped() {
		return this.isEquipped;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();

		JSONArray targetsArray = new JSONArray();
		for (String target : this.targets) {
			targetsArray.put(target);
		}
		data.put(ActionData.TARGETS, targetsArray);

		data.put(ActionData.ITEM, this.item.jsonify());

		data.put(ActionData.IS_COMMUNAL, this.isCommunal);
		
		data.put(ActionData.IS_EQUIPPED, this.isEquipped);

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

		JSONArray targetsArray = jsonObj.getJSONArray(ActionData.TARGETS);
		
		Collection<String> targets = new HashSet<String>();
		for (int i = 0; i < targetsArray.length(); i++) {
			targets.add(targetsArray.getString(i));
		}

		JSONObject itemObject = jsonObj.getJSONObject(ActionData.ITEM);
		String itemID = itemObject.getString(ActionData.ITEM_ID);
		ItemData item = new ItemData(itemID);

		boolean isCommunal = jsonObj.getBoolean(ActionData.IS_COMMUNAL);
		
		boolean isEquipped = jsonObj.getBoolean(ActionData.IS_EQUIPPED);

		return new UseItemRequestData(targets, item, isCommunal, isEquipped);
	}

}
