package actiondata;

import org.json.JSONObject;

public class InventoryData {

	/**
	 * {@link ItemData} is an inner class of {@link CommunalInventoryRequestData} that holds
	 * data for an item only for the purpose of using it from a player character's
	 * personal inventory.
	 * 
	 * @author Studio Toozo
	 */
	public static class ItemData {
		private String name;

		public ItemData(final String name) {
			this.name = name;
		}

		public final String getName() {
			return this.name;
		}

		/**
		 * 
		 * @return {@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.ITEM_NAME, this.name);
			return data;
		}
	}

}
