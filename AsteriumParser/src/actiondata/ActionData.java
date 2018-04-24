package actiondata;

import java.util.function.Function;

import org.json.JSONObject;

public interface ActionData {

	//Action Name Constants
	public static final String JOIN_AS_PLAYER = "join_as_player";
	public static final String JOIN_AS_PLAYER_RESPONSE = "join_as_player_response";

	public static final String CREATE_GAME = "create_game";
	public static final String CREATE_GAME_RESPONSE = "create_game_response";
	
	//Field Constants
	public static final String ACTION_NAME = "action_name";
	public static final String NAME = "name";
	public static final String LOBBY_ID = "lobby_id";
	public static final String PLAYER_DATA = "player_data";
	public static final String AUTH_TOKEN = "auth_token";
	
	public JSONObject jsonify();
	
	public String getName();

	
	public boolean equals(final Object other);
}
