package gamelogic;

import sessionmanagement.SessionManager.Session;

public class Player extends Client {
	
	private final String playerName;
	
	public Player(Session session, String playerName) {
		super(session);
		this.playerName = playerName;
	}

}
