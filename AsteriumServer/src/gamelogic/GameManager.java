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
	
	/*
	 * Maps player auth tokens to Game.
	 */
	private Map<String, Game> playerMap = new ConcurrentHashMap<String, Game>();
	
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
<<<<<<< HEAD
	 * When a new game is started, create a new {@link Game}.
=======
<<<<<<< HEAD
	 * When a new game is started, create a new Game and return it
	 * to the action that requested it. Starts the {@link Game} as a {@link Thread}.
=======
	 * When a new game is started, create a new {@link Game}.
>>>>>>> 3514cfc875e806649def7df390d98f746d9f3d41
>>>>>>> f7c21bc700243597b23bea06940de86087b86cf2
=======
	 * When a new game is started, create a new {@link Game} and return it
	 * to the action that requested it. Starts the {@link Game} as a {@link Thread}.
>>>>>>> 929dc8f4f779113fa060b8d15fa0ebb968d99d67
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
	public void registerGame(final Game game) {
		// Put the Game in the game map. 
		gameMap.put(game.getLobbyID(), game);
	}

	/**
	 * Checks to see if the given lobby ID is already in use by a {@link Game}.
	 * 
	 * @param lobbyID The lobby ID to be checked.
	 * @return True if the lobby ID is in use, false otherwise.
	 */
	public boolean isLobbyIDUsed(final String lobbyID) {
		return gameMap.containsKey(lobbyID);
	}
	
	/**
	 * @param lobbyID The lobbyID associated with a {@link Game}.
	 * @return The {@link Game} associated with the given lobby ID.
	 */
	public Game getGame(final String lobbyID) {
		return gameMap.get(lobbyID);
	}
	
	/**
	 * @param authToken the {@link Player}'s auth token
	 * @return the {@link Game} associated with the {@link Player}'s auth token.
	 */
	public Game getGameForPlayer(final String authToken) {
		if (playerMap.get(authToken) != null) {
			return playerMap.get(authToken);
		} else {
			return null;
		}
	}
}
