package gamelogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton {@link GameManager} handles the creation and management of {@link Game} on the server.
 * 
 *  @author Studio Toozo
 */
public final class GameManager {

	/*
	 * The single instance of GameManager.
	 */
	private static GameManager gameManager;
	
	/*
	 * Maps lobby IDs to Game.
	 */
	private Map<String, Game> gameMap = new ConcurrentHashMap<String, Game>();
	
	/**
	 * @return The single instance of {@link GameManager}.
	 */
	public static synchronized GameManager getInstance() {
		if (gameManager == null) {
			gameManager = new GameManager();
		} 
		return gameManager;
	}

	private GameManager() {
		// Empty on purpose
	}
	
	/**
<<<<<<< HEAD
	 * When a new game is started, create a new Game and return it
	 * to the action that requested it. Starts the {@link Game} as a {@link Thread}.
=======
	 * When a new game is started, create a new {@link Game}.
>>>>>>> 3514cfc875e806649def7df390d98f746d9f3d41
	 * 
	 * @return {@link Game}
	 */
	public Game createGame() {
		Game game = new Game();
		game.start();
		
		// Register the new game with the GameManager.
		registerGame(game);
		return game;
	}
	
	/**
	 * Register a new {@link Game} with the {@link GameManager}.
	 * 
	 * @param game The {@link Game} to be registered.
	 */
	public void registerGame(Game game) {
		// Put the Game in the game map. 
		gameMap.put(game.getLobbyID(), game);
	}

	/**
	 * Checks to see if the given lobby ID is already in use by a {@link Game}.
	 * 
	 * @param lobbyID The lobby ID to be checked.
	 * @return True if the lobby ID is in use, false otherwise.
	 */
	public boolean isLobbyIDUsed(String lobbyID) {
		return gameMap.containsKey(lobbyID);
	}
	
	/**
	 * @param lobbyID The lobbyID associated with a {@link Game}.
	 * @return The {@link Game} associated with the given lobby ID.
	 */
	public Game getGame(String lobbyID) {
		return gameMap.get(lobbyID);
	}
}
