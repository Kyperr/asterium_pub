package actiondata;

import org.json.JSONObject;

/**
 * 
 * 
 * @author Bridgette Campbell, Jenna Hand, Daniel McBride, and Greg Schmitt
 *
 */
public interface ActionData {

	//Action Name Constants
	public static final String JOIN_AS_PLAYER = "join_as_player";
	public static final String JOIN_AS_PLAYER_RESPONSE = "join_as_player_response";
	
	public static final String JOIN_AS_GAMEBOARD = "join_as_gameboard";
	public static final String JOIN_AS_GAMEBOARD_RESPONSE = "join_as_gameboard_response";

	public static final String CREATE_GAME = "create_game";
	public static final String CREATE_GAME_RESPONSE = "create_game_response";

	public static final String PLAYER_SYNC = "player_sync";
	public static final String PLAYER_SYNC_RESPONSE = "player_sync_response";

	public static final String ALLOCATE_STATS = "allocate_stats";
	public static final String ALLOCATE_STATS_RESPONSE = "allocate_stats_response";
	
	//Field Constants
	public static final String ACTION_NAME = "action_name";
	public static final String NAME = "name";
	public static final String LOBBY_ID = "lobby_id";
	public static final String PLAYER_LIST = "player_list";
	public static final String PLAYER_DATA = "player_data";
	public static final String AUTH_TOKEN = "auth_token";
	
	/**
	 * 
	 * @return	JSONObject representation of the data.
	 */
	public JSONObject jsonify();
	
	public String getName();
	
	public boolean equals(final Object other);
	
}
