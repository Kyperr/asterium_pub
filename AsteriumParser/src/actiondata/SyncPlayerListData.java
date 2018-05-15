package actiondata;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import actiondata.JoinAsPlayerRequestData.PlayerData;

public class SyncPlayerListData extends AbstractResponseActionData {

	public SyncPlayerListData(List<SyncPlayerListData.PlayerData> players) {
		super(ActionData.SYNC_LOCATIONS);
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		return data;
	}
	
	public static SyncPlayerListData parseArgs(final JSONObject jsonObj) throws JSONException {
		return new SyncPlayerListData();
	}
	
	public static class PlayerData {

		private final String name;

		public PlayerData(final String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}

		/**
		 * 
		 * @return	{@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.NAME, this.name);
			return data;
		}
		
		public boolean equals(final Object other) {
			if (other instanceof PlayerData) {
				PlayerData otherPlayerData = (PlayerData) other;
				return otherPlayerData.name.equals(this.name);
			} else {
				return false;
			}
		}
	}

}
