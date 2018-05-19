package gamelogic;

import sessionmanagement.SessionManager.Session;

/**
 * {@link GameBoard} Clients are the central client for displaying the game state. A {@link Game} may have one or more.
 * 
 * @author Studio Toozo
 */
public class GameBoard extends Client {
	
	/**
	 * Creates and returns a {@link GameBoard} Client.
	 * 
	 * @param session The client's session handler
	 */
	public GameBoard(Session session) {
		super(session);
	}

}