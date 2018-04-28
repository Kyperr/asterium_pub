package gamelogic;

import sessionmanagement.SessionManager.Session;

/**
 * Player Clients are users interacting with the server from a mobile platform. Many may join
 * one game.
 * 
 * @author Daniel McBride, Jenna Hand, Bridgette Campbell, Greg Schmitt
 */
public class Player extends Client {
	
	/*
	 * The name associated with a Player
	 */
	private final String playerName;
	
	/**
	 * Creates and returns a Player.
	 * 
	 * @param session The session/connection associated with the Player
	 * @param playerName The name associated with the Player
	 */
	public Player(Session session, String playerName) {
		super(session);
		this.playerName = playerName;
	}

}
