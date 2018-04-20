package general;

import java.util.Map;

public class JoinAsPlayerAction extends AbstractAction {
	
	/* Argument names */
	public static final String LOBBY_ID = "lobby_id";
	public static final String PLAYER = "player";

	public JoinAsPlayerAction(Map<String, Object> args) {
		super(AbstractAction.JOIN_AS_PLAYER, args);
	}

	@Override
	public void run() {
		Player p = (Player)this.getArg("player");
		System.out.println("Player " + p.getName() + " has joined lobby " + this.getArg("lobby_id"));
	}

}
