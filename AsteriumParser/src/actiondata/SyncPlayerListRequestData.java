package actiondata;

import java.util.ArrayList;
import java.util.Collection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SyncPlayerListRequestData extends AbstractRequestActionData {

	Collection<PlayerData> players;

	public SyncPlayerListRequestData(Collection<SyncPlayerListRequestData.PlayerData> players) {
		super(ActionData.SYNC_PLAYER_LIST);
		this.players = players;
	}

	public Collection<PlayerData> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<PlayerData> players) {
		this.players = players;
	}

	@Override
	public JSONObject jsonify() {
		JSONObject data = new JSONObject();
		// Add players to data
		JSONArray players = new JSONArray();
		for (PlayerData player : this.players) {
			players.put(player.jsonify());
		}
		data.put(ActionData.PLAYERS, players);
		return data;
	}

	public static SyncPlayerListRequestData parseArgs(final JSONObject jsonObj) throws JSONException {
		JSONArray playerArray = jsonObj.getJSONArray(ActionData.PLAYERS);
		Collection<PlayerData> players = new ArrayList<PlayerData>();
		JSONObject playerObject;
		PlayerData player;
		for (int i = 0; i < playerArray.length(); i++) {
			playerObject = playerArray.getJSONObject(i);

			JSONObject statsObject = playerObject.getJSONObject(ActionData.DISPLAY_STATS);

			PlayerData.DisplayStats dStats = new PlayerData.DisplayStats(statsObject.getInt(ActionData.INTUITION),
					statsObject.getInt(ActionData.LUCK), statsObject.getInt(ActionData.STAMINA));

			player = new PlayerData(playerObject.getString(ActionData.NAME),
					playerObject.getBoolean(ActionData.PLAYER_READY_STATUS), dStats);
			
			players.add(player);
		}
		return new SyncPlayerListRequestData(players);
	}

	public static class PlayerData {

		private final String name;
		private boolean readyStatus;
		private DisplayStats displayStats;

		public PlayerData(final String name, boolean readyStatus, DisplayStats displayStats) {
			this.name = name;
			this.readyStatus = readyStatus;
			this.displayStats = displayStats;
		}

		public String getName() {
			return this.name;
		}

		public boolean getReadyStatus() {
			return this.readyStatus;
		}

		public void setReadyStatus(boolean readyStatus) {
			this.readyStatus = readyStatus;
		}

		/**
		 * 
		 * @return {@link JSONObject} representation of the data.
		 */
		public JSONObject jsonify() {
			JSONObject data = new JSONObject();
			data.put(ActionData.NAME, this.name);
			data.put(ActionData.DISPLAY_STATS, this.displayStats.jsonify());
			data.put(ActionData.PLAYER_READY_STATUS, this.readyStatus);
			return data;
		}

		public static class DisplayStats {
			private int intuition;
			private int luck;
			private int stamina;

			public DisplayStats(int intuition, int luck, int stamina) {
				this.intuition = intuition;
				this.luck = luck;
				this.stamina = stamina;
			}

			public int getLuck() {
				return this.luck;
			}

			public int getIntuition() {
				return this.intuition;
			}

			public int getStamina() {
				return this.stamina;
			}

			/**
			 * 
			 * @return {@link JSONObject} representation of the data.
			 */
			public JSONObject jsonify() {
				JSONObject data = new JSONObject();
				data.put(ActionData.INTUITION, this.intuition);
				data.put(ActionData.LUCK, this.luck);
				data.put(ActionData.STAMINA, this.stamina);
				return data;
			}

		}
	}

}
