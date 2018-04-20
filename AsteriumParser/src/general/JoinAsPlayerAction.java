package general;

import java.util.Map;

public class JoinAsPlayerAction extends AbstractAction {
	
	public static final String NAME = "join_lobby";

	public JoinAsPlayerAction(Map<String, Object> args) {
		super(NAME, args);
	}

	@Override
	public void run() {
		/*Player p = (Player)this.getArg("player");
		System.out.println("Player " + p.getName() + "has joined lobby " + this.getArg("lobby_id"));*/
	}

}
