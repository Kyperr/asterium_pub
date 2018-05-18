package actiondata;

import org.json.JSONObject;

/**
 * The {@link ActionData} interface for creating a {@link Request} or {@link Response}.
 * 
 * @author Studio Toozo
 *
 */
public interface ActionData {

	//Action Name Constants
	public static final String JOIN_AS_PLAYER = "join_as_player";	
	public static final String JOIN_AS_GAMEBOARD = "join_as_gameboard";
	public static final String TOGGLE_READY_UP = "toggle_ready_up";	
	public static final String CREATE_GAME = "create_game";
	public static final String PLAYER_SYNC = "player_sync";
	public static final String SYNC_LOCATIONS = "sync_locations";
	public static final String ALLOCATE_STATS = "allocate_stats";
	public static final String SYNC_GAME_BOARD_DATA = "sync_game_board_data";
	public static final String TURN = "turn";
	
	//Query action constants
	public static final String QUERY_IS_IN_GAME = "query_is_in_game";
	
	//Field Constants
	public static final String ACTION_NAME = "action_name";
	public static final String NAME = "name";
	public static final String COLOR = "color";
	public static final String FOOD = "food";
	public static final String FUEL = "fuel";
	public static final String LOBBY_ID = "lobby_id";
	public static final String PLAYER_LIST = "player_list";
	public static final String PLAYER_DATA = "player_data";
	public static final String PLAYERS = "players";
	public static final String STATS = "stats";
	public static final String HEALTH = "health";
	public static final String STAMINA = "stamina";
	public static final String LUCK = "luck";
	public static final String INTUITION = "intuition";	
	public static final String GAMEBOARD_DATA = "gameboard_data";
	public static final String AUTH_TOKEN = "auth_token";
	public static final String ACTIVITY = "activity";
	public static final String ACTIVITIES = "activities";
	public static final String ACTIVITY_NAME = "activity_name";
	public static final String CHARACTER = "character";
	public static final String CHARACTER_NAME = "character_name";
	public static final String LOCATION = "location";
	public static final String LOCATIONS = "locations";
	public static final String MAP_LOCATION = "map_location";
	public static final String COMMUNAL_INVENTORY = "communal_inventory";
	public static final String TYPE = "type";
	public static final String LOCATION_ID = "location_id";
	public static final String ROOM_ID = "room_id"; 
	public static final String CURRENT_VALUE = "current_value";
	public static final String MAX_VALUE = "max_value";
	public static final String VICTORY_CONDITIONS = "victory_conditions";
	
	/**
	 * 
	 * @return	JSONObject representation of the data.
	 */
	public JSONObject jsonify();
	
	public String getName();
	
	public boolean equals(final Object other);
	
}
