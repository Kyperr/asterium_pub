package com.toozo.asteriumwebserver.gamelogic;

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
			Game debugGame = new Game();
			debugGame.start();
			gameManager.gameMap.put("TOOZO", debugGame);
		} 
		return gameManager;
	}

	private GameManager() {
		// Empty on purpose
	}
	
	/**
	 * When a new game is started, create a new {@link Game} and return it
	 * to the action that requested it. Starts the {@link Game} as a {@link Thread}.
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
		for(Game g : playerMap.values()) {
			System.out.println("!Game phase: " + g.getGameState().getGamePhase());
		}
		return playerMap.get(authToken);
	}
	
	public Game registerPlayerToGame(final String authToken, Game game) {
		return playerMap.put(authToken, game);
	}
}
