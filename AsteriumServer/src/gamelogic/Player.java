package gamelogic;

import sessionmanagement.SessionManager.Session;

/**
 * {@link Player} Clients are users interacting with the server from a mobile platform. 
 * Many may join one game.
 * 
 * @author Studio Toozo
 */
public class Player extends Client {
	
	/*
	 * The name associated with a Player
	 */
	private final String playerName;
	/* Player's auth token */
	private final String authToken;
	
	/**
	 * Creates and returns a {@link Player}.
	 * 
	 * @param session The {@link Session} associated with the {@link Player}
	 * @param playerName The name associated with the {@link Player}
	 */
	public Player(final Session session, final String playerName, final String authToken) {
		super(session);
		this.playerName = playerName;
		this.authToken = authToken;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public String getAuthToken() {
		return this.authToken;
	}
}
