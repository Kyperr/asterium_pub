package action;

import java.util.Map;

import general.PlayerData;

public class JoinAsPlayerAction extends AbstractAction {
	
	/* Argument names */
	public static final String LOBBY_ID = "lobby_id";
	public static final String PLAYER_DATA = "player_data";

	public JoinAsPlayerAction(Map<String, Object> args) {
		super(AbstractAction.JOIN_AS_PLAYER, args);
	}

	@Override
	public void run() {
		PlayerData p = (PlayerData)this.getArg(PLAYER_DATA);
		System.out.println("Player " + p.getName() + " has joined lobby " + this.getArg("lobby_id"));
	}

}
