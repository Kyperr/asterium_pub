package actiondata;

import org.json.JSONObject;

import message.Request;
import message.Response;

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
	public static final String ALLOCATE_STATS = "allocate_stats";
	//public static final String PLAYER_SYNC = "player_sync";
	public static final String SYNC_LOCATIONS = "sync_locations";
	public static final String SYNC_GAME_BOARD_DATA = "sync_game_board_data";
	public static final String SYNC_PLAYER_CLIENT_DATA = "sync_player_client_data";
	public static final String TURN_ACTION = "turn_action";
	public static final String SYNC_PLAYER_LIST = "sync_player_list";
	public static final String USE_ITEM = "use_item";
	public static final String LEAVE_GAME = "leave_game";
	public static final String SUCCESS_RESPONSE = "success_response";
	public static final String ERRORED_RESPONSE = "errored_response";
	public static final String GENERAL_REQUEST = "general_request";
	
	//Query action constants
	public static final String QUERY_IS_IN_GAME = "query_is_in_game";
	
	//Field Constants
	public static final String ACTION_NAME = "action_name";
	public static final String NAME = "name";
	public static final String COLOR = "color";
	public static final String FOOD = "food";
	public static final String FUEL = "fuel";
	public static final String DAY = "day";
	public static final String LOBBY_ID = "lobby_id";
	public static final String PLAYER_LIST = "player_list";
	public static final String PLAYER_DATA = "player_data";
	public static final String PLAYER_CHARACTER_DATA = "player_character_data";
	public static final String PLAYERS = "players";
	public static final String STATS = "stats";
	public static final String HEALTH = "health";
	public static final String STAMINA = "stamina";
	public static final String LUCK = "luck";
	public static final String INTUITION = "intuition";	
	public static final String TURN_TAKEN = "turn_taken";	
	public static final String GAMEBOARD_DATA = "gameboard_data";
	public static final String AUTH_TOKEN = "auth_token";
	public static final String GAME_PHASE_NAME = "game_phase_name";
	public static final String ACTIVITY = "activity";
	public static final String ACTIVITIES = "activities";
	public static final String ACTIVITY_NAME = "activity_name";
	public static final String CHARACTER = "character";
	public static final String CHARACTERS = "characters";
	public static final String CHARACTER_NAME = "character_name";
	public static final String LOCATION = "location";
	public static final String LOCATIONS = "locations";
	public static final String MAP_LOCATION = "map_location";
	public static final String COMMUNAL_INVENTORY = "communal_inventory";
	public static final String PERSONAL_INVENTORY = "personal_inventory";
	public static final String LOADOUT = "loadout";
//	public static final String HEAD = "HEAD";
//	public static final String TORSO = "TORSO";
//	public static final String ARMS = "ARMS";
//	public static final String LEGS = "LEGS";
	public static final String SLOT = "slot";
	public static final String EQUIPMENT = "equipment";
	public static final String TYPE = "type";
	public static final String ITEM = "item";
	public static final String IS_COMMUNAL = "is_communal";
	public static final String USER = "user";
	public static final String TARGETS = "targets";
	public static final String ITEM_ID = "item_id";
	public static final String ITEM_NAME = "item_name";
	public static final String ICON = "icon";
	public static final String LOCATION_ID = "location_id";
	public static final String LOCATION_TYPE = "location_type";
	public static final String LOCATION_NAME = "location_name";
	public static final String ROOM_ID = "room_id"; 
	public static final String CURRENT_VALUE = "current_value";
	public static final String MAX_VALUE = "max_value";
	public static final String VICTORY_CONDITIONS = "victory_conditions";
	public static final String PLAYER_READY_STATUS = "player_ready_status";
	public static final String DISPLAY_STATS = "display_stats";
	
	/**
	 * 
	 * @return	JSONObject representation of the data.
	 */
	public JSONObject jsonify();
	
	public String getName();
	
	public boolean equals(final Object other);
	
}
